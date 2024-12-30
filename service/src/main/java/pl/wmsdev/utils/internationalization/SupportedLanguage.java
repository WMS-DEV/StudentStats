package pl.wmsdev.utils.internationalization;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Locale;

@Getter
@RequiredArgsConstructor
public enum SupportedLanguage {

    PL(new Locale("pl")),
    EN(new Locale("en"));

    private final Locale locale;

    public static SupportedLanguage valueOfLocaleLanguage(Locale locale) {
        for (SupportedLanguage language : values()) {
            if (language.getLocale().getLanguage().equals(locale.getLanguage())) {
                return language;
            }
        }
        return null;
    }
}
