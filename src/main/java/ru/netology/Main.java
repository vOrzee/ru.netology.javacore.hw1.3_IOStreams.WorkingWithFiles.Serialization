package ru.netology;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    private static final String SAVE_PATH = "D://tmp/Games/savegames/";

    public static void main(String[] args) {

        GameProgress firstEx = new GameProgress(99, 65, 90, 5.5);
        GameProgress secondEx = new GameProgress(105, 155, 98, 15.5);
        GameProgress thirdEx = new GameProgress(356, 75, 95, 9.5);

        List<String> listSave = new ArrayList<>(Arrays.asList(SAVE_PATH + "firstSave.dat", SAVE_PATH + "secondSave.dat", SAVE_PATH + "thirdSave.dat"));

        saveGame(listSave.get(0), firstEx);
        saveGame(listSave.get(1), secondEx);
        saveGame(listSave.get(2), thirdEx);

        zipFiles(SAVE_PATH + "saves.zip", listSave);

    }

    public static void saveGame(String fullPathToTheFile, GameProgress progress) {
        try (FileOutputStream fos = new FileOutputStream(fullPathToTheFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(progress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String fullPathToTheFile, List<String> listPathZippingObjects) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(fullPathToTheFile))) {
            for (String savingFile : listPathZippingObjects) {
                try (FileInputStream fis = new FileInputStream(savingFile)) {
                    String[] logNameSplit = savingFile.split("/");
                    String savingFileName = logNameSplit[logNameSplit.length - 1];
                    ZipEntry entry = new ZipEntry(savingFileName);
                    zos.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    if (fis.read(buffer) == 0) {
                        System.out.println("File " + savingFile + " is empty!!!");
                    } else {
                        new File(savingFile).deleteOnExit();
                    }
                    zos.write(buffer);
                    zos.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}