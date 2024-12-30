package pl.wmsdev.usos.model;

import lombok.Builder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Builder
public record Semester(String name, List<Course> courses, Optional<Set<String>> decisionUrls) {
}
