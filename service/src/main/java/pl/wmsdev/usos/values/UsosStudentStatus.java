package pl.wmsdev.usos.values;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UsosStudentStatus {
    NOT_A_STUDENT(0),
    INACTIVE_STUDENT(1),
    ACTIVE_STUDENT(2);

    private final int value;
}
