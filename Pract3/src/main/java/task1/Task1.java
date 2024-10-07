package task1;

public class Task1 {

    public static void main(String[] args) throws InterruptedException {
        TemperatureSensor temperatureSensor = new TemperatureSensor();
        CO2Sensor co2Sensor = new CO2Sensor();

        AlarmSystem alarmSystem = new AlarmSystem();

        alarmSystem.monitor(temperatureSensor.getObservable(), co2Sensor.getObservable());

        Thread.sleep(10000);

        alarmSystem.shutdown();
    }
}
