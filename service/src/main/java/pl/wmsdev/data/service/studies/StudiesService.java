package pl.wmsdev.data.service.studies;

import pl.wmsdev.usos.model.Studies;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface StudiesService {
    CompletableFuture<List<Studies>> getStudies();
}
