package ru.netology;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS");
    static List<String> resultAction = new ArrayList<>(Arrays.asList("создано", "создание не удалось"));
    static List<String> type = new ArrayList<>(Arrays.asList("файл", "директория"));
    static String pathPrefix = "D://tmp/Games/";
    public static void main(String[] args) {

        StringBuilder logger = new StringBuilder();

        List<String> dirGames = new ArrayList<>(Arrays.asList("src", "res", "savegames", "temp"));
        List<String> dirSrc = new ArrayList<>(Arrays.asList("src/main", "src/test"));
        List<String> dirMain = new ArrayList<>(Arrays.asList("src/main/Main.java", "src/main/Utils.java"));
        List<String> dirRes = new ArrayList<>(Arrays.asList("res/drawables", "res/vectors", "res/icons"));
        String logFileName = pathPrefix + "temp/temp.txt";

        creatorPath(dirGames, logger);
        creatorPath(dirSrc, logger);
        creatorFile(pathPrefix + dirMain.get(0), logger);
        creatorFile(pathPrefix + dirMain.get(1), logger);
        creatorPath(dirRes, logger);
        creatorFile(logFileName, logger);

        try (FileWriter writer = new FileWriter(logFileName, true)) {
            // запись всей строки
            writer.write(logger.toString());
            // дозаписываем и очищаем буфер
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static String getLogMessageStart(String type, String name) {
        if (name.isEmpty()) name = "EmptyName";
        String[] logNameSplit = name.split("/");
        String logName = logNameSplit[logNameSplit.length-1];
        return "\n" + LocalDateTime.now().format(dateTimeFormatter) + " - " + type + " " + logName + " - ";
    }

    public static void creatorPath(List<String> list, StringBuilder logger) {
        for(String dirName : list) {
            logger.append(getLogMessageStart(type.get(1), dirName));
            if (new File(pathPrefix + dirName).mkdir()) {
                logger.append(resultAction.get(0));
            }
            else {
                logger.append(resultAction.get(1));
            }
        }
    }

    public static void creatorFile(String file, StringBuilder logger) {
        try {
            logger.append(getLogMessageStart(type.get(0), file));
            if (new File(file).createNewFile()) {
                logger.append(resultAction.get(0));
            }
            else {
                logger.append(resultAction.get(1));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}