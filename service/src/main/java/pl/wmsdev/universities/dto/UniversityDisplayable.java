package pl.wmsdev.universities.dto;

import lombok.Builder;

@Builder
public record UniversityDisplayable(String id, String name) {
}
