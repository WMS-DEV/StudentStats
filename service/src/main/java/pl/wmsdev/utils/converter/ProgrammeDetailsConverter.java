package pl.wmsdev.utils.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wmsdev.data.dto.ProgrammeDetails;
import pl.wmsdev.usos4j.model.progs.UsosProgramme;
import pl.wmsdev.utils.internationalization.LocalizedMessageService;

@Component
@RequiredArgsConstructor
public class ProgrammeDetailsConverter {

    private final LocalizedMessageService msgService;

    public ProgrammeDetails toProgrammeDetails(UsosProgramme programme) {
        return ProgrammeDetails.builder()
                .currentMajor(getCurrentMajor(programme))
                .currentStageOfStudies(msgService.getLocalized(programme.levelOfStudies()))
                .studiesType(msgService.getLocalized(programme.modeOfStudies()))
                .currentFaculty(getCurrentFaculty(programme))
                .build();
    }

    private String getCurrentMajor(UsosProgramme programme) {
        try {
            return msgService.getLocalized(programme.name()).split(",")[0];
        } catch (ArrayIndexOutOfBoundsException ignored) {
            return "-"; // Could not parse programme name
        }
    }

    private String getCurrentFaculty(UsosProgramme programme) {
        return msgService.getLocalized(programme.allFaculties().get(0).name()); // Every programme has at least 1 faculty
    }
}
