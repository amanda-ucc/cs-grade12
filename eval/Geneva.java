/* 
 *  Date: 03.01.2025 (Happy New Year!)
 * 
 *  Author: Amanda Uccello
 * 
 *  Class: ICS4UR-1
 * 
 *  Descrption of Class: 
 *      Geneva confection problem S3
 */

import java.util.ArrayList;
import java.util.Arrays; // used for internal class testing if lines uncommented
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Geneva {

    private Stack<Integer> mountainStack = new Stack<>();
    private Stack<Integer> branchStack = new Stack<>();
    private Stack<Integer> lakeStack = new Stack<>();
    private List<Integer> allArgs = new ArrayList<>(); // used for internal class testing if lines uncommented
 
    // Instance Methods
    /////////////////////////////////////////////////////

    // Convert a list of strings to a list of integers
    // used for internal class testing if lines uncommented
    private List<Integer> convertStringListToIntegerList(List<String> stringList) {
        List<Integer> integerList = new ArrayList<>();
        for (String s : stringList) {
            try {
                integerList.add(Integer.parseInt(s));
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: " + s + ". Please enter valid integers.");
            }
        }
        return integerList;
    }

    // Convert a string of integers to a list of integers
    private List<Integer> convertStringToIntegerList(String str) {
        List<Integer> integerList = new ArrayList<>();
        String[] parts = str.split(" ");
        for (String part : parts) {
            try {
                integerList.add(Integer.parseInt(part));
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: " + part + ". Please enter valid integers.");
            }
        }
        return integerList;
    }

    // Method to get the first number from a  string separated by spaces
    private int getFirstNumber(String str) {
        String[] parts = str.split(" ");
        return Integer.parseInt(parts[0]);
    }

    // Run the test to determine if the railway cars can be arranged in the lake
    private void runTest() {

        int order = 1;
        int testSize = mountainStack.size();

        // Loop in front of the mountain stack and the branch stack either is empty
        while (!mountainStack.isEmpty() || !branchStack.isEmpty()) {

            // if the mountain stack is not empty and the top of the mountain stack is in order
            //  then push it into the lake stack and bump the order
            if (!mountainStack.isEmpty() && mountainStack.peek() == order) {
                lakeStack.push(mountainStack.pop()); 
                order++;
            // if the lake stack is not empty and the top of the lake stack is in order
            //  then push it into the lake stack and bump the order
            } else if(!branchStack.isEmpty() && branchStack.peek() == order) {
                lakeStack.push(branchStack.pop());
                order++;
            // otherwise if the mountain stack is not empty push the top into the branch stack 
            } else if (!mountainStack.isEmpty()) {
                branchStack.push(mountainStack.pop());
            } else {
                break; // we are done here - just have to determine if lake stack is full
            }
        }

        if (testSize == lakeStack.size() ) {
            System.out.println("Y"); // the lake stack is full
        } else {
            System.out.println("N"); // the lake stack is not full
        }
    }

    // Process an input string
    public void processString(String str) {

        List<Integer> args = convertStringToIntegerList(str);
        process(args);

    }

    // Process a list of integers
    public void process(List<Integer> args) {

        // no input provided
        if (args.isEmpty()) {
            System.out.println("No input provided.");
            return;
        }

        try {
            int numberOfTests = args.get(0);
            int index = 1;

            // loop on the number of tests
            for (int test = 0; test < numberOfTests; test++) {
                if (index >= args.size()) {
                    System.out.println("Not enough input for test " + (test + 1));
                    break;
                }

                int numberOfCars = args.get(index);
                index++;

                System.out.println("\nTest " + (test + 1) + ":"); // for each test print the test number
                System.out.println("Number of cars: " + numberOfCars); // print the number of cars

                // fill the muount stack before running the test
                for (int i = 0; i < numberOfCars; i++) {
                    if (index >= args.size()) {
                        System.out.println("Not enough integers for test " + (test + 1));
                        break;
                    }

                    try {
                        int value = args.get(index);
                        mountainStack.push(value);
                        System.out.print(value + " ");
                        index++;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input: " + args.get(index) + ". Please enter valid integers.");
                        index++;
                    }
                }

                System.out.println("\nProcessing test...");
                // run the current tests
                runTest();
                // clean up
                branchStack.clear();
                lakeStack.clear();
            }
            // Clean up the mountain stack
            mountainStack.clear();   
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: " + args.get(0) + ". Please enter a valid number of tests.");
        }
    
    }

    // Read input from the command line
    ////////////////////////////////////////////////
    public List<Integer> scanArgList() {
        Scanner scanner = new Scanner(System.in);
        List<Integer> argsList = new ArrayList<>();
        System.out.println("Enter the number of tests:");

        if (scanner.hasNextInt()) {
            int numberOfTests = scanner.nextInt();
            argsList.add(numberOfTests);

            for (int test = 0; test < numberOfTests; test++) {
                System.out.println("\nEnter the number of cars for test " + (test + 1) + ":");
                if (scanner.hasNextInt()) {
                    int numberOfCars = scanner.nextInt();
                    argsList.add(numberOfCars);

                    System.out.println("Enter the cars for test " + (test + 1) + ":");
                    for (int i = 0; i < numberOfCars; i++) {
                        if (scanner.hasNextInt()) {
                            int car = scanner.nextInt();
                            argsList.add(car);
                            branchStack.push(car);
                        } else {
                            System.out.println("Invalid input. Please enter an integer.");
                            scanner.next(); // Skip the invalid input
                            i--; // Retry the current car input
                        }
                    }
                } else {
                    System.out.println("Invalid input. Please enter an integer.");
                    scanner.next(); // Skip the invalid input
                    test--; // Retry the current test input
                }
            }
        } else {
            System.out.println("Invalid input. Please enter an integer.");
        }

        scanner.close();
        System.out.println("All args: " + argsList);
        return argsList;
    }



    // Static Methods
    /////////////////////////////////////////////////////
    public static void main(String[] args) {

        Geneva g = new Geneva();

        // Uncomment the following lines to test the internal components of the Geneva class
        // // Test Process Directly
        // List<Integer> testProcessDirectly = Arrays.asList(2, 4, 2, 3, 1, 4, 4, 4, 1, 3, 2);
        // geneva.process(testProcessDirectly);

        // // Test with String Array Input
        // String [] test2StringArray = {"2", "4", "2", "3", "1", "4", "4", "4", "1", "3", "2"};
        // List<Integer> test2StringArrayList = geneva.convertStringListToIntegerList(Arrays.asList(test2StringArray));
        // geneva.process(test2StringArrayList);

        // String tests
        // TestGroup 1
        String testGroup = "2 4 2 3 1 4 4 4 1 3 2";
        System.out.println("\n=========================================\n" + "Test Group 1\nNumber of tests in group: " + g.getFirstNumber(testGroup) + "\n");
        g.processString(testGroup); 
        
        // TestGroup 2
        testGroup = "3 4 2 3 1 4 3 1 3 2 5 3 2 1 4 5";
        System.out.println("\n=========================================\n" + "Test Group 2\nNumber of tests in group: " + g.getFirstNumber(testGroup) + "\n");
        g.processString(testGroup); 


        // TestGroup 3 invalid input
        testGroup = "3 4 2 3 1 4 3 1 3 2 5 3 2 1 4 A";
        System.out.println("\n=========================================\n" + "Test Group 2\nNumber of tests in group: " + g.getFirstNumber(testGroup) + "\n");
        g.processString(testGroup); 

        // Test with Scanner - Uncomment to test
        // System.out.println("\n=========================================\n" + "Test with Scanner\n");
        // List<Integer> testScanner = g.scanArgList();
        // g.process(testScanner);

    }

}
