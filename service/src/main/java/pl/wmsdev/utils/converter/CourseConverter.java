package pl.wmsdev.utils.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wmsdev.usos.model.Course;
import pl.wmsdev.usos4j.model.courses.UsosCourseEdition;
import pl.wmsdev.utils.internationalization.LocalizedMessageService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CourseConverter {

    private final LocalizedMessageService msgService;

    public Course toCourse(UsosCourseEdition courseEdition) {
        return Course.builder()
                .name(msgService.getLocalized(courseEdition.courseName()))
                .code(courseEdition.courseId())
                .mark(BigDecimal.ZERO)
                .Ects(0)
                .teachers(getTeachers(courseEdition))
                .courseTypes(getCourseTypes(courseEdition))
                .build();
    }

    private Set<String> getTeachers(UsosCourseEdition course) {
        return course.userGroups().stream()
                .flatMap(e -> e.lecturers().stream())
                .map(e -> e.firstName() + " " + e.lastName())
                .collect(Collectors.toSet());
    }

    private List<String> getCourseTypes(UsosCourseEdition course) {
        return course.userGroups().stream()
                .map(group -> msgService.getLocalized(group.classType()))
                .collect(Collectors.toList());
    }
}
