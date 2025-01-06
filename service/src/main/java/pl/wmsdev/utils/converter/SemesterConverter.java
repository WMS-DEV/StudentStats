package pl.wmsdev.utils.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wmsdev.usos.model.Course;
import pl.wmsdev.usos.model.Semester;
import pl.wmsdev.usos4j.model.courses.UsosCourseEdition;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SemesterConverter {

    private final CourseConverter courseConverter;

    public Semester toSemester(Map.Entry<String, Collection<UsosCourseEdition>> semesterIdToCourseEditions) {
        return Semester.builder()
                .name(semesterIdToCourseEditions.getKey())
                .courses(convertCourses(semesterIdToCourseEditions.getValue()))
                .decisionUrls(Optional.empty())
                .build();
    }

    private List<Course> convertCourses(Collection<UsosCourseEdition> courseEditions) {
        return courseEditions.stream().map(courseConverter::toCourse).collect(Collectors.toList());
    }
}
