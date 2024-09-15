import javax.management.timer.Timer;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Task2 {

    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(4);

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        while (true) {
            int n = scanner.nextInt();
            executor.schedule(() -> {
                System.out.println(n * n);
            }, random.nextInt(1, 5), TimeUnit.SECONDS);
        }

    }

}
