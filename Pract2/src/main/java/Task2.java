import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Task2 {

    public static void main(String[] args) {
        copyByFISFOS();
        copyByFileChannel();
        copyByApacheCommonsIO();
        copyByFilesClass();
    }


    public static void copyByFISFOS(){
        String sourcePath = "src/main/resources/100Mb.txt";
        String destinationPath = "src/main/resources/100Mb_copy_1.txt";

        try (FileInputStream fis = new FileInputStream(sourcePath);
             FileOutputStream fos = new FileOutputStream(destinationPath)) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            System.out.println("Файл успешно скопирован (1)!");

        } catch (IOException e) {
            System.err.println("Ошибка при копировании файла: " + e.getMessage());
        }

    }

    public static void copyByFileChannel(){
        String sourcePath = "src/main/resources/100Mb.txt";
        String destinationPath = "src/main/resources/100Mb_copy_2.txt";

        try (FileInputStream fis = new FileInputStream(sourcePath);
             FileOutputStream fos = new FileOutputStream(destinationPath);
             FileChannel sourceChannel = fis.getChannel();
             FileChannel destinationChannel = fos.getChannel()) {

            destinationChannel.transferFrom(sourceChannel, 0, sourceChannel.size());

            System.out.println("Файл успешно скопирован (2)!");

        } catch (IOException e) {
            System.err.println("Ошибка при копировании файла: " + e.getMessage());
        }
    }

    public static void copyByApacheCommonsIO(){
        File sourceFile = new File("src/main/resources/100Mb.txt");
        File destinationFile = new File("src/main/resources/100Mb_copy_3.txt");

        try {
            // Копирование файла
            FileUtils.copyFile(sourceFile, destinationFile);
            System.out.println("Файл успешно скопирован с использованием Apache Commons IO!");

        } catch (IOException e) {
            System.err.println("Ошибка при копировании файла: " + e.getMessage());
        }
    }

    public static void copyByFilesClass(){
        Path sourcePath =  Paths.get("src/main/resources/100Mb.txt");
        Path destinationPath =  Paths.get("src/main/resources/100Mb_copy_4.txt");

        try {
            // Копирование файла
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Файл успешно скопирован с использованием класса Files!");

        } catch (IOException e) {
            System.err.println("Ошибка при копировании файла: " + e.getMessage());
        }
    }
}
