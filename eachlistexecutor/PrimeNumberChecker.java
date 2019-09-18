package eachlistexecutor;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class PrimeNumberChecker implements Callable<ArrayList<Integer>> {
    private int start, end;

    public PrimeNumberChecker(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public ArrayList<Integer> call() {
        ArrayList<Integer> allPrimeNumbers = new ArrayList<>() ;

        for (int i = start; i <= end; i++) {
            if (isPrime(i)) {
                allPrimeNumbers.add(i);
            }
        }
        return allPrimeNumbers;
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
