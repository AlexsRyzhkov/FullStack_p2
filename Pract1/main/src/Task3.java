import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

class File {
    public long id;
    public String type; // Тип файла
    public int size; // Размер файла

    File(String type, int size, long id) {
        this.type = type;
        this.size = size;
        this.id = id;
    }
}

public class Task3 {


    public static void main(String[] args) {
        Random rand = new Random();
        ExecutorService executor = Executors.newScheduledThreadPool(2);
        BlockingQueue<File> queue = new LinkedBlockingQueue<>(5);

        AtomicLong id = new AtomicLong();

        executor.submit(() -> {
            while (true) {
                File file = new File("json", rand.nextInt(10, 100), id.incrementAndGet());
                queue.put(file);
                System.out.println("Добавлен элемент - " + file.id);
                TimeUnit.MILLISECONDS.sleep(rand.nextInt(100, 1000));
            }
        });

        executor.submit(() -> {
            while (true) {
                File file = queue.take();
                TimeUnit.MILLISECONDS.sleep(file.size * 7L);
                System.out.println("Извлечен элемент ----- " + file.id);
            }
        });
    }
}
