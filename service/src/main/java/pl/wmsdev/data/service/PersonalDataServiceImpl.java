package pl.wmsdev.data.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wmsdev.data.dto.PersonalDataUser;
import pl.wmsdev.data.dto.ProgrammeDetails;
import pl.wmsdev.data.service.personal.PersonalDataUserService;
import pl.wmsdev.data.service.programme.ProgrammeDetailsService;
import pl.wmsdev.data.service.progress.ProgressService;
import pl.wmsdev.data.service.studies.StudiesService;
import pl.wmsdev.universities.service.UniversitiesService;
import pl.wmsdev.usos.model.PersonalData;
import pl.wmsdev.usos.model.Studies;
import pl.wmsdev.utils.converter.PersonalDataConverter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonalDataServiceImpl implements PersonalDataService {

    private final StudiesService studiesService;
    private final PersonalDataUserService personalDataUserService;
    private final ProgrammeDetailsService programmeDetailsService;
    private final ProgressService progressService;
    private final UniversitiesService universitiesService;
    private final PersonalDataConverter personalDataConverter;

    @SneakyThrows
    @Override
    public PersonalData getPersonalData() {
        CompletableFuture<List<Studies>> studies = studiesService.getStudies();
        CompletableFuture<PersonalDataUser> personalDataUser = personalDataUserService.getPersonalDataUser();
        CompletableFuture<ProgrammeDetails> programmeDetails = programmeDetailsService.getProgrammeDetails();
        CompletableFuture<Integer> currentSemester = progressService.getCurrentSemesterNumber();

        CompletableFuture.allOf(studies, personalDataUser, programmeDetails, currentSemester).join();

        String universityName = universitiesService.getUsersUniversityName();

        return personalDataConverter.toPersonalData(universityName, studies.get(), personalDataUser.get(),
                programmeDetails.get(), currentSemester.get());
    }
}
