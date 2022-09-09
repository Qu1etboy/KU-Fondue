package ku.cs.models;

import ku.cs.TestDataSource;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Appearance {
    private List<String> themes;
    private List<String> fonts;
    private List<String> fontSizes;

    public Appearance() {
        themes = new ArrayList<>();
        fonts = new ArrayList<>();
        fontSizes = new ArrayList<>();

        addTheme();
        addFont();
        addFontSize();

//        readThemeFile();
//        readFontFile();
//        readFontSizeFile();
    }
    public void addTheme() {
        themes.add("dark");
        themes.add("light");
    }

    public void addFont() {
        fonts.add("Helvetica");
        fonts.add("Kanit");
        fonts.add("Sarabun");
    }

    public void addFontSize() {
        fontSizes.add("12px");
        fontSizes.add("16px");
        fontSizes.add("18px");
        fontSizes.add("20px");
        fontSizes.add("24px");
    }
//    public void readThemeFile() {
//        // read all file from themes directory
//        File folder = null;
//        try {
//            folder = new File(TestDataSource.class.getResource("/ku/cs/css/themes").toURI());
//            File[] files = folder.listFiles();
//            if (files == null) {
//                return;
//            }
//            for (File file : files) {
//                if (file.isFile()) {
//                    // get file name without fil extension
//                    themes.add(file.getName().substring(0, file.getName().length() - 4));
//                    // System.out.println("File: " + file.getName().substring(0, file.getName().length() - 4));
//                }
//            }
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public List<String> getAllTheme() {
        return themes;
    }

//    public void readFontFile() {
//        // read all file from fonts directory
//        File folder = null;
//        try {
//            folder = new File(TestDataSource.class.getResource("/ku/cs/css/fonts").toURI());
//            File[] files = folder.listFiles();
//            if (files == null) {
//                return;
//            }
//            for (File file : files) {
//                if (file.isFile()) {
//                    // get file name without fil extension
//                    fonts.add(file.getName().substring(0, file.getName().length() - 4));
//                    // System.out.println("File: " + file.getName().substring(0, file.getName().length() - 4));
//                }
//            }
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public List<String> getAllFont() { return fonts; }

//    public void readFontSizeFile() {
//        // read all file from fonts directory
//        File folder = null;
//        try {
//            folder = new File(TestDataSource.class.getResource("/ku/cs/css/fontSize").toURI());
//            File[] files = folder.listFiles();
//            if (files == null) {
//                return;
//            }
//            for (File file : files) {
//                if (file.isFile()) {
//                    // get file name without fil extension
//                    fontSizes.add(file.getName().substring(0, file.getName().length() - 4));
//                    // System.out.println("File: " + file.getName().substring(0, file.getName().length() - 4));
//                }
//            }
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public List<String> getAllFontSize() { return fontSizes; }
}
