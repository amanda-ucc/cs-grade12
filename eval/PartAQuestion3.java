/* 
 *  Date: 03.01.2025 (Happy New Year!)
 * 
 *  Author: Amanda Uccello
 * 
 *  Class: ICS4UR-1
 * 
 *   Description of Class: 
 *      Calculate Fibonacci sequence and series
 */

import java.util.HashMap;
import java.util.Map;

public class PartAQuestion3 {

    private static Map<Integer, Long> memo_seq = new HashMap<>();
    private static Map<Integer, Long> memo_ser = new HashMap<>();


    public static void main(String[] args) {

        // Time check for fibonacci function
        int n = 40;
        long startTime = System.nanoTime(); // in nano seconds
        long fibonacciResult = fibonacci(n);
        long endTime = System.nanoTime(); // in nano seconds
        double duration = (endTime - startTime)/ 1_000_000_000.0; // this is in seconds
        System.out.println("Fibonacci(" + n + ") = " + fibonacciResult);
        System.out.println("Execution time for fibonacci: " + duration + " seconds");

        // Time check for fibonacci_memo function
        startTime = System.nanoTime();
        long fibonacciMemoResult = fibonacci_memo(n);
        endTime = System.nanoTime();
        duration = (endTime - startTime)/ 1_000_000_000.0; // this is in seconds
        System.out.println("Fibonacci_memo(" + n + ") = " + fibonacciMemoResult);
        System.out.println("Execution time for fibonacci_memo: " + duration + " seconds");

        // Time check for sumFibonacci function
        startTime = System.nanoTime();
        long sumFibonacciResult = sumFibonacci(n);
        endTime = System.nanoTime();
        duration = (endTime - startTime)/ 1_000_000_000.0; // this is in seconds
        System.out.println("SumFibonacci(" + n + ") = " + sumFibonacciResult);
        System.out.println("Execution time for sumFibonacci: " + duration + " seconds");

        // Time check for sumFibonacci_memo function
        startTime = System.nanoTime();
        long sumFibonacciMemoResult = sumFibonacci_memo(n);
        endTime = System.nanoTime();
        duration = (endTime - startTime)/ 1_000_000_000.0; // this is in seconds
        System.out.println("SumFibonacci_memo(" + n + ") = " + sumFibonacciMemoResult);
        System.out.println("Execution time for sumFibonacci_memo: " + duration + " seconds");
    }

    // Question 3a Fibonacci Sequence 3 Marks
    public static long fibonacci (int n) {
        if (n == 0) {
            return 0; // Base case: fibonacci(0) = 0
        } else if (n == 1) {
            return 1; // Base case: fibonacci(1) = 1
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2); // Recursive case
        }
    }

    public static long fibonacci_memo(int n) {
        if (n == 0) {
            return 0; // Base case: fibonacci(0) = 0
        } else if (n == 1) {
            return 1; // Base case: fibonacci(1) = 1
        } else if (memo_seq.containsKey(n)) {
            return memo_seq.get(n); // Return cached value if available
        } else {
            long result = fibonacci_memo(n - 1) + fibonacci_memo(n - 2); // Recursive case
            memo_seq.put(n, result); // Cache the result
            return result;
        }
    }

   
    // Question 3b Sum of Fibonacci Sequence 3 Marks
    public static long sumFibonacci (int n) {
        if (n == 0) {
            return 0; // Base case: sumFibonacci(0) = 0
        } else if (n == 1) {
            return 1; // Base case: sumFibonacci(1) = 1
        } else {
            return fibonacci(n) + sumFibonacci(n - 1); // Recursive case
        }
    }

    public static long sumFibonacci_memo(int n) {
        if (n == 0) {
            return 0; // Base case: sumFibonacci(0) = 0
        } else if (n == 1) {
            return 1; // Base case: sumFibonacci(1) = 1
        } else if (memo_ser.containsKey(n)) {
            return memo_ser.get(n); // Return cached value if available
        } else {
            long result = fibonacci(n) + sumFibonacci(n - 1); // Recursive case
            memo_ser.put(n, result); // Cache the result doesn't seem to help here with the series
            return result;
        }
    }

}
