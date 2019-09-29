package eachlist;

import java.util.ArrayList;
import java.util.List;

public class NumberFinder implements Runnable{
    private List<Integer> allPrimeNumbers;
    private Thread thread;
    private int start, end;

    NumberFinder(int start, int end) {
        this.start = start;
        this.end = end;
        allPrimeNumbers = new ArrayList<>();
        thread = new Thread(this);
        thread.start();
    }

    List<Integer> getAllPrimeNumbers() {
        return allPrimeNumbers;
    }

    Thread getThread() {
        return thread;
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
