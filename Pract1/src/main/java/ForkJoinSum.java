import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

public class ForkJoinSum extends RecursiveTask<Integer> {
    private static final long THRESHOLD = 1000;
    private final ArrayList<Integer> arr;
    private final int start;
    private final int end;

    public ForkJoinSum(ArrayList<Integer> arr, int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int length = end - start;

        if (length <= THRESHOLD) {
            return sum();
        }

        int mid = start + length / 2;

        ForkJoinSum leftTask = new ForkJoinSum(arr, start, mid);
        ForkJoinSum rightTask = new ForkJoinSum(arr, mid, end);

        leftTask.fork();

        int rightSum = rightTask.compute();
        int leftSum = leftTask.compute();

        return leftSum + rightSum;
    }

    private int sum() {
        int sum = 0;

        for (int i = start; i < end; i++) {
            sum += arr.get(i);
        }

        return sum;
    }
}
