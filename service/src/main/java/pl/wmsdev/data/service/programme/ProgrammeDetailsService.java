package pl.wmsdev.data.service.programme;

import pl.wmsdev.data.dto.ProgrammeDetails;

import java.util.concurrent.CompletableFuture;

public interface ProgrammeDetailsService {

    CompletableFuture<ProgrammeDetails> getProgrammeDetails();
}
