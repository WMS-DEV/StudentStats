package pl.wmsdev.usos.cards.service;

import org.springframework.context.i18n.LocaleContext;
import pl.wmsdev.data.model.StudentStatsObject;
import pl.wmsdev.usos.model.PersonalData;

import java.util.List;

public interface UsosCardService {

    List<StudentStatsObject> getCards(PersonalData personalData, LocaleContext localeContext);

}
