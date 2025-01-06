package pl.wmsdev.usos.cards.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import pl.wmsdev.data.model.StudentStatsObject;
import pl.wmsdev.usos.cards.creators.UsosCardCreator;
import pl.wmsdev.usos.model.PersonalData;
import pl.wmsdev.usos.model.Semester;
import pl.wmsdev.usos.model.Studies;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
@Slf4j
public class UsosCardServiceImpl implements UsosCardService {

    private final List<UsosCardCreator> usosCardCreators;

    @Override
    public List<StudentStatsObject> getCards(PersonalData personalData, LocaleContext localeContext) {
        log.info("Creating cards for USOS student stats");
        LocaleContextHolder.setLocaleContext(localeContext);
        List<Studies> studies = personalData.getStudies();

        return hasCourses(studies) ? createCards(studies) : Collections.emptyList();
    }

    private boolean hasCourses(List<Studies> userStudies) {
        return userStudies.stream()
                .map(Studies::semesters)
                .flatMap(Collection::stream)
                .map(Semester::courses)
                .mapToLong(Collection::size)
                .sum() > 0;
    }

    private List<StudentStatsObject> createCards(List<Studies> userStudies) {
        var parsedOptionalCards = parseOptionalCards(userStudies);
        var alwaysPresentCards = parseAlwaysPresentCards(userStudies);

        return Stream.of(parsedOptionalCards, alwaysPresentCards)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<StudentStatsObject> parseOptionalCards(List<Studies> userStudies) {
        return usosCardCreators.stream()
                .map(creator -> creator.getOptionalCards(userStudies))
                .flatMap(Collection::stream)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private List<StudentStatsObject> parseAlwaysPresentCards(List<Studies> userStudies) {
        return usosCardCreators.stream()
                .map(creator -> creator.getAlwaysPresentCards(userStudies))
                .flatMap(Collection::stream)
                .toList();
    }
}
