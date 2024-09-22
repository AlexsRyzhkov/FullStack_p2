import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import static java.nio.file.StandardWatchEventKinds.*;
import java.util.stream.Stream;

public class Task4 {

    public static void main(String[] args) {
        Path directoryPath = Paths.get("watch");

        try{
            WatchService watchService = FileSystems.getDefault().newWatchService();

            directoryPath.register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);

            System.out.println("Наблюдение за каталогом началось...");

            HashMap<String, List<String>> oldFiles = readFiles(directoryPath);
            HashMap<String, Integer> oldFilesSize = getFilesSize(directoryPath);

            while (true) {

                WatchKey key;

                try {
                    key = watchService.take(); // Ожидание события
                } catch (InterruptedException ex) {
                    System.out.println("Наблюдение было прервано");
                    return;
                }

                // Обрабатываем события
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    System.out.println(kind.name());


                    // Убедитесь, что это событие не является утерянным
                    if (kind == StandardWatchEventKinds.OVERFLOW) {
                        continue;
                    }

                    // Проверяем, что событие является изменением файла
                    if (kind == ENTRY_MODIFY) {
                        // Получаем имя измененного файла
                        WatchEvent<Path> ev = (WatchEvent<Path>) event;
                        Path filename = ev.context();
                        String name = filename.toString().replace("~", "");

                        Path filePath = directoryPath.resolve(name);
                        // Сравниваем старое и новое состояние файла
                        List<String> newFile = Files.readAllLines(filePath);
                        List<String> oldFile = oldFiles.get(filePath.toString());

//                        newFile.forEach(System.out::println);
//                        System.out.println();

                        var addedAndRemovedString = FileHelper.addedAndRemovedStrings(
                                oldFile,
                                newFile
                        );
                        // Определяем добавленные и удаленные строки

                        // Выводим изменения
//                        if (!addedAndRemovedString.getFirst().isEmpty()) {
//                            System.out.println();
//                            System.out.println("Добавленные строки:");
//                            addedAndRemovedString.getFirst().forEach(System.out::println);
//                        }
//
//                        if (!addedAndRemovedString.get(1).isEmpty()) {
//                            System.out.println();
//                            System.out.println("Удаленные строки:");
//                            addedAndRemovedString.get(1).forEach(System.out::println);
//                        }

                        oldFiles.put(filePath.toString(),newFile);

                    }else if (kind == ENTRY_CREATE) {
                        // Получаем имя нового файла
                        WatchEvent<Path> ev = (WatchEvent<Path>) event;
                        Path filename = ev.context();

                        if (!filename.toString().contains("~")){
                            Path createPath = directoryPath.resolve(filename);

                            if (Files.isRegularFile(createPath)){
                                System.out.println();
                                System.out.printf("Создан новый файл: %s%n", filename);

                                oldFiles = readFiles(directoryPath);
                                oldFilesSize = getFilesSize(directoryPath);
                            }
                        }

                    }else if (kind == ENTRY_DELETE) {
                        WatchEvent<Path> ev = (WatchEvent<Path>) event;
                        Path filename = ev.context();
                        Path filePath = directoryPath.resolve(filename);

                        if (!filename.toString().contains("~")){
                            System.out.println("Размер файла: " + oldFilesSize.get(filePath.toString()) + " байт");

                            System.out.println("Контрольная сумма: "+Task3.getCheckSum(oldFiles.get(filePath.toString())));

                            oldFiles = readFiles(directoryPath);
                            oldFilesSize = getFilesSize(directoryPath);
                        }
                    }
                }

                // Сбрасываем ключ и проверяем, доступен ли он для дальнейшего отслеживания
                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    public static HashMap<String, List<String>> readFiles(Path folderPath) {
        HashMap<String, List<String>> fileContents = new HashMap<>();

        try (Stream<Path> paths = Files.list(folderPath)) {
            paths.filter(Files::isRegularFile) // Отфильтровываем только файлы
                    .forEach(filePath -> {
                        try {
                            List<String> content = Files.readAllLines(filePath);

                            if (content.isEmpty()) {
                                fileContents.put(filePath.toString(), new ArrayList<>());
                            }else{
                                fileContents.put(filePath.toString(),content);
                            }
                        } catch (IOException e) {
                            System.err.println("Ошибка при чтении файла" + filePath + ": " + e.getMessage());
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileContents;
    }

    public static HashMap<String, Integer> getFilesSize(Path folderPath) {
        HashMap<String, Integer> fileSizes = new HashMap<>();

        try (Stream<Path> paths = Files.list(folderPath)) {
            paths.filter(Files::isRegularFile) // Отфильтровываем только файлы
                    .forEach(filePath -> {
                        File file = new File(filePath.toString());

                        fileSizes.put(filePath.toString(), (int) file.length());
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileSizes;
    }
}
