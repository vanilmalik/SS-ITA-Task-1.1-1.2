package onelist;

import java.util.List;

public class PrimeNumberChecker  implements Runnable{
    private Thread thread;
    private int start, end;
    private List<Integer> allPrimeNumbers;

    public Thread getThread() {
        return thread;
    }

    public PrimeNumberChecker(List<Integer> allPrimeNumbers, int start, int end) {
        this.start = start;
        this.end = end;

        this.allPrimeNumbers = allPrimeNumbers;
        thread = new Thread(this);
        thread.start();
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
                    allPrimeNumbers.add(i);
            }
        }
    }
}
