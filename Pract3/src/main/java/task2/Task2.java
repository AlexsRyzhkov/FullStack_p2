package task2;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

import java.util.Random;

public class Task2 {

    public static void main(String[] args) {
        Random rand = new Random();

        Observable<Integer> randStream = Observable
                .range(1, rand.nextInt(0,1000))
                .map(i -> rand.nextInt(1000));

        Single<Long> countStream = randStream.count();

        var _ = countStream.subscribe(System.out::println);
    }
}
