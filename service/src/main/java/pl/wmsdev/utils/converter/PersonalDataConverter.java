package pl.wmsdev.utils.converter;

import org.springframework.stereotype.Component;
import pl.wmsdev.data.dto.PersonalDataUser;
import pl.wmsdev.data.dto.ProgrammeDetails;
import pl.wmsdev.usos.model.PersonalData;
import pl.wmsdev.usos.model.Studies;

import java.util.List;

@Component
public class PersonalDataConverter {

    public PersonalData toPersonalData(String universityName, List<Studies> studies, PersonalDataUser personalDataUser,
                                       ProgrammeDetails programmeDetails, Integer currentSemester) {
        return PersonalData.builder()
                .universityName(universityName)
                .studies(studies)
                .firstName(personalDataUser.firstName())
                .lastName(personalDataUser.lastName())
                .usosProfileUrl(personalDataUser.usosProfileUrl())
                .studentStatus(personalDataUser.studentStatus())
                .phdStudentStatus(personalDataUser.phdStatus())
                .photoUrl(personalDataUser.photoUrl())
                .indexNumber(personalDataUser.indexNumber())
                .currentMajor(programmeDetails.currentMajor())
                .currentStageOfStudies(programmeDetails.currentStageOfStudies())
                .studiesType(programmeDetails.studiesType())
                .currentFaculty(programmeDetails.currentFaculty())
                .semester(currentSemester)
                .build();
    }
}
