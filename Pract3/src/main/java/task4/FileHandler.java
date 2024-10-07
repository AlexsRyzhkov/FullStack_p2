package task4;

import java.util.concurrent.TimeUnit;

public class FileHandler {
    private final FileType fileType;


    public FileHandler(FileType fileType) {
        this.fileType = fileType;
    }

    public void handleFile(File file) {
        if (file.type == fileType) {
            System.out.println("Обработка файла: " + file);
            try {
                // Время обработки файла: размер * 7 мс
                TimeUnit.MILLISECONDS.sleep(file.size * 7);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Файл обработан: " + file);
        } else {
            System.out.println("Обработчик типа " + fileType + " пропустил файл: " + file);
        }
    }
}
