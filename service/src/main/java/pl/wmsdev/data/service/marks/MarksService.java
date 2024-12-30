package pl.wmsdev.data.service.marks;

import pl.wmsdev.usos.model.Semester;
import pl.wmsdev.usos4j.client.UsosUserAPI;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface MarksService {

    CompletableFuture<Void> addMarks(UsosUserAPI userApi, List<Semester> semesters, String[] semesterIds);
}
