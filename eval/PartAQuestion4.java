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

public class PartAQuestion4 {
    
    public static void main(String[] args) {
        
        System.out.println(mirror("Go Storm Go!")); // Output: olleH

    }

    // Question 4 String Mirror
    public static String mirror(   String s) {
       return s + " : " + _mirror(s);
    }
    
    // Recursive function to mirror a string
    public static String _mirror(String s) {
        if (s.length() == 0) {
            return ""; // Base case: mirror("") = ""
        } else {
            return s.charAt(s.length() - 1) + _mirror(s.substring(0, s.length() - 1)); // Recursive case
        }
    }
        
}
