package task4;

import io.reactivex.rxjava3.core.Observable;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class FileGenerator {
    private final Random rand = new Random();

    public Observable<File> generateFile() {
        return Observable.create(emitter -> {
            while (!emitter.isDisposed()) {
                FileType type = FileType.values()[rand.nextInt(FileType.values().length)];
                // Генерация случайного размера файла (от 10 до 100)
                int size = rand.nextInt(10,100);

                File file = new File(type, size);
                emitter.onNext(file);

                int delay = rand.nextInt(100,1000);
                TimeUnit.MILLISECONDS.sleep(delay);
            }
        });
    }
}
