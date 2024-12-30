package pl.wmsdev.data.dto;

import lombok.Builder;
import pl.wmsdev.usos.values.UsosStudentStatus;

@Builder
public record PersonalDataUser(String firstName, String lastName, String usosProfileUrl,
                               UsosStudentStatus studentStatus, UsosStudentStatus phdStatus, String photoUrl,
                               Integer indexNumber) {
}
