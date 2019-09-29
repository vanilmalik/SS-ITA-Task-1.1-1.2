package eachlist;

import utils.InputData;
import utils.RangeForThreads;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        InputData inputData = new InputData();
        int start = inputData.getStart();
        int end = inputData.getEnd();
        int threadNumber = inputData.getThreadNumber();
        List<NumberFinder> numberFinders = startToPerformThreads(start, end, threadNumber);
        joinThreads(numberFinders);

        for (Integer i : retrieveAndAddToCommonList(numberFinders))
            System.out.print(i + " ");
    }

    private static List<NumberFinder> startToPerformThreads(int start, int end, int threadNumber) {
        NumberFinder numberFinder;
        List<NumberFinder> numberFinders = new ArrayList<>();
        int quantityOfNumbersInSubRange = RangeForThreads.getNumbersInEachRange(end, start, threadNumber);
        int localStart = start;

        for (int i = 1; i <= threadNumber; i++) {
            if (!RangeForThreads.isLastInRange(end, start, threadNumber, i)) {
                numberFinder = new NumberFinder(localStart, end);
                numberFinders.add(numberFinder);
            } else {
                numberFinder = new NumberFinder(localStart, (localStart + quantityOfNumbersInSubRange - 1));
                numberFinders.add(numberFinder);
            }
            localStart = localStart + quantityOfNumbersInSubRange;
        }
        return numberFinders;
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

    private static List<Integer> retrieveAndAddToCommonList(List<NumberFinder> numberFinders) {
        List<Integer> allPrimeNumbers = new ArrayList<>();
        for (NumberFinder p : numberFinders) {
            allPrimeNumbers.addAll(p.getAllPrimeNumbers());
        }
        return allPrimeNumbers;
    }
}
