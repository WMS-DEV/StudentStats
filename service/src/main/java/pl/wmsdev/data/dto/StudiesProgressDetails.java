package pl.wmsdev.data.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record StudiesProgressDetails(Double currentSemesterProgress, Double studiesProgress,
                                     LocalDate firstDayOfStudies) {
}
