package onelistexecutor;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class PrimeNumberChecker  implements Runnable{
    private ReentrantLock reentrantLock;
    private int start, end;
    private List<Integer> allPrimeNumbers;

    public PrimeNumberChecker(List<Integer> allPrimeNumbers, ReentrantLock lock, int start, int end) {
        this.start = start;
        this.end = end;
        reentrantLock = lock;
        this.allPrimeNumbers = allPrimeNumbers;
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

    @Override
    public void run() {
        for (int i = start; i <= end; i++) {
            if (isPrime(i)) {
                try {
                    //reentrantLock.lock();
                    allPrimeNumbers.add(i);
                } finally {
                    //reentrantLock.unlock();
                }
            }
        }
    }
}
