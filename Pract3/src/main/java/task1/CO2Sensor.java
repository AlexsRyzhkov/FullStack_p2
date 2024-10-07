package task1;

import io.reactivex.rxjava3.core.Observable;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CO2Sensor {

    private final Random random = new Random();

    public Observable<Integer> getObservable() {
        return Observable.interval(1, TimeUnit.SECONDS)
                .map(tick -> random.nextInt(30,100)); // Случайное значение от 30 до 100
    }
}
