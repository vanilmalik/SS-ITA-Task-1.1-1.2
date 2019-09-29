package eachlistexecutor;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class NumberFinder implements Callable<ArrayList<Integer>> {
    private int start, end;

    NumberFinder(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public ArrayList<Integer> call() {
        ArrayList<Integer> allPrimeNumbers = new ArrayList<>() ;

        for (int i = start; i <= end; i++) {
            if (utils.PrimeNumberChecker.isPrime(i)) {
                allPrimeNumbers.add(i);
            }
        }
        return allPrimeNumbers;
    }
}
