package pl.wmsdev.usos.cards.creators;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wmsdev.data.model.*;
import pl.wmsdev.data.values.StudentStatsCategory;
import pl.wmsdev.data.values.StudentStatsChartType;
import pl.wmsdev.usos.model.Course;
import pl.wmsdev.usos.model.Semester;
import pl.wmsdev.usos.model.Studies;
import pl.wmsdev.utils.internationalization.LocalizedMessageService;
import pl.wmsdev.utils.values.Gender;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class UsosCoursesCardCreator implements UsosCardCreator {

    private final LocalizedMessageService msgService;

    @Override
    public List<StudentStatsObject> getAlwaysPresentCards(List<Studies> userStudies) {
        return List.of(createBestCourseCard(userStudies), createWorstCourseCard(userStudies),
                createMarkGraphCard(userStudies), createMostMarksCard(userStudies), totalECTSPointsCard(userStudies),
                createHighestEctsCourseCard(userStudies), createLowestEctsCourseCard(userStudies), createCourseByTeachersGenderCard(userStudies),
                createCourseByTypesCard(userStudies));
    }

    @Override
    public List<Optional<StudentStatsObject>> getOptionalCards(List<Studies> userStudies) {
        return List.of(createGreatMarkCourseCard(userStudies), createFailedCoursesCard(userStudies), createECTSPointsChartCard(userStudies));
    }

    private List<Course> getCoursesWithMark(List<Studies> studies, BigDecimal mark) {
        return extractCoursesFromStudies(studies)
                .filter(course -> course.getMark().equals(mark))
                .toList();
    }

    private StudentStatsObject totalECTSPointsCard(List<Studies> userStudies) {
        var totalECTSPoints = extractCoursesFromStudies(userStudies)
                .map(Course::getEcts)
                .reduce(0, Integer::sum);

        return StudentStatsText.asObject(StudentStatsCategory.COURSES,
                msgService.getMessageFromContext("msg.StudentStatsService.ects-balance"),
                null,
                totalECTSPoints.toString());
    }

    private Optional<StudentStatsObject> createFailedCoursesCard(List<Studies> studies) {
        var failedCourses = getCoursesWithMark(studies, BigDecimal.valueOf(2.0));
        if (!failedCourses.isEmpty()) {
            return Optional.of(StudentStatsText.asObject(StudentStatsCategory.COURSES,
                    msgService.getMessageWithArgsFromContext("msg.UsosCoursesCardCreator.how-many-failed", failedCourses.size()),
                    msgService.getMessageWithArgsFromContext("msg.UsosCoursesCardCreator.which-failed", toCourseList(failedCourses)),
                    String.valueOf(failedCourses.size())));
        }
        return Optional.empty();
    }

    private Optional<StudentStatsObject> createGreatMarkCourseCard(List<Studies> studies) {
        var greatMarks = getCoursesWithMark(studies, BigDecimal.valueOf(5.5));
        if (!greatMarks.isEmpty()) {
            return Optional.of(StudentStatsText.asObject(StudentStatsCategory.COURSES,
                    msgService.getMessageWithArgsFromContext("msg.UsosCoursesCardCreator.how-many-excellent-grades"),
                    msgService.getMessageWithArgsFromContext("msg.UsosCoursesCardCreator.best-subjects", toCourseList(greatMarks)),
                    String.valueOf(greatMarks.size())));
        }
        return Optional.empty();
    }

    private String toCourseList(List<Course> courses) {
        return courses.stream().map(Course::getName).map(String::strip).collect(Collectors.joining(", "));
    }

    private StudentStatsObject createWorstCourseCard(List<Studies> userStudies) {
        return createCourseCard(userStudies, Comparator.comparing(Course::getMark).reversed(),
                msgService.getMessageFromContext("msg.UsosCoursesCardCreator.worst-grade"), course -> course.getMark().toString());
    }

    private StudentStatsObject createCourseCard(List<Studies> userStudies, Comparator<Course> courseComparator,
                                                String titleToFormat, Function<Course, String> valueGenerator) {
        var bestMark = extractCoursesFromStudies(userStudies).max(courseComparator);

        return StudentStatsObject.builder()
                .category(StudentStatsCategory.COURSES)
                .content(StudentStatsText.builder()
                        .title(titleToFormat.formatted(bestMark.orElseThrow().getName().trim()))
                        .value(valueGenerator.apply(bestMark.orElseThrow()))
                        .build())
                .build();
    }

    private StudentStatsObject createBestCourseCard(List<Studies> userStudies) {
        return createCourseCard(userStudies, Comparator.comparing(Course::getMark),
                msgService.getMessageFromContext("msg.UsosCoursesCardCreator.best-grade"), course -> course.getMark().toString());
    }

    private Optional<StudentStatsObject> createECTSPointsChartCard(List<Studies> userStudies) {
        if (numberOfSemesters(userStudies) <= 1) {
            return Optional.empty();
        }

        List<StudentStatsChartValue> values = createECTSChartValues(userStudies);

        return Optional.ofNullable(buildECTSChart(values));
    }

    private List<StudentStatsChartValue> createECTSChartValues(List<Studies> userStudies) {
        List<StudentStatsChartValue> values = new ArrayList<>();

        userStudies.stream()
                .flatMap(studies -> studies.semesters().stream())
                .forEach(semester -> {
                    int totalECTS = semester.courses().stream()
                            .mapToInt(Course::getEcts)
                            .sum();
                    values.add(new StudentStatsChartValue((double) totalECTS, semester.name()));
                });

        Collections.reverse(values);

        return values;
    }

    private StudentStatsObject buildECTSChart(List<StudentStatsChartValue> values) {
        return StudentStatsObject.builder()
                .category(StudentStatsCategory.COURSES)
                .content(StudentStatsChart.builder()
                        .chartType(StudentStatsChartType.LINE)
                        .title(msgService.getMessageFromContext("msg.UsosCoursesCardCreator.ects-graph"))
                        .values(values)
                        .build())
                .build();
    }


    private Integer numberOfSemesters(List<Studies> userStudies) {
        return userStudies.stream()
                .mapToInt(Studies::numberOfSemesters)
                .sum();
    }

    private StudentStatsObject createMostMarksCard(List<Studies> studies) {
        var marksAndStudies = getMarksPerCourse(studies);
        var mostOftenReceivedMark = marksAndStudies.entrySet()
                .stream().max(Comparator.comparing(entry -> entry.getValue().size()))
                .stream().findFirst().orElseThrow();

        return StudentStatsText.asObject(StudentStatsCategory.COURSES,
                msgService.getMessageWithArgsFromContext(
                        "msg.UsosCoursesCardCreator.highest-frequency-grade"
                ),
                msgService.getMessageWithArgsFromContext(
                        "msg.UsosCoursesCardCreator.highest-frequency-grade-courses",
                        mostOftenReceivedMark.getValue().stream()
                                .limit(2)
                                .map(course -> course.getName().trim())
                                .collect(Collectors.joining(", "))
                ),
                mostOftenReceivedMark.getKey().toString()
        );
    }

    private StudentStatsObject createMarkGraphCard(List<Studies> studies) {
        Map<BigDecimal, List<Course>> marksPerCourse = getMarksPerCourse(studies);
        return StudentStatsObject.builder()
                .category(StudentStatsCategory.COURSES)
                .content(StudentStatsChart.builder()
                        .chartType(StudentStatsChartType.BAR)
                        .title(msgService.getMessageFromContext("msg.UsosCoursesCardCreator.grade-graph"))
                        .values(toStudentStatsChartValue(marksPerCourse))
                        .build())
                .build();
    }

    private List<StudentStatsChartValue> toStudentStatsChartValue(Map<BigDecimal, List<Course>> marksPerCourse) {
        return marksPerCourse.entrySet()
                .stream().map(entry -> StudentStatsChartValue.of((double) entry.getValue().size(), entry.getKey().toString()))
                .sorted(Comparator.comparingDouble(chartValue -> Double.parseDouble(chartValue.getLabel())))
                .toList();
    }

    private Map<BigDecimal, List<Course>> getMarksPerCourse(List<Studies> studies) {
        return Stream.of(2.0, 3.0, 3.5, 4.0, 4.5, 5.0, 5.5)
                .map(BigDecimal::valueOf)
                .map(mark -> new AbstractMap.SimpleEntry<>(mark, getCoursesWithMark(studies, mark)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private StudentStatsObject createHighestEctsCourseCard(List<Studies> studies) {
        return createCourseByEctsCard(studies,
                Comparator.comparing(Course::getEcts),
                "msg.UsosCoursesCardCreator.highest-ects");
    }

    private StudentStatsObject createLowestEctsCourseCard(List<Studies> studies) {
        return createCourseByEctsCard(studies,
                Comparator.comparing(Course::getEcts).reversed(),
                "msg.UsosCoursesCardCreator.lowest-ects");
    }

    private StudentStatsObject createCourseByEctsCard(List<Studies> studies, Comparator<Course> comparator, String messageKey) {
        return findCourseWithMostEcts(studies, comparator)
                .map(course -> StudentStatsText.asObject(
                        StudentStatsCategory.COURSES,
                        msgService.getMessageWithArgsFromContext(
                                messageKey,
                                course.getName().trim()
                        ),
                        null,
                        String.valueOf(course.getEcts())
                ))
                .orElseThrow();
    }

    private Optional<Course> findCourseWithMostEcts(List<Studies> studies, Comparator<Course> comparator) {
        return extractCoursesFromStudies(studies).max(comparator);
    }

    private StudentStatsObject createCourseByTeachersGenderCard(List<Studies> studies) {
        Map<Gender, Long> teachersByGender = countTeachersByGender(studies);
        return StudentStatsDoubleText.asObject(
                StudentStatsCategory.COURSES,
                msgService.getMessageFromContext("msg.UsosCoursesCardCreator.teachers-gender-count"),
                null,
                String.valueOf(teachersByGender.getOrDefault(Gender.FEMALE, 0L)),
                String.valueOf(teachersByGender.getOrDefault(Gender.MALE, 0L))
        );
    }

    private Map<Gender, Long> countTeachersByGender(List<Studies> studies) {
        return extractCoursesFromStudies(studies)
                .flatMap(course -> course.getTeachers().stream())
                .distinct()
                .collect(Collectors.groupingBy(this::determineGender, Collectors.counting()));
    }

    private Gender determineGender(String teacher) {
        String firstName = getFirstName(teacher);
        return firstName.endsWith("a") ? Gender.FEMALE : Gender.MALE; // In Polish language female names end with 'a'
    }

    private String getFirstName(String fullName) {
        return fullName.split(" ")[0];
    }

    private StudentStatsObject createCourseByTypesCard(List<Studies> studies) {
        List<StudentStatsChartValue> values = countCourseTypes(studies);
        return StudentStatsObject.builder()
                .content(StudentStatsChart.builder()
                        .values(values)
                        .chartType(StudentStatsChartType.BAR)
                        .title(msgService.getMessageFromContext("msg.UsosCoursesCardCreator.course-types-graph"))
                        .build())
                .category(StudentStatsCategory.COURSES)
                .build();
    }

    private List<StudentStatsChartValue> countCourseTypes(List<Studies> studies) {
        return extractCoursesFromStudies(studies)
                .flatMap(course -> course.getCourseTypes().stream())
                .collect(Collectors.groupingBy(type -> type, Collectors.counting()))
                .entrySet()
                .stream()
                .map(entry -> StudentStatsChartValue.of(Double.valueOf(entry.getValue()), entry.getKey()))
                .toList();
    }

    private Stream<Course> extractCoursesFromStudies(List<Studies> studies) {
        return studies.stream()
                .map(Studies::semesters)
                .flatMap(List::stream)
                .map(Semester::courses)
                .flatMap(List::stream);
    }
}
