package pl.wmsdev.data.service.ects;

import pl.wmsdev.usos.model.Semester;
import pl.wmsdev.usos4j.client.UsosUserAPI;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface EctsService {
    CompletableFuture<Void> addEcts(UsosUserAPI userApi, List<Semester> semesters);
}
