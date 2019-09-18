package eachlistexecutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        int start = 0, end = 0, threadNumber = 0;

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Input start of range: ");
            start = Integer.parseInt(bufferedReader.readLine());
            System.out.println("Input end of range: ");
            end = Integer.parseInt(bufferedReader.readLine());
            System.out.println("Input number of threads: ");
            threadNumber = Integer.parseInt(bufferedReader.readLine());

            if (threadNumber > (end - start + 1) || start <= 0 || end <= 0 || threadNumber <= 1 || end < start) {
                throw new Exception();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Wrong input!");
            return;
        }

        int quantityOfNumbersInSubRange = Math.round( (end - start + 1) / (float) threadNumber);

        int localStart = start;
        long s = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(threadNumber);
        ArrayList<Integer> allPrimeNumbers = new ArrayList<>();
        ArrayList<Future<ArrayList<Integer>>> futures = new ArrayList<>();
        Future<ArrayList<Integer>> future;

        for (int i = 1; i <= threadNumber; i++) {
            if (!( ( (end - start + 1) % threadNumber) == 0) && (i == threadNumber) ) {
                future = executorService.submit(new PrimeNumberChecker(localStart, end));
                futures.add(future);
            } else {
                future = executorService.submit(new PrimeNumberChecker(localStart, (localStart + quantityOfNumbersInSubRange - 1)));
                futures.add(future);
            }

            localStart = localStart + quantityOfNumbersInSubRange;
        }

        for (Future<ArrayList<Integer>> f : futures) {
            try {
                allPrimeNumbers.addAll(f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        shutdownAndAwaitTermination(executorService);

        for (Integer i : allPrimeNumbers) {
            System.out.print(i + " ");
        }
        System.out.println();

        long e = System.currentTimeMillis();
        System.out.println( (e - s) );
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
