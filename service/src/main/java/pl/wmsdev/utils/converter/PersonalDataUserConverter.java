package pl.wmsdev.utils.converter;

import org.springframework.stereotype.Component;
import pl.wmsdev.data.dto.PersonalDataUser;
import pl.wmsdev.usos.values.UsosStudentStatus;
import pl.wmsdev.usos4j.model.user.UsosUser;

@Component
public class PersonalDataUserConverter {

    public PersonalDataUser toPersonalDataUser(UsosUser user) {
        return PersonalDataUser.builder()
                .firstName(user.firstName())
                .lastName(user.lastName())
                .usosProfileUrl(user.profileUrl())
                .studentStatus(getStudentStatus(user.studentStatus()))
                .phdStatus(getStudentStatus(user.phdStudentStatus()))
                .photoUrl(getPhotoUrl(user))
                .indexNumber(Integer.parseInt(user.studentNumber()))
                .build();
    }

    private UsosStudentStatus getStudentStatus(Integer status) {
        for (UsosStudentStatus studentStatus : UsosStudentStatus.values()) {
            if (studentStatus.getValue() == status) {
                return studentStatus;
            }
        }
        return UsosStudentStatus.NOT_A_STUDENT; // invalid student status
    }

    private String getPhotoUrl(UsosUser user) {
        return user.photoUrls().values().stream().findFirst().orElse("");
    }
}
