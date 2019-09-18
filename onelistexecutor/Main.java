package onelistexecutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) {
        int start = 0, end = 0, threadNumber = 0;

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        ReentrantLock reentrantLock = new ReentrantLock();
        ExecutorService executorService;
        List<Integer> allPrimeNumbers = null;

        try {
            System.out.println("Input start of range: ");
            start = Integer.parseInt(bufferedReader.readLine());
            System.out.println("Input end of range: ");
            end = Integer.parseInt(bufferedReader.readLine());
            System.out.println("Input number of threads: ");
            threadNumber = Integer.parseInt(bufferedReader.readLine());

            if (end <= start || threadNumber <= 1 || end < 0 || start < 0)
                throw new Exception();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Input only integers");
        } catch (Exception e) {
            System.out.println("Sorry wrong values, check your input");
            return;
        }

        long s = System.currentTimeMillis();
        if (threadNumber != 0 ) {
            int quantityOfNumbersInSubRange = Math.round((end - start + 1) / (float) threadNumber); //find haw many elements will process each thread
            int localStart = start; //create temp variable to save start value

            executorService = Executors.newFixedThreadPool(threadNumber);
            allPrimeNumbers = Collections.synchronizedList(new ArrayList<>());

            for (int i = 1; i <= threadNumber; i++) {
                if (!(((end - start + 1) % threadNumber) == 0) && (i == threadNumber)) { //if range is last of all
                    executorService.execute(new PrimeNumberChecker(allPrimeNumbers, reentrantLock, localStart, end));
                } else {
                    executorService.execute(new PrimeNumberChecker(allPrimeNumbers, reentrantLock, localStart, (localStart + quantityOfNumbersInSubRange - 1)));
                }

                localStart += quantityOfNumbersInSubRange; //change start position for next thread
            }

            shutdownAndAwaitTermination(executorService);
        }

        if (allPrimeNumbers != null) {
            Collections.sort(allPrimeNumbers);
            for (Integer i : allPrimeNumbers) {
                System.out.print(i + " ");
            }
        }

        System.out.println();
        long e = System.currentTimeMillis();
        System.out.print( e - s );
    }

    //Recommended way to shutdown ExecutorService from Oracle API https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html
    private static void shutdownAndAwaitTermination(ExecutorService executorService) {
        executorService.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            executorService.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }
}
