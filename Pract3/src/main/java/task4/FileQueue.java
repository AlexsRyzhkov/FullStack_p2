package task4;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class FileQueue {
    private final PublishSubject<File> queue = PublishSubject.create();

    public Observable<File> getQueueObservable() {
        return queue;
    }

    public void addFile(File file){
        System.out.println("Файл добавлен в очередь");
        queue.onNext(file);
    }
}
