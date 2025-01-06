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

import java.util.*;
import java.util.Scanner;

public class PretoPost {

    Scanner scan = new Scanner(System.in);
    static String sample;

    // input numbers to sample
    public void inputSample() {
        
        System.out.println("Please input sample prefix with spaces inbetween: ");
        sample = scan.nextLine();

        // test cases on sample to determine if its chill

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

    


    public static void main(String[] args) {

        // input sample
        sample = "- - 3 + 2 1 9";
        System.out.println("Original prefix: " + sample);

        // converts string into a list of characters without spaces
        List<Character> sampleList = convertStringtoList(sample);

        // takes that list of characters and turns it into a postfix stack
        Stack<String> converted = PretoPost.preToPost(sampleList);

        // converts the final postfix stack and turns it into a string with spaces
        String samplestring = PretoPost.convertStackToString(converted);
        System.out.println("Final postfix: " + samplestring);


    }

}
