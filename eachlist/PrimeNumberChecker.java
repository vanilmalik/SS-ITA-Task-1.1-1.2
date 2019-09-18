package eachlist;

import java.util.ArrayList;
import java.util.List;

public class PrimeNumberChecker implements Runnable{
    private List<Integer> allPrimeNumbers;
    private Thread thread;
    private int start, end;

    public PrimeNumberChecker(int start, int end) {
        this.start = start;
        this.end = end;

        allPrimeNumbers = new ArrayList<>();

        thread = new Thread(this);
        thread.start();
    }

    public List<Integer> getAllPrimeNumbers() {
        return allPrimeNumbers;
    }

    public Thread getThread() {
        return thread;
    }

    @Override
    public void run() {
        for (int i = start; i <= end; i++) {
            if (isPrime(i)) {
                    allPrimeNumbers.add(i);
            }
        }
    }

    private boolean isPrime(int number) {
        boolean isPrime = true;

        for (int j = 2; j <= number/2; j++) {
            if ( (number % j) == 0 ) {
                isPrime = false;
                break;
            }
        }

        return isPrime;
    }
}
