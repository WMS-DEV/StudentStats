package pl.wmsdev.data.service.progress;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.wmsdev.data.dto.StudiesProgressDetails;
import pl.wmsdev.usos4j.client.UsosServerAPI;
import pl.wmsdev.usos4j.client.UsosUserAPI;
import pl.wmsdev.usos4j.model.progs.UsosProgramme;
import pl.wmsdev.usos4j.model.progs.UsosProgrammeParam;
import pl.wmsdev.usos4j.model.terms.UsosTerm;
import pl.wmsdev.usos4j.model.terms.UsosTermsParams;
import pl.wmsdev.utils.converter.ProgressDetailsConverter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ProgressServiceImpl implements ProgressService {

	private static final Pattern totalSemesterAmountPattern = Pattern.compile("\\d+");
	private static final int DEFAULT_SEMESTER_DURATION_OF_STUDIES = 7; // Full-time engineer studies in Poland last usually 7 semesters

	private final UsosUserAPI userApi;
	private final UsosServerAPI serverApi;
	private final ProgressDetailsConverter progressDetailsConverter;

	@Async
	@Override
	public CompletableFuture<Integer> getCurrentSemesterNumber() {
		return CompletableFuture.completedFuture(userApi.courses().user().courseEditions().size());
	}

	@Async
	@Override
	public CompletableFuture<LocalDate> getFirstDayOfStudies(String[] semesterIds) {
		var semester = serverApi.terms().terms(UsosTermsParams.builder(semesterIds).build())
				.values().stream()
				.min(Comparator.comparingInt(UsosTerm::orderKey))
				.orElseThrow();

		return CompletableFuture.completedFuture(semester.startDate());
	}

	@Async
	@Override
	@SneakyThrows
	public CompletableFuture<StudiesProgressDetails> getStudiesProgressDetails(String[] semesterIds,
	                                                                           int amountOfAllSemesters) {
		CompletableFuture<UsosProgramme> programme = getProgramme();
		CompletableFuture<Double> semesterProgress = getSemesterProgress(semesterIds);
		CompletableFuture<LocalDate> firstDayOfStudies = getFirstDayOfStudies(semesterIds);

		CompletableFuture.allOf(programme, semesterProgress).join();

		CompletableFuture<Double> studiesProgress = getStudiesProgress(
				programme.get(),
				amountOfAllSemesters,
				semesterProgress.get()
		);

		CompletableFuture.allOf(firstDayOfStudies, studiesProgress);

		return CompletableFuture.completedFuture(
				progressDetailsConverter.toStudiesProgressDetails(firstDayOfStudies.get(), studiesProgress.get(),
						semesterProgress.get()));
	}

	@Async
	@Override
	public CompletableFuture<Double> getSemesterProgress(String[] semesterIds) {
		var semester = serverApi.terms().terms(UsosTermsParams.builder(semesterIds).build())
				.values().stream()
				.max(Comparator.comparingInt(UsosTerm::orderKey))
				.orElseThrow();

		var startDate = semester.startDate();
		var endDate = semester.endDate();

		long daysInSemester = ChronoUnit.DAYS.between(startDate, endDate);
		long daysPassed = Math.max(0, ChronoUnit.DAYS.between(startDate, LocalDate.now()));

		return CompletableFuture.completedFuture(Math.min((double) daysPassed / daysInSemester, 1.0d));
	}

	@Async
	@Override
	public CompletableFuture<UsosProgramme> getProgramme() {
		return CompletableFuture.completedFuture(
				userApi.progs().student().get(0).programme() // Every student has at least 1 programme
		);
	}

	@Async
	@Override
	public CompletableFuture<Double> getStudiesProgress(UsosProgramme programme, int amountOfAllSemesters,
	                                                    double semesterProgress) {
		String studiesId = programme.id();
		var studiesProgramme = userApi.progs().programme(UsosProgrammeParam.builder(studiesId).build());
		String durationDescription = studiesProgramme.duration().pl();

		// All semesters, except last one, are completed (last one may too, then == semestersAmount)
		double semestersPassed = amountOfAllSemesters - 1 + semesterProgress;
		int semesterDurationOfStudies = parseAmountOfTotalSemesters(durationDescription); // Studies duration may vary

		double studiesProgress = semestersPassed / semesterDurationOfStudies; // semesterDurationOfStudies is never 0
		return CompletableFuture.completedFuture(studiesProgress);
	}

	private int parseAmountOfTotalSemesters(String durationDescription) {
		Matcher matcher = totalSemesterAmountPattern.matcher(durationDescription);

		if (matcher.find()) {
			return Integer.parseInt(matcher.group());
		} else {
			return DEFAULT_SEMESTER_DURATION_OF_STUDIES;
		}
	}
}
