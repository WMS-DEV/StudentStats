package pl.wmsdev.data.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class StudentStatsFlag extends AbstractStudentStatsContent {
    private Boolean value;
}
