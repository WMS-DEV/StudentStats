package pl.wmsdev.data.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import pl.wmsdev.data.values.StudentStatsChartType;


import java.util.List;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class StudentStatsChart extends AbstractStudentStatsContent {
    private StudentStatsChartType chartType;
    private String subtitle;
    private List<StudentStatsChartValue> values;
}
