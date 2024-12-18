package task2;

import java.util.Random;
import java.util.stream.IntStream;

public class Task2 {

    public static void main(String[] args) {
    }


    public static void taskOnly500() {
        Random random = new Random();

        IntStream randomNumbers = random.ints(1000, 0, 1001);

        randomNumbers
                .filter(num -> num > 500)
                .forEach(System.out::println);
    }

    public static void combineStream() {
        Random random = new Random();

        IntStream stream1 = random.ints(1000, 0, 10);

        IntStream stream2 = random.ints(1000, 0, 10);

        IntStream combinedStream = IntStream.concat(stream1, stream2);

        combinedStream.forEach(System.out::print);
    }

    public static void firstFive() {
        Random random = new Random();

        IntStream randomNumbers = random.ints(10, 0, 1001);

        randomNumbers
                .limit(5)
                .forEach(System.out::println);
    }
}
