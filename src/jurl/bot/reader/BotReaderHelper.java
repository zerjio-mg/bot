package jurl.bot.reader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BotReaderHelper {

    public static final String SCRIPTS_FOLDER = "./scripts";
    public static final String SCRIPTS_EXTENSION = "bot";
    public static final String CONTEXTS_FOLDER = "./contexts";
    public static final String CONTEXTS_EXTENSION = "java";

    public static List<String> findContexts() {
        return findScriptsInDirectory(CONTEXTS_FOLDER);
    }

    public static List<String> findContextsInDirectory(String directoryName) {
        return findFilesByExtension(directoryName, CONTEXTS_EXTENSION);
    }

    public static List<String> findScripts() {
        return findScriptsInDirectory(SCRIPTS_FOLDER);
    }

    public static List<String> findScriptsInDirectory(String directoryName) {
        return findFilesByExtension(directoryName, SCRIPTS_EXTENSION);
    }

    public static List<String> findFilesByExtension(String directoryName, String extension) {

        File directory = new File(directoryName);
        if (directory.exists() == false) {
            throw new RuntimeException(String.format("'%s' not found", directoryName));
        }
        if (directory.isDirectory() == false) {
            throw new RuntimeException(String.format("'%s' is not a directory", directoryName));
        }

        List<String> fileList = new ArrayList();

        findFilesByExtension(directory, extension.toLowerCase(), fileList );

        return fileList;
    }

    private static void findFilesByExtension(File file, String extension, List<String> fileList) {

        for (File f : file.listFiles()){
            if (f.isDirectory()) {
                findFilesByExtension(f, extension, fileList);
            } else {
                if (f.getName().toLowerCase().endsWith(extension)) {
                    fileList.add(f.getAbsolutePath());
                }
            }
        }
    }


}
