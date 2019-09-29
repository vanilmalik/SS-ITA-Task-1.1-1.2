package onelist;

import java.util.List;

public class NumberFinder implements Runnable{
    private Thread thread;
    private int start, end;
    private List<Integer> allPrimeNumbers;

    Thread getThread() {
        return thread;
    }

    NumberFinder(List<Integer> allPrimeNumbers, int start, int end) {
        this.start = start;
        this.end = end;
        this.allPrimeNumbers = allPrimeNumbers;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        for (int i = start; i <= end; i++) {
            if (utils.PrimeNumberChecker.isPrime(i)) {
                allPrimeNumbers.add(i);
            }
        }
    }
}
