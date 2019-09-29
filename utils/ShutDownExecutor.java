package utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class ShutDownExecutor {
    //Recommended way to shutdown ExecutorService from Oracle API https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html
    public static void shutdownAndAwaitTermination(ExecutorService executorService) {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
