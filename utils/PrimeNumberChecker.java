package utils;

public class PrimeNumberChecker {

    public static boolean isPrime(int number) {
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
