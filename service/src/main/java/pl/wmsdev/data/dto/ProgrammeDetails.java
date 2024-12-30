package pl.wmsdev.data.dto;

import lombok.Builder;

@Builder
public record ProgrammeDetails(String currentMajor, String currentStageOfStudies, String studiesType,
                               String currentFaculty) {
}
