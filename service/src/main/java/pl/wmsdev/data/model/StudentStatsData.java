package pl.wmsdev.data.model;

import lombok.Builder;
import lombok.Data;
import pl.wmsdev.usos.model.PersonalData;

import java.util.List;

@Data
@Builder
public class StudentStatsData {
    private PersonalData personalData;
    private List<StudentStatsObject> content;
}
