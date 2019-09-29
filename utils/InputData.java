package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputData {
    private int start;
    private int end;
    private int threadNumber;

    public InputData() {
        performInput();
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getThreadNumber() {
        return threadNumber;
    }

    public void performInput() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
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
        }
    }
}
