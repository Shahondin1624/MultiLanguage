package internal;

import listener.MonitoredLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Locale;
import java.util.ResourceBundle;

public class MultiLanguageSystem {

    private static MultiLanguageSystem instance;
    private final Logger logger = LoggerFactory.getLogger(MultiLanguageSystem.class);
    private Configuration configuration;
    private String bundle;
    private Path missingKeys;

    private MultiLanguageSystem(Locale... locales) {
        configuration = new Configuration(locales);
    }

    public static MultiLanguageSystem init(String resourceBundle, Path missingKeys, Locale... locales) throws IOException {
        instance = new MultiLanguageSystem(locales);
        instance.bundle = resourceBundle;
        ResourceBundle.getBundle(instance.bundle, instance.configuration.getActive());

        instance.missingKeys = missingKeys;
        if (instance.missingKeys != null) {
            if (Files.exists(instance.missingKeys)) {
                Files.delete(instance.missingKeys);
            }
            Files.createFile(instance.missingKeys);
        }
        return instance;
    }

    public void addLanguage(Locale locale) {
        configuration.addLanguage(locale);
        logger.debug("Added {} to languages", locale.getLanguage());
    }

    public Collection<Locale> getLanguages() {
        return configuration.getLanguages();
    }

    public Locale getActive() {
        return configuration.getActive();
    }

    public void setActive(Locale locale) throws LanguageNotFoundException {
        configuration.setActive(locale);
        logger.debug("Set {} as the active language", locale.getLanguage());
        logger.debug("Querying update of monitored Labels");
        configuration.getRegistry().getMonitored().forEach((key, value) -> {
            String updatedTranslation = translate(key);
            value.update(updatedTranslation);
        });
    }

    public String translate(String key) {
        ResourceBundle rb = ResourceBundle.getBundle(bundle, configuration.getActive());
        String translation = key;
        if (rb.containsKey(key)) {
            translation = rb.getString(key);
        } else {
            try {
                logger.warn("Key {} is missing", key);
                if (instance.missingKeys != null) {
                    Files.writeString(missingKeys, key + "=", StandardOpenOption.APPEND);
                }
            } catch (IOException e) {
                logger.warn("Could not write {} to {} because of\n{}", key, missingKeys.toString(), StackTraceWriter.transformStackTrace(e));
            }
        }
        return translation;
    }

    public static MultiLanguageSystem getInstance() {
        return instance;
    }

    public void register(String key, MonitoredLabel label) {
        configuration.register(key, label);
    }

}
