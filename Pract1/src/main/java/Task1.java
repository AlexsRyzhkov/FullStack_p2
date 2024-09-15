import java.util.ArrayList;
import java.util.concurrent.*;

public class Task1 {

    public static ArrayList<Integer> generateArray() {
        ArrayList<Integer> array = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            array.add(1);
        }

        return array;
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ArrayList<Integer> arr = generateArray();

        Runtime runtime = Runtime.getRuntime();
        long memoryBefore = 0;
        long memoryAfter = 0;
        long memoryUsed = 0;


        long startTime = 0;
        long duration = 0;

        memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        startTime = System.currentTimeMillis();

        System.out.println("Сумма последовательного суммирования: " + syncSum(arr));

        duration = System.currentTimeMillis() - startTime;
        memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        memoryUsed = memoryAfter - memoryBefore;

        System.out.println("Использовано памяти (в КБ): " + memoryUsed / 1024);
        System.out.println("Затрачено времени (в мс): " + duration);

        runtime.gc();
        Thread.sleep(1);

        memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        startTime = System.currentTimeMillis();

        System.out.println("Сумма паралельного суммирования: " + threadSum(arr));

        duration = System.currentTimeMillis() - startTime;
        memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        memoryUsed = memoryAfter - memoryBefore;
        System.out.println("Использовано памяти (в КБ): " + memoryUsed / 1024);
        System.out.println("Затрачено времени (в мс): " + duration);

        runtime.gc();
        Thread.sleep(1);

        memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        startTime = System.currentTimeMillis();

        System.out.println("Сумма паралельного суммирования(forkJoin): " + forkJoinThreadsSum(arr));

        duration = System.currentTimeMillis() - startTime;
        memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        memoryUsed = memoryAfter - memoryBefore;
        System.out.println("Использовано памяти (в КБ): " + memoryUsed / 1024);
        System.out.println("Затрачено времени (в мс): " + duration);
    }

    public static int syncSum(ArrayList<Integer> arr) {
        int sum = 0;

        for (Integer a : arr) {
            sum += a;
        }

        return sum;
    }

    public static int threadSum(ArrayList<Integer> arr) throws ExecutionException, InterruptedException {
        int threadPool = 3;
        ExecutorService executor = Executors.newFixedThreadPool(threadPool);

        int chunkSize = arr.size() / threadPool;
        ArrayList<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < threadPool; i++) {
            int start = i * chunkSize;
            int end = (i == threadPool - 1) ? arr.size() : start + chunkSize;

            Callable<Integer> task = () -> {
                int sum = 0;

                for (int j = start; j < end; j++) {
                    sum += arr.get(j);
                }

                return sum;
            };

            futures.add(executor.submit(task));
        }

        int totalSum = 0;
        for (Future<Integer> future : futures) {
            totalSum += future.get();
        }

        executor.shutdown();

        return totalSum;

    }

    public static int forkJoinThreadsSum(ArrayList<Integer> arr) {
        ForkJoinPool pool = new ForkJoinPool();

        ForkJoinSum task = new ForkJoinSum(arr, 0, arr.size());

        return pool.invoke(task);
    }
}
