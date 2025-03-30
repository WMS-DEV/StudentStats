package pl.wmsdev.data.service.programme;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.wmsdev.data.dto.ProgrammeDetails;
import pl.wmsdev.usos4j.client.UsosUserAPI;
import pl.wmsdev.usos4j.model.progs.UsosProgramme;
import pl.wmsdev.usos4j.model.progs.UsosStudentProgramme;
import pl.wmsdev.utils.converter.ProgrammeDetailsConverter;
import pl.wmsdev.utils.exceptions.NotFoundException;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ProgrammeDetailsServiceImpl implements ProgrammeDetailsService {

    private final UsosUserAPI userApi;
    private final ProgrammeDetailsConverter programmeDetailsConverter;

    @Async
    @Override
    public CompletableFuture<ProgrammeDetails> getProgrammeDetails() {
        List<UsosStudentProgramme> programmes = userApi.progs().student();

        if (programmes.isEmpty()) {
            throw new NotFoundException("Student has no active study programme.");
        }

        UsosProgramme programme = programmes.get(0).programme();
        return CompletableFuture.completedFuture(programmeDetailsConverter.toProgrammeDetails(programme));
    }
}
