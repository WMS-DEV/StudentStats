package pl.wmsdev.data.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import pl.wmsdev.data.values.StudentStatsCategory;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class StudentStatsText extends AbstractStudentStatsContent {
    private String subtitle;
    private String value;

    public static StudentStatsObject asObject(StudentStatsCategory category, String title,
                                              String subtitle, String value){
        return StudentStatsObject.builder()
                .category(category)
                .content(StudentStatsText.builder()
                        .title(title)
                        .subtitle(subtitle)
                        .value(value)
                        .build())
                .build();
    }

}
