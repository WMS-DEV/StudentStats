package pl.wmsdev.utils.internationalization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import pl.wmsdev.usos4j.model.common.UsosLocalizedString;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocalizedMessageServiceImpl implements LocalizedMessageService {

    @Value("${language.default}")
    private SupportedLanguage DEFAULT_LANGUAGE;

    private final MessageSource messageSource;

    @Override
    public String getMessage(String key, SupportedLanguage language) {
        try {
            return messageSource.getMessage(key, null, language.getLocale());
        } catch (NoSuchMessageException e) {
            log.error(e.getMessage());
            return key;
        }
    }

    @Override
    public String getMessageWithArgs(String key, SupportedLanguage language, Object... args) {
        try {
            return messageSource.getMessage(key, args, language.getLocale());
        } catch (NoSuchMessageException e) {
            log.error(e.getMessage());
            return key;
        }
    }

    public String getMessageFromContext(String key){
        return getMessage(key, getLanguageFromContextOrDefault());
    }

    public String getMessageWithArgsFromContext(String key, Object... args) {
        return getMessageWithArgs(key, getLanguageFromContextOrDefault(), args);
    }

    public String getLocalized(UsosLocalizedString text) {
        var locale = getLanguageFromContextOrDefault();
        switch (locale) {
            case EN -> { return text.en(); }
            case PL -> { return text.pl(); }
        }
        return text.en();
    }

    @Override
    public SupportedLanguage getLanguageFromContextOrDefault() {
        SupportedLanguage language = SupportedLanguage.valueOfLocaleLanguage(LocaleContextHolder.getLocale());
        return language == null ? DEFAULT_LANGUAGE : language;
    }
}
