package ku.cs.models;

import ku.cs.TestDataSource;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Appearance {
    private List<String> themes;

    public Appearance() {
        themes = new ArrayList<>();
        readThemeFile();
    }

    public void readThemeFile() {
        // read all file from themes directory
        File folder = null;
        try {
            folder = new File(TestDataSource.class.getResource("/ku/cs/css/themes").toURI());
            File[] files = folder.listFiles();
            if (files == null) {
                return;
            }
            for (File file : files) {
                if (file.isFile()) {
                    // get file name without fil extension
                    themes.add(file.getName().substring(0, file.getName().length() - 4));
                    // System.out.println("File: " + file.getName().substring(0, file.getName().length() - 4));
                }
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getAllTheme() {
        return themes;
    }

}
