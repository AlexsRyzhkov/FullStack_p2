package task1;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class AlarmSystem {
    private static final int TEMPERATURE_THRESHOLD = 25;
    private static final int CO2_THRESHOLD = 70;

    private final CompositeDisposable disposables = new CompositeDisposable();

    public void monitor(Observable<Integer> temperatureObservable, Observable<Integer> co2Observable) {
        Observable<Boolean> temperatureWarning = temperatureObservable
                .map(temp -> temp > TEMPERATURE_THRESHOLD)
                .doOnNext(temp -> {
                    if (temp) {
                        System.out.println("Warning: High temperature!");
                    }
                });

        Observable<Boolean> co2Warning = co2Observable
                .map(co2 -> co2 > CO2_THRESHOLD)
                .doOnNext(co2 -> {
                    if (co2) {
                        System.out.println("Warning: High CO2!");
                    }
                });

        Observable<Boolean> alarm = Observable.combineLatest(temperatureWarning, co2Warning,
                        (tempWarn, co2Warn) -> tempWarn && co2Warn)
                .filter(bothWarnings -> bothWarnings)
                .doOnNext(alarmFlag -> {
                    if (alarmFlag) {
                        System.out.println("ALARM!!!");
                    }
                });

        disposables.add(temperatureWarning.subscribe());
        disposables.add(co2Warning.subscribe());
        disposables.add(alarm.subscribe());
    }

    public void shutdown() {
        disposables.dispose();
    }
}
