package eachlistexecutor;

import utils.InputData;
import utils.RangeForThreads;
import utils.ShutDownExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) {
        InputData inputData = new InputData();
        int start = inputData.getStart();
        int end = inputData.getEnd();
        int threadNumber = inputData.getThreadNumber();

        ExecutorService executorService = Executors.newFixedThreadPool(threadNumber);
        List<Integer> allPrimeNumbers = getPrimeNumbersFromThreads(startToPerformThreads(start, end, threadNumber, executorService));
        for (Integer currentNumber : allPrimeNumbers)
            System.out.print(currentNumber + " ");

        ShutDownExecutor.shutdownAndAwaitTermination(executorService);
    }

    private static ArrayList<Future<ArrayList<Integer>>> startToPerformThreads(int start, int end, int threadNumber, ExecutorService executorService) {
        int quantityOfNumbersInSubRange = RangeForThreads.getNumbersInEachRange(end, start, threadNumber);
        int localStart = start;

        Future<ArrayList<Integer>> future;
        ArrayList<Future<ArrayList<Integer>>> futures = new ArrayList<>();

        for (int currentThreadNumber = 1; currentThreadNumber <= threadNumber; currentThreadNumber++) {
            if (!RangeForThreads.isLastInRange(end, start, threadNumber, currentThreadNumber)) {
                future = executorService.submit(new NumberFinder(localStart, end));
                futures.add(future);
            } else {
                future = executorService.submit(new NumberFinder(localStart, (localStart + quantityOfNumbersInSubRange - 1)));
                futures.add(future);
            }

            localStart += quantityOfNumbersInSubRange;
        }
        return futures;
    }

    private static List<Integer> getPrimeNumbersFromThreads(ArrayList<Future<ArrayList<Integer>>> futures) {
        List<Integer> allPrimeNumbers = new ArrayList<>();
        for (Future<ArrayList<Integer>> f : futures) {
            try {
                allPrimeNumbers.addAll(f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return allPrimeNumbers;
    }
}
