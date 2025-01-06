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

public class PartAQuestion1 {

    public static void main(String[] args) {
        // Time check for power function
        int base = 2;
        int exponent = 20;
        long startTime = System.nanoTime();
        int powerResult = power(base, exponent);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Power(" + base + ", " + exponent + ") = " + powerResult);
        System.out.println("Execution time for power: " + duration + " nanoseconds");
    }    

    // Part A Question 1 Three Marks 
    // Recursive function to calculate power
    public static int power(int base, int exponent) {
        if (exponent == 0) {
            return 1; // Base case: any number raised to the power of 0 is 1
        } else {
            return base * power(base, exponent - 1); // Recursive case
        }
    }
    
}
