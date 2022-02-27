package tests;

import impls.TestLabel;
import internal.LanguageNotFoundException;
import internal.MultiLanguageSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

public class MultiLanguageTest {
    private static final String MISSING_KEYS = String.format("%s/src/test/resources/missingKeys.properties", //
            System.getProperty("user.dir"));
    private static MultiLanguageSystem MLS;
    private static final Locale GERMAN = Locale.GERMAN;
    private static final Locale ENGLISH = Locale.ENGLISH;
    protected static final String KEY_LABEL_1 = "/test/label/1";
    protected static final String KEY_LABEL_2 = "/test/label/2";
    protected static final String KEY_LABEL_3 = "/test/label/3";
    protected static final TestLabel LABEL_1 = new TestLabel(KEY_LABEL_1);
    protected static final TestLabel LABEL_2 = new TestLabel(KEY_LABEL_2);
    protected static final TestLabel LABEL_3 = new TestLabel(KEY_LABEL_3);

    @BeforeAll
    protected static void beforeAll() throws IOException, LanguageNotFoundException {
        MLS = MultiLanguageSystem.init("Localisation", Path.of(MISSING_KEYS), GERMAN, ENGLISH);
        MLS.setActive(ENGLISH);
        MLS.register(LABEL_1.getMlsKey(), LABEL_1);
        MLS.register(LABEL_2.getMlsKey(), LABEL_2);
    }

    @Test
    public void testCorrectLabels() {
        Assertions.assertEquals("Label_1_EN", LABEL_1.getValue());
        Assertions.assertEquals("Label_2_EN", LABEL_2.getValue());
    }

    @Test
    public void testLabelCorrectAfterLanguageChange() throws LanguageNotFoundException {
        MLS.setActive(GERMAN);
        Assertions.assertEquals("Label_1_DE", LABEL_1.getValue());
        Assertions.assertEquals("Label_2_DE", LABEL_2.getValue());
        MLS.setActive(ENGLISH);
    }

    @Test
    public void testMissingKeys() throws IOException {
        MLS.register(LABEL_3.getMlsKey(), LABEL_3);
        Assertions.assertTrue(Files.readString(MLS.getMissingKeys()).contains(LABEL_3.getMlsKey()));
    }
}
