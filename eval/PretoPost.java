/* 
 *  Date: 03.01.2025 (Happy New Year!)
 * 
 *  Author: Amanda Uccello
 * 
 *  Class: ICS4UR-1
 * 
 *  Descrption of Class: 
 *      Converts from prefix to postfix using stack data structures
 */

import java.util.*;

public class PretoPost {


    // input numbers to sample
    public static void inputSample() {
        Scanner scan = new Scanner(System.in);
        String sample;
    
        while (true) {
            System.out.println("Please input sample prefix with spaces in between (or 'q' to quit): ");
            sample = scan.nextLine();
    
            if (sample.equalsIgnoreCase("q")) {
                break;
            }
    
            if (sample.matches(".*\\s{2,}.*")) {
                System.out.println("Input should not have more than one space between characters.");
            } else if (sample.length() > 1 && !sample.matches("(\\S\\s)*\\S")) {
                System.out.println("Input should have spaces in between characters.");
            } else {
                String result = preToPost(sample);
                System.out.println("Result: " + result);
            }
        }
    
        // close scan
        scan.close();
    }

    // find the operator
    public static boolean isOperator(char var) {

        if (var == '*' || var == '/' || var == '+' || var == '-') {
            return true;
            }
            else {
                return false;
                
        }
    }
    

    //convert string to list without spaces
    public static List<Character> convertStringtoList (String str) {

        List<Character> charList = new ArrayList<>();

        for (char ch : str.toCharArray()) {
            if (ch != ' ') {
                charList.add(ch);
            }
        }

        return charList;
    }

    // convert list to string with spaces
    public static String convertStackToString(Stack<String> stack) {
        StringBuilder sb = new StringBuilder();

        for (String str : stack) {
            for (char ch : str.toCharArray()) {
                sb.append(ch).append(' ');
            }
        }

        // remove the last space
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }

        return sb.toString();
    }

    // converts prefix to postfix
    public static Stack<String> preToPost(List<Character> charList) {
        Stack<String> stack = new Stack<>();

        for (int i = charList.size() - 1; i >= 0; i--) {
            char ch = charList.get(i);

            if (PretoPost.isOperator(ch)) {

                String x = stack.pop();
                String y = stack.pop();
                String temp = x + y + ch;
                stack.push(temp);

            } else {
                stack.push(ch + "");
            }
        }

        return stack;

    }


    public static String preToPost(String sample) {

        // determines if sample is 0
        if (sample.equals("0")) {
            return "";
        }

        // converts string into a list of characters without spaces
        List<Character> sampleList = convertStringtoList(sample);

        // takes that list of characters and turns it into a postfix stack
        Stack<String> converted = PretoPost.preToPost(sampleList);

        // converts the final postfix stack and turns it into a string with spaces
        String samplestring = PretoPost.convertStackToString(converted);   
        
        return samplestring;

    }

    
    public static void main(String[] args) {
      
        // Test 1
        // input sample
        System.out.println("\n");

        String sample = "- - 3 + 2 1 9";
        System.out.println("Original prefix: " + sample);

        String samplestring = preToPost(sample);
        
        // prints the output
        System.out.println("Final postfix:   " + samplestring + "\n");


        // Test 2
        sample = "- 2 2";
        System.out.println("Original prefix: " + sample);
        samplestring = preToPost(sample);
        System.out.println("Final postfix:   " + samplestring + "\n");

        // Test 3
        sample = "+ 2 - 2 1";
        System.out.println("Original prefix: " + sample);
        samplestring = preToPost(sample);
        System.out.println("Final postfix:   " + samplestring + "\n");

        // Test 4
        sample = "0";
        System.out.println("Original prefix: " + sample);
        samplestring = preToPost(sample);
        System.out.println("Final postfix:   " + samplestring + "\n");

        // Test 5
        sample = "1";
        System.out.println("Original prefix: " + sample);
        samplestring = preToPost(sample);
        System.out.println("Final postfix:   " + samplestring + "\n");


        // Uncomment if you want to run from the scan
        // inputSample();
    }

}
