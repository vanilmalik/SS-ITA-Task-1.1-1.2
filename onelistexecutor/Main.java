package onelistexecutor;

import utils.InputData;
import utils.RangeForThreads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        InputData inputData = new InputData();
        int start = inputData.getStart();
        int end = inputData.getEnd();
        int threadNumber = inputData.getThreadNumber();

        ExecutorService executorService = Executors.newFixedThreadPool(threadNumber);
        for (Integer currentNumber : startToPerformThreads(start, end, threadNumber, executorService))
            System.out.print(currentNumber + " ");

        utils.ShutDownExecutor.shutdownAndAwaitTermination(executorService);
    }

    private static List<Integer> startToPerformThreads(int start, int end, int threadNumber, ExecutorService executorService) {
        List<Integer> allPrimeNumbers = Collections.synchronizedList(new ArrayList<>());

        int quantityOfNumbersInSubRange = RangeForThreads.getNumbersInEachRange(end, start, threadNumber);
        int localStart = start;

        for (int currentThreadNumber = 1; currentThreadNumber <= threadNumber; currentThreadNumber++) {
            if (!RangeForThreads.isLastInRange(end, start, threadNumber, currentThreadNumber)) {
                executorService.execute(new NumberFinder(allPrimeNumbers, localStart, end));
            } else {
                executorService.execute(new NumberFinder(allPrimeNumbers, localStart, (localStart + quantityOfNumbersInSubRange - 1)));
            }

            localStart += quantityOfNumbersInSubRange;
        }
        return allPrimeNumbers;
    }
}
