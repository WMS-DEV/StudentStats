package pl.wmsdev.usos.model;

import lombok.Builder;

@Builder
public record StudiesDescription(String currentMajor, String studiesStage, String studiesType) {
}
