package pl.wmsdev.utils.converter;

import org.springframework.stereotype.Component;
import pl.wmsdev.data.dto.StudiesProgressDetails;

import java.time.LocalDate;

@Component
public class ProgressDetailsConverter {

    public StudiesProgressDetails toStudiesProgressDetails(LocalDate firstDayOfStudies, Double studiesProgress,
                                                           Double semesterProgress) {
        return StudiesProgressDetails.builder()
                .firstDayOfStudies(firstDayOfStudies)
                .studiesProgress(studiesProgress)
                .currentSemesterProgress(semesterProgress)
                .build();
    }
}
