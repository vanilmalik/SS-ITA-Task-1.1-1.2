package eachlist;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int start, end, threadNumber;

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("Input start of range: ");
            start = Integer.parseInt(bufferedReader.readLine());
            System.out.println("Input end of range: ");
            end = Integer.parseInt(bufferedReader.readLine());
            //todo:проверка количества потоков больше количества элементов
            System.out.println("Input number of threads: ");
            threadNumber = Integer.parseInt(bufferedReader.readLine());

            int quantityOfNumbersInSubRange = Math.round( (end - start + 1) / (float) threadNumber);

            List<Integer> allPrimeNumbers = new ArrayList<>();
            List<PrimeNumberChecker> primeNumberCheckers = new ArrayList<>();
            PrimeNumberChecker primeNumberChecker;
            int localStart = start;
            long s = System.currentTimeMillis();
            for (int i = 1; i <= threadNumber; i++) {
                if (!( ( (end - start + 1) % threadNumber) == 0) && (i == threadNumber) ) {
                    primeNumberChecker = new PrimeNumberChecker(localStart, end);
                    primeNumberCheckers.add(primeNumberChecker);
                    //primeNumberChecker.getThread().join();
                    //allPrimeNumbers.addAll(primeNumberChecker.getAllPrimeNumbers());
                } else {
                    primeNumberChecker = new PrimeNumberChecker(localStart, (localStart + quantityOfNumbersInSubRange - 1));
                    primeNumberCheckers.add(primeNumberChecker);
//                    primeNumberChecker.getThread().join();
//                    allPrimeNumbers.addAll(primeNumberChecker.getAllPrimeNumbers());
                }

                localStart = localStart + quantityOfNumbersInSubRange;
            }

            for (PrimeNumberChecker p : primeNumberCheckers) {
                p.getThread().join();
            }

            for (PrimeNumberChecker p : primeNumberCheckers) {
                allPrimeNumbers.addAll(p.getAllPrimeNumbers());
            }

            for (Integer i : allPrimeNumbers) {
                System.out.print(i + " ");
            }

            System.out.println();
            long e = System.currentTimeMillis();
            System.out.println( (e - s) );

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
