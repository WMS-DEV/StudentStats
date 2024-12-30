package pl.wmsdev.usos.model;

import lombok.Builder;
import lombok.Data;
import pl.wmsdev.usos.values.UsosStudentStatus;

import java.util.List;

@Data
@Builder
public class PersonalData {
    private String firstName;
    private String lastName;
    private String universityName;
    private Integer indexNumber;
    private String currentFaculty;
    private String currentMajor;
    private String currentStageOfStudies;
    private String studiesType;
    private Integer semester;
    private UsosStudentStatus studentStatus;
    private UsosStudentStatus phdStudentStatus;
    private String usosProfileUrl;
    private String photoUrl;
    private List<Studies> studies;

}
