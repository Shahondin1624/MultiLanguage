package internal;

import listener.MonitoredLabel;
import listener.MultiLanguageListenerRegistry;

import java.util.*;

public class Configuration {
    private Locale active;
    private final Set<Locale> languages = new HashSet<>();
    private final MultiLanguageListenerRegistry registry = new MultiLanguageListenerRegistry();

    protected Configuration(Locale... locales) {
        if (locales == null || locales.length < 1) {
            throw new IllegalArgumentException("Must at least pass one language");
        }
        languages.addAll(List.of(locales));
        active = locales[0];
    }

    public Locale getActive() {
        return active;
    }

    public void setActive(Locale locale) throws LanguageNotFoundException {
        if (languages.contains(locale)) {
            active = locale;
        } else throw new LanguageNotFoundException(locale.getLanguage() + " not in languages");
    }

    public Set<Locale> getLanguages() {
        return languages;
    }

    public void addLanguage(Locale language) {
        languages.add(language);
    }

    public MultiLanguageListenerRegistry getRegistry() {
        return registry;
    }

    public void register(String key, MonitoredLabel label) {
        registry.registerLabel(key, label);
    }

    public void remove(String key) {
        registry.removeLabel(key);
    }
}
