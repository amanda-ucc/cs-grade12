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

public class PascalTriangle {

    private int [][] pascalArray;

    public PascalTriangle(int n) {

        this.pascalTriangle(n);

    }
    

    public int [][] pascalTriangle(int n) {

        n = n+1;

        this.pascalArray = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            this.pascalArray[i][0] = 1;
            this.pascalArray[i][i] = 1;
            for (int j = 1; j < i; j++) {
                this.pascalArray[i][j] = pascalTriangle(i - 1, j - 1) + pascalTriangle(i - 1, j);
            }
        }
       
        return this.pascalArray;
    }


    public void printPascal() {
        int size = this.pascalArray.length - 1 ;
        System.out.println("Pascal Triangle - PascalTriangle(" + size + ")");
        for (int i = 0; i < this.pascalArray.length; i++) {
            for (int j = 0; j < this.pascalArray[i].length; j++) {
                System.out.print(this.pascalArray[i][j] + " ");
            }
            System.out.println();
        }
    }


    // Static Metods
    ///////////////////////////////////
    public static int pascalTriangle(int row, int column) {
        if (column == 0 || column == row) {
            return 1;
        } 
        else if (column > row) {
            return 0;
        } 
        else {
            return pascalTriangle(row - 1, column - 1) + pascalTriangle(row - 1, column);
        }
    }

    // Static Methods
    public static void main(String[] args) {

        // Static Recursive Method
        System.out.println(pascalTriangle(1,1));
        System.out.println(pascalTriangle(3,2));
        System.out.println(pascalTriangle(2,2));
        System.out.println(pascalTriangle(2,1));


        // Test Constructor
        PascalTriangle p1 = new PascalTriangle(3);
        p1.printPascal();

        PascalTriangle p2 = new PascalTriangle(5);
        p2.printPascal();

        // Test Changing Triangle of Existing Object
        p1.pascalTriangle(4);
        p1.printPascal();

        p2.pascalTriangle(6);
        p2.printPascal();

        // Test whethe the pascalTriangle returns the correct array
        System.out.println("Test whether the pascalTriangle returns the correct array");
        System.out.println("p1.pascalTriangle(2)");
        int [][] pascalArray = p1.pascalTriangle(2);
        for (int i = 0; i < pascalArray.length; i++) {
            for (int j = 0; j < pascalArray[i].length; j++) {
                System.out.print(pascalArray[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("p2.pascalTriangle(3)");
        pascalArray = p2.pascalTriangle(3);
        for (int i = 0; i < pascalArray.length; i++) {
            for (int j = 0; j < pascalArray[i].length; j++) {
                System.out.print(pascalArray[i][j] + " ");
            }
            System.out.println();
        }

    }

}
