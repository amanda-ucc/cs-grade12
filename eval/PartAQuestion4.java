/* 
 *  Date: 03.01.2025 (Happy New Year!)
 * 
 *  Author: Amanda Uccello
 * 
 *  Class: ICS4UR-1
 * 
 *  Descrpition of Class: 
 *      Mirrors a string
 */

public class PartAQuestion4 {
    
    public static void main(String[] args) {
        
        System.out.println(mirror("Go Storm Go!")); 
        System.out.println(mirror("!@#$%^&*()")); 
        System.out.println(mirror("12345678")); 
        System.out.println(mirror("H      E     L     L    O")); 
        System.out.println(mirror("It works!")); 
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
