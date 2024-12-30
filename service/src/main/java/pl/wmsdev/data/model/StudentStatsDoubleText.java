package pl.wmsdev.data.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import pl.wmsdev.data.values.StudentStatsCategory;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class StudentStatsDoubleText extends AbstractStudentStatsContent {
    private String subtitle;
    private String value1;
    private String value2;

    public static StudentStatsObject asObject(StudentStatsCategory category, String title,
                                              String subtitle, String value1, String value2){
        return StudentStatsObject.builder()
                .category(category)
                .content(StudentStatsDoubleText.builder()
                        .title(title)
                        .subtitle(subtitle)
                        .value1(value1)
                        .value2(value2)
                        .build())
                .build();
    }
}
