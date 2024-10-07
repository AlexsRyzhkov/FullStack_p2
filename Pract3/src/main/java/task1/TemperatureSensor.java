package task1;

import io.reactivex.rxjava3.core.Observable;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TemperatureSensor {
    private final Random random = new Random();

    public Observable<Integer> getObservable() {
        return Observable.interval(1, TimeUnit.SECONDS)
                .map(tick -> random.nextInt(15,30)); // Случайное значение от 15 до 30
    }
}
