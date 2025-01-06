package pl.wmsdev.data.service.programme;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.wmsdev.data.dto.ProgrammeDetails;
import pl.wmsdev.usos4j.client.UsosUserAPI;
import pl.wmsdev.usos4j.model.progs.UsosProgramme;
import pl.wmsdev.utils.converter.ProgrammeDetailsConverter;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ProgrammeDetailsServiceImpl implements ProgrammeDetailsService {

    private final UsosUserAPI userApi;
    private final ProgrammeDetailsConverter programmeDetailsConverter;

    @Async
    @Override
    public CompletableFuture<ProgrammeDetails> getProgrammeDetails() {
        UsosProgramme programme = userApi.progs().student().get(0).programme(); // Every student has at least 1 programme

        return CompletableFuture.completedFuture(programmeDetailsConverter.toProgrammeDetails(programme));
    }
}
