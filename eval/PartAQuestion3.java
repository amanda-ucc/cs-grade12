/* 
 *  Date: 03.01.2025 (Happy New Year!)
 * 
 *  Author: Amanda Uccello
 * 
 *  Class: ICS4UR-1
 * 
 *  Descrption of Class: 
 *      Converts from prefix to postfix using Stack data structures
 */

import java.util.HashMap;
import java.util.Map;

public class PartAQuestion3 {

    private static Map<Integer, Integer> memo = new HashMap<>();


    public static void main(String[] args) {

        // Time check for fibonacci function
        int n = 45;
        long startTime = System.nanoTime();
        int fibonacciResult = fibonacci(n);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Fibonacci(" + n + ") = " + fibonacciResult);
        System.out.println("Execution time for fibonacci: " + duration + " nanoseconds");

        // Time check for fibonacci_memo function
        startTime = System.nanoTime();
        int fibonacciMemoResult = fibonacci_memo(n);
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("Fibonacci_memo(" + n + ") = " + fibonacciMemoResult);
        System.out.println("Execution time for fibonacci_memo: " + duration + " nanoseconds");

        // Time check for sumFibonacci function
        startTime = System.nanoTime();
        int sumFibonacciResult = sumFibonacci(n);
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("SumFibonacci(" + n + ") = " + sumFibonacciResult);
        System.out.println("Execution time for sumFibonacci: " + duration + " nanoseconds");

        // Time check for sumFibonacci_memo function
        startTime = System.nanoTime();
        int sumFibonacciMemoResult = sumFibonacci_memo(n);
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("SumFibonacci_memo(" + n + ") = " + sumFibonacciMemoResult);
        System.out.println("Execution time for sumFibonacci_memo: " + duration + " nanoseconds");
    }

    // Question 3a Fibonacci Sequence 3 Marks
    public static int fibonacci (int n) {
        if (n == 0) {
            return 0; // Base case: fibonacci(0) = 0
        } else if (n == 1) {
            return 1; // Base case: fibonacci(1) = 1
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2); // Recursive case
        }
    }

    public static int fibonacci_memo(int n) {
        if (n == 0) {
            return 0; // Base case: fibonacci(0) = 0
        } else if (n == 1) {
            return 1; // Base case: fibonacci(1) = 1
        } else if (memo.containsKey(n)) {
            return memo.get(n); // Return cached value if available
        } else {
            int result = fibonacci_memo(n - 1) + fibonacci_memo(n - 2); // Recursive case
            memo.put(n, result); // Cache the result
            return result;
        }
    }

   

    // Question 3b Sum of Fibonacci Sequence 3 Marks
    public static int sumFibonacci (int n) {
        if (n == 0) {
            return 0; // Base case: sumFibonacci(0) = 0
        } else if (n == 1) {
            return 1; // Base case: sumFibonacci(1) = 1
        } else {
            return fibonacci(n) + sumFibonacci(n - 1); // Recursive case
        }
    }

    public static int sumFibonacci_memo(int n) {
        if (n == 0) {
            return 0; // Base case: sumFibonacci(0) = 0
        } else if (n == 1) {
            return 1; // Base case: sumFibonacci(1) = 1
        } else if (memo.containsKey(n)) {
            return memo.get(n); // Return cached value if available
        } else {
            int result = fibonacci(n) + sumFibonacci(n - 1); // Recursive case
            memo.put(n, result); // Cache the result
            return result;
        }
    }

}
