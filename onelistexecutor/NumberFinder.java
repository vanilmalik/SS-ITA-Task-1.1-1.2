package onelistexecutor;

import java.util.List;

public class NumberFinder implements Runnable {
    private int start, end;
    private List<Integer> allPrimeNumbers;

    NumberFinder(List<Integer> allPrimeNumbers, int start, int end) {
        this.start = start;
        this.end = end;
        this.allPrimeNumbers = allPrimeNumbers;
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
