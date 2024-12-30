package pl.wmsdev.utils.internationalization;

import pl.wmsdev.usos4j.model.common.UsosLocalizedString;

public interface LocalizedMessageService {

    String getMessage(String key, SupportedLanguage language);

    String getMessageWithArgs(String key, SupportedLanguage language, Object... args);

    String getMessageFromContext(String key);

    String getMessageWithArgsFromContext(String key, Object... args);

    SupportedLanguage getLanguageFromContextOrDefault();

    String getLocalized(UsosLocalizedString text);
}
