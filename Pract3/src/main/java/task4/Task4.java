package task4;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class Task4 {
    public static void main(String[] args) {
        FileGenerator fileGenerator = new FileGenerator();

        FileQueue fileQueue = new FileQueue();

        FileHandler xmlHandler = new FileHandler(FileType.XML);
        FileHandler jsonHandler = new FileHandler(FileType.JSON);
        FileHandler xlsHandler = new FileHandler(FileType.XLS);

        fileQueue.getQueueObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(file -> {
                    xmlHandler.handleFile(file);
                    jsonHandler.handleFile(file);
                    xlsHandler.handleFile(file);
                });

        fileGenerator.generateFile()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(fileQueue::addFile);

        try {
            Thread.sleep(30000);  // Работа системы 30 секунд
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
