package pl.wmsdev.usos.cards.creators;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wmsdev.data.model.StudentStatsChart;
import pl.wmsdev.data.model.StudentStatsChartValue;
import pl.wmsdev.data.model.StudentStatsDoubleText;
import pl.wmsdev.data.model.StudentStatsObject;
import pl.wmsdev.data.values.StudentStatsCategory;
import pl.wmsdev.data.values.StudentStatsChartType;
import pl.wmsdev.usos.model.Course;
import pl.wmsdev.usos.model.Semester;
import pl.wmsdev.usos.model.Studies;
import pl.wmsdev.utils.internationalization.LocalizedMessageService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UsosAveragesCardCreator implements UsosCardCreator {

    private final LocalizedMessageService msgService;

    @Override
    public List<Optional<StudentStatsObject>> getOptionalCards(List<Studies> userStudies) {
        return List.of(createWeightedAndUnweightedAvgGradeFromWholeSemesterCard(userStudies),
                createWeightedAndUnweightedAvgGradeFromLAstSemesterCard(userStudies));
    }

    @Override
    public List<StudentStatsObject> getAlwaysPresentCards(List<Studies> studies) {
        return List.of(createUnweightedAvgGraphPerSemester(studies), createWeightedAvgGraphPerSemester(studies));
    }

    private StudentStatsObject createWeightedAvgGraphPerSemester(List<Studies> studies) {
        var values = getAverageValues(studies,
                (semester -> weightedAverageGradeFromOneSemester(semester).doubleValue()));
        return StudentStatsObject.builder()
                .content(StudentStatsChart.builder()
                        .values(values)
                        .chartType(StudentStatsChartType.BAR)
                        .title(msgService.getMessageFromContext("msg.weighted-average-per-semester-graph"))
                        .build())
                .category(StudentStatsCategory.GPA)
                .build();
    }

    private StudentStatsObject createUnweightedAvgGraphPerSemester(List<Studies> studies) {
        var values = getAverageValues(studies,
                (semester -> unweightedAverageGradeFromOneSemester(semester).doubleValue()));
        return StudentStatsObject.builder()
                .content(StudentStatsChart.builder()
                        .values(values)
                        .chartType(StudentStatsChartType.BAR)
                        .title(msgService.getMessageFromContext("msg.unweighted-average-per-semester-graph"))
                        .build())
                .category(StudentStatsCategory.GPA)
                .build();
    }

    private List<StudentStatsChartValue> getAverageValues(List<Studies> studies, Function<Semester,
                                                          Double> computeAverage) {
        var values = studies.stream()
                .flatMap(study -> study.semesters().stream())
                .map(semester -> StudentStatsChartValue.of(computeAverage.apply(semester), semester.name()))
                .collect(Collectors.toList());
        Collections.reverse(values);
        return values;
    }

    private Optional<StudentStatsObject> createWeightedAndUnweightedAvgGradeFromWholeSemesterCard(
            List<Studies> userStudies) {
        return Optional.of(StudentStatsDoubleText.asObject(StudentStatsCategory.GPA,
                msgService.getMessageFromContext("msg.avg-grades-weighted-not-weighted.all"), null,
                String.valueOf(weightedAverageGradeFromAllSemesters(userStudies)),
                String.valueOf(unweightedAverageGradeFromAllSemesters(userStudies))));
    }


    private Optional<StudentStatsObject> createWeightedAndUnweightedAvgGradeFromLAstSemesterCard(
            List<Studies> userStudies) {
        return Optional.of(StudentStatsDoubleText.asObject(StudentStatsCategory.GPA,
                msgService.getMessageFromContext("msg.avg-grades-weighted-not-weighted.last-sem"), null,
                String.valueOf(weightedAverageGradeFromOneSemester(userStudies.get(0).lastSemester())),
                String.valueOf(unweightedAverageGradeFromOneSemester(userStudies.get(0).lastSemester()))));
    }

    private BigDecimal weightedAverageGradeFromAllSemesters(List<Studies> userStudies) {
        BigDecimal marksSum = userStudies.stream()
                .map(Studies::semesters)
                .flatMap(List::stream)
                .map(Semester::courses)
                .flatMap(List::stream)
                .map(course -> course.getMark().multiply(BigDecimal.valueOf(course.getEcts())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int EctsSum = userStudies.stream()
                .map(Studies::semesters)
                .flatMap(List::stream)
                .map(Semester::courses)
                .flatMap(List::stream)
                .mapToInt(Course::getEcts)
                .sum();

        return marksSum.divide(BigDecimal.valueOf(EctsSum), 2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal unweightedAverageGradeFromAllSemesters(List<Studies> userStudies) {
        BigDecimal marksSum = userStudies.stream()
                .map(Studies::semesters)
                .flatMap(List::stream)
                .map(Semester::courses)
                .flatMap(List::stream)
                .map(Course::getMark)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int marksCounter = userStudies.stream()
                .map(Studies::semesters)
                .flatMap(List::stream)
                .map(Semester::courses)
                .mapToInt(List::size)
                .sum();

        return marksSum.divide(BigDecimal.valueOf(marksCounter), 2, RoundingMode.HALF_EVEN);
    }


    private BigDecimal weightedAverageGradeFromOneSemester(Semester semester) {
        BigDecimal marksSum = semester.courses().stream()
                .map(course -> course.getMark().multiply(BigDecimal.valueOf(course.getEcts())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        double ECTSSum = semester.courses().stream()
                .mapToDouble(Course::getEcts)
                .sum();
        return marksSum.divide(BigDecimal.valueOf(ECTSSum), 3, RoundingMode.HALF_EVEN);
    }

    private BigDecimal unweightedAverageGradeFromOneSemester(Semester semester) {
        int marksCounter = semester.courses().size();
        BigDecimal marksSum = semester.courses().stream()
                .map(Course::getMark)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return marksSum.divide(BigDecimal.valueOf(marksCounter), 3, RoundingMode.HALF_EVEN);
    }

}
