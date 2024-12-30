package pl.wmsdev.utils.converter;

import org.springframework.stereotype.Component;
import pl.wmsdev.data.dto.ProgrammeDetails;
import pl.wmsdev.data.dto.StudiesProgressDetails;
import pl.wmsdev.usos.model.Semester;
import pl.wmsdev.usos.model.Studies;
import pl.wmsdev.usos4j.model.terms.UsosTerm;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class StudiesConverter {

    public List<Studies> toStudies(List<Semester> semesters, ProgrammeDetails programmeDetails,
                                   LocalDate firstDayOfStudies, Map<String, UsosTerm> semesterIdsToTerms,
                                   StudiesProgressDetails studiesProgressDetails) {
        return Collections.singletonList(
                Studies.builder()
                        .semesters(semesters)
                        .name(programmeDetails.currentMajor())
                        .level(programmeDetails.currentStageOfStudies())
                        .type(programmeDetails.studiesType())
                        .firstDayOfFirstSemester(firstDayOfStudies)
                        .semesters(getSortedSemesters(semesters, semesterIdsToTerms))
                        .studiesProgress(studiesProgressDetails.studiesProgress())
                        .currentSemesterProgress(studiesProgressDetails.currentSemesterProgress())
                        .build()
        );
    }

    private List<Semester> getSortedSemesters(List<Semester> semesters, Map<String, UsosTerm> semestersMap) {
        return semesters.stream()
                .sorted((s1, s2) -> semestersMap.get(s2.name()).orderKey() - semestersMap.get(s1.name()).orderKey())
                .toList();
    }
}
