
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Geneva {

    Stack<Integer> mountainStack = new Stack<>();
    Stack<Integer> branchStack = new Stack<>();
    Stack<Integer> lakeStack = new Stack<>();
    List<Integer> allArgs = new ArrayList<>();
 


    public static void main(String[] args) {

        Geneva geneva = new Geneva();
        //geneva.readInput(args);

        

        // Test Process Directly
        List<Integer> test1 = Arrays.asList(2, 4, 2, 3, 1, 4, 4, 4, 1, 3, 2);
        geneva.process(test1);

        // Test with String Array Input
        String [] test2StringArray = {"2", "4", "2", "3", "1", "4", "4", "4", "1", "3", "2"};
        List<Integer> test2 = geneva.convertStringListToIntegerList(Arrays.asList(test2StringArray));
        geneva.process(test2);

        // Test with String Input
        String test3String = "2 4 2 3 1 4 4 4 1 3 2";
        List<Integer> test3 = geneva.convertStringToIntegerList(test3String);
        geneva.process(test3); 

        // Test with Scanner
        // List<Integer> test4 = geneva.scanArgList();
        // geneva.process(test4);

    }

    public List<Integer> convertStringListToIntegerList(List<String> stringList) {
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

    public List<Integer> convertStringToIntegerList(String str) {
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

    public List<Integer> scanArgList() {
        Scanner scanner = new Scanner(System.in);
        List<Integer> argsList = new ArrayList<>();
        System.out.println("Enter the number of tests:");

        if (scanner.hasNextInt()) {
            int numberOfTests = scanner.nextInt();
            argsList.add(numberOfTests);

            for (int test = 0; test < numberOfTests; test++) {
                System.out.println("Enter the number of cars for test " + (test + 1) + ":");
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

    public void process(List<Integer> args) {

        if (args.isEmpty()) {
            System.out.println("No input provided.");
            return;
        }

        try {
            int numberOfTests = args.get(0);
            int index = 1;

            for (int test = 0; test < numberOfTests; test++) {
                if (index >= args.size()) {
                    System.out.println("Not enough input for test " + (test + 1));
                    break;
                }

                int numberOfCars = args.get(index);
                index++;

                System.out.println("\nTest " + (test + 1) + ":");
                System.out.println("Number of cars: " + numberOfCars);

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
                // Process the current test
                runTest();
                branchStack.clear();
                lakeStack.clear();
            }
            mountainStack.clear();   
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: " + args.get(0) + ". Please enter a valid number of tests.");
        }
    
    }
    // Run the test
    public void runTest() {

        int order = 1;
        int testSize = mountainStack.size();

        while (!mountainStack.isEmpty() || !branchStack.isEmpty()) {

            if (!mountainStack.isEmpty() && mountainStack.peek() == order) {
                lakeStack.push(mountainStack.pop());
                order++;
            } else if(!branchStack.isEmpty() && branchStack.peek() == order) {
                lakeStack.push(branchStack.pop());
                order++;
            } else if (!mountainStack.isEmpty()) {
                branchStack.push(mountainStack.pop());
            } else {
                break;
            }
        }

        if (testSize == lakeStack.size() ) {
            System.out.println("Y");
        } else {
            System.out.println("N");
        }
    }

}
