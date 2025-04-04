package pl.wmsdev.data.model;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@Accessors(chain = true)
public abstract class AbstractStudentStatsContent {
    private String title;
}
