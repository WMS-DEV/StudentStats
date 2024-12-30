package pl.wmsdev.data.service.studies;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.wmsdev.data.dto.ProgrammeDetails;
import pl.wmsdev.data.dto.StudiesProgressDetails;
import pl.wmsdev.data.service.ects.EctsService;
import pl.wmsdev.data.service.marks.MarksService;
import pl.wmsdev.data.service.programme.ProgrammeDetailsService;
import pl.wmsdev.data.service.progress.ProgressService;
import pl.wmsdev.usos.model.Semester;
import pl.wmsdev.usos.model.Studies;
import pl.wmsdev.usos4j.client.UsosServerAPI;
import pl.wmsdev.usos4j.client.UsosUserAPI;
import pl.wmsdev.usos4j.model.courses.UsosCourseEdition;
import pl.wmsdev.usos4j.model.terms.UsosTerm;
import pl.wmsdev.usos4j.model.terms.UsosTermsParams;
import pl.wmsdev.utils.converter.SemesterConverter;
import pl.wmsdev.utils.converter.StudiesConverter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class StudiesServiceImpl implements StudiesService {

    private final UsosUserAPI userApi;
    private final UsosServerAPI serverApi;
    private final EctsService ectsService;
    private final MarksService marksService;
    private final ProgressService progressService;
    private final ProgrammeDetailsService programmeDetailsService;
    private final StudiesConverter studiesConverter;
    private final SemesterConverter semesterConverter;

    @Async
    @SneakyThrows
    @Override
    public CompletableFuture<List<Studies>> getStudies() {
        // Note: This USOS API request is necessary for the rest of processing, but it is a bottleneck
        Map<String, Collection<UsosCourseEdition>> semesterIdToCourseEditions = userApi.courses().user().courseEditions();

        String[] semesterIds = semesterIdToCourseEditions.keySet().toArray(String[]::new);
        int amountOfAllSemesters = semesterIdToCourseEditions.size();

        CompletableFuture<List<Semester>> semesters = getSemesters(userApi, semesterIdToCourseEditions, semesterIds);

        CompletableFuture<Map<String, UsosTerm>> semesterIdsToTerms = getTerms(serverApi, semesterIds);

        CompletableFuture<StudiesProgressDetails> studiesProgress =
                progressService.getStudiesProgressDetails(semesterIds, amountOfAllSemesters);

        CompletableFuture<ProgrammeDetails> programmeDetails = programmeDetailsService.getProgrammeDetails();

        CompletableFuture<LocalDate> firstDayOfStudies = progressService.getFirstDayOfStudies(semesterIds);

        CompletableFuture.allOf(semesters,
                        semesterIdsToTerms,
                        programmeDetails,
                        studiesProgress,
                        firstDayOfStudies)
                .join();

        return CompletableFuture.completedFuture(studiesConverter.toStudies(semesters.get(), programmeDetails.get(),
                firstDayOfStudies.get(), semesterIdsToTerms.get(), studiesProgress.get()));
    }

    @Async
    protected CompletableFuture<List<Semester>> getSemesters(UsosUserAPI userApi,
                                                           Map<String, Collection<UsosCourseEdition>> semesterIdsToCourseEditions,
                                                           String[] semesterIds) {
        List<Semester> semesters = new ArrayList<>();
        for (var semesterIdToCourseEditions : semesterIdsToCourseEditions.entrySet()) {
            semesters.add(semesterConverter.toSemester(semesterIdToCourseEditions));
        }

        CompletableFuture<Void> marksFuture = marksService.addMarks(userApi, semesters, semesterIds);
        CompletableFuture<Void> ectsFuture = ectsService.addEcts(userApi, semesters);
        CompletableFuture.allOf(marksFuture, ectsFuture).join();

        removeUngradedCoursesAndEmptySemesters(semesters);

        return CompletableFuture.completedFuture(semesters);
    }

    private void removeUngradedCoursesAndEmptySemesters(List<Semester> semesters) {
        semesters.forEach(semester -> semester.courses().removeIf(course -> course.getMark().equals(BigDecimal.ZERO)));
        semesters.removeIf(semester -> semester.courses().isEmpty());
    }

    @Async
    protected CompletableFuture<Map<String, UsosTerm>> getTerms(UsosServerAPI serverApi, String[] semesterIds) {
        return CompletableFuture.completedFuture(serverApi.terms().terms(UsosTermsParams.builder(semesterIds).build()));
    }
}
