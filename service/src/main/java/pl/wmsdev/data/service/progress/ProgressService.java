package pl.wmsdev.data.service.progress;

import pl.wmsdev.data.dto.StudiesProgressDetails;
import pl.wmsdev.usos4j.model.progs.UsosProgramme;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

public interface ProgressService {
    CompletableFuture<Integer> getCurrentSemesterNumber();

    CompletableFuture<LocalDate> getFirstDayOfStudies(String[] semesterIds);

    CompletableFuture<StudiesProgressDetails> getStudiesProgressDetails(String[] semesterIds, int amountOfAllSemesters);

    CompletableFuture<Double> getSemesterProgress(String[] semesterIds);

    CompletableFuture<UsosProgramme> getProgramme();

    CompletableFuture<Double> getStudiesProgress(UsosProgramme programme, int amountOfAllSemesters, double semesterProgress);
}
