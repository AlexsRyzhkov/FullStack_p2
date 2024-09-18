import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import static java.nio.file.StandardWatchEventKinds.*;
import java.util.stream.Stream;

public class Task4 {

    public static void main(String[] args) {
        Path directoryPath = Paths.get("watch");


        try{
            WatchService watchService = FileSystems.getDefault().newWatchService();

            directoryPath.register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
            HashMap<String, List<String>> oldFiles = readFiles(directoryPath);

            System.out.println("Наблюдение за каталогом началось...");

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

                        // Создаем копии списков для симметрической разности
                        List<String> addLines = new ArrayList<>(newFile);
                        List<String> delLines = new ArrayList<>(oldFile);

                        // Удаляем из difference1 элементы, которые есть в list2 (разность list1 - list2)
                        addLines.removeAll(oldFile);

                        System.out.println();
                        System.out.println("addLines");
                        addLines.forEach(System.out::println);
                        System.out.println();
                        System.out.println("New");
                        newFile.forEach(System.out::println);
                        System.out.println();
                        System.out.println("Old");
                        oldFile.forEach(System.out::println);

                        // Удаляем из difference2 элементы, которые есть в list1 (разность list2 - list1)
                        delLines.removeAll(newFile);

//                        // Определяем добавленные и удаленные строки
//
//                        // Выводим изменения
//                        if (!addLines.isEmpty()) {
//                            System.out.println();
//                            System.out.println("Добавленные строки:");
//                            addLines.forEach(System.out::println);
//                        }
//
//                        if (!delLines.isEmpty()) {
//                            System.out.println();
//                            System.out.println("Удаленные строки:");
//                            delLines.forEach(System.out::println);
//                        }

                        oldFiles.put(filePath.toString(), newFile);

                    }else if (kind == ENTRY_CREATE) {
                        // Получаем имя нового файла
                        WatchEvent<Path> ev = (WatchEvent<Path>) event;
                        Path filename = ev.context();

                        if (!filename.toString().contains("~")){
                            Path createPath = directoryPath.resolve(filename);

                            if (Files.isRegularFile(createPath)){
                                System.out.println();
                                System.out.printf("Создан новый файл: %s%n", filename);
                            }
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
        }

    }

    public static HashMap<String, List<String>> readFiles(Path folderPath) {
        HashMap<String, List<String>> fileContents = new HashMap<>();

        try (Stream<Path> paths = Files.list(folderPath)) {
            paths.filter(Files::isRegularFile) // Отфильтровываем только файлы
                    .forEach(filePath -> {
                        try {
                            List<String> content = Files.readAllLines(filePath);

                            fileContents.put(filePath.toString(),content);
                        } catch (IOException e) {
                            System.err.println("Ошибка при чтении файла" + filePath + ": " + e.getMessage());
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileContents;
    }
}
