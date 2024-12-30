package pl.wmsdev.data.model;

import lombok.Builder;
import lombok.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import pl.wmsdev.data.values.StudentStatsCategory;
import pl.wmsdev.usos4j.model.common.UsosLocalizedString;
import pl.wmsdev.utils.internationalization.SupportedLanguage;

@Value
public class StudentStatsObject {
    private String type;
    private String category;
    private AbstractStudentStatsContent content;

    @Builder
    public StudentStatsObject(StudentStatsCategory category, AbstractStudentStatsContent content){
        this.type = content.getClass().getSimpleName().replaceAll("StudentStats", "")
                .replaceAll("([a-z])([A-Z])", "$1_$2")
                .toUpperCase();
        this.category = getLocalized(category.value());
        this.content = content;
    }

    public static String getLocalized(UsosLocalizedString text) {
        var locale = getLanguageFromContextOrDefault();
        switch (locale) {
            case EN -> { return text.en(); }
            case PL -> { return text.pl(); }
        }
        return text.en();
    }

    public static SupportedLanguage getLanguageFromContextOrDefault() {
        SupportedLanguage language = SupportedLanguage.valueOfLocaleLanguage(LocaleContextHolder.getLocale());
        return language == null ? SupportedLanguage.PL : language;
    }
}
