package tests;

import internal.LanguageNotFoundException;
import internal.MultiLanguageSystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;

public class TestSystem {
    private static Path missingKeys;
    public static void main(String[] args) throws IOException, LanguageNotFoundException {
        String testResourcePath = new File("").getAbsolutePath() + "/src/test/resources";
        missingKeys = Path.of(testResourcePath + "/missingKeys.properties");
        MultiLanguageSystem system = MultiLanguageSystem.init("Localisation", missingKeys,
                new Locale("DE"),
                new Locale("EN"));
        system.setActive(new Locale("DE"));
        System.out.println(system.translate("/test/helloWorld"));
        System.out.println(system.translate("/test/test1"));
    }
}
