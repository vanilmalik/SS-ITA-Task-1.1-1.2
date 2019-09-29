package utils;

public class RangeForThreads {
    public static int getNumbersInEachRange(int end, int start, int threadNumber) {
        return (int) Math.round((end - start + 1) / ((double) threadNumber));
    }

    public static boolean isLastInRange(int end, int start, int threadNumber, int numberOfCurrentThread) {
        return (((end - start + 1) % threadNumber) == 0) && (numberOfCurrentThread == threadNumber);
    }
}
