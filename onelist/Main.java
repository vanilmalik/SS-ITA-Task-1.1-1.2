package onelist;

import utils.InputData;
import utils.RangeForThreads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        InputData inputData = new InputData();
        int start = inputData.getStart();
        int end = inputData.getEnd();
        int threadNumber = inputData.getThreadNumber();

        List<NumberFinder> numberFinders = new ArrayList<>();
        List<Integer> allPrimeNumbers = startToPerformThreads(start, end, threadNumber, numberFinders);
        joinThreads(numberFinders);

        for (Integer currentNumber : allPrimeNumbers)
            System.out.print(currentNumber + " ");
    }

    private static List<Integer> startToPerformThreads(int start, int end, int threadNumber, List<NumberFinder> numberFinders) {
        List<Integer> allPrimeNumbers = Collections.synchronizedList(new ArrayList<>());
        int quantityOfNumbersInSubRange = RangeForThreads.getNumbersInEachRange(end, start, threadNumber);

        NumberFinder numberFinder;
        int localStart = start;
        for (int currentThreadNumber = 1; currentThreadNumber <= threadNumber; currentThreadNumber++) {
            if (!RangeForThreads.isLastInRange(end, start, threadNumber, currentThreadNumber)) {
                numberFinder = new NumberFinder(allPrimeNumbers, localStart, end);
                numberFinders.add(numberFinder);
            } else {
                numberFinder = new NumberFinder(allPrimeNumbers, localStart, (localStart + quantityOfNumbersInSubRange - 1));
                numberFinders.add(numberFinder);
            }

            localStart += quantityOfNumbersInSubRange;
        }
        return allPrimeNumbers;
    }

    private static void joinThreads(List<NumberFinder> numberFinders) {
        for (NumberFinder p : numberFinders) {
            try {
                p.getThread().join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
