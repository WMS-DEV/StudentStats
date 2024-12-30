package pl.wmsdev.usos.cards.creators;

import pl.wmsdev.data.model.StudentStatsObject;
import pl.wmsdev.usos.model.Studies;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface UsosCardCreator {

    default List<StudentStatsObject> getAlwaysPresentCards(List<Studies> studies) {
        return Collections.emptyList();
    }

    default List<Optional<StudentStatsObject>> getOptionalCards(List<Studies> studies) {
        return Collections.emptyList();
    }

}
