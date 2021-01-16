import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class Driver{
    public static void main(String args[]) throws IOException{
        String s= "C:\\Users\\Sandeep Aggarwal\\Desktop\\2nd_year\\COL106\\PracticeCoding\\Assignments\\A1_test_cases\\testcases_A2_20.txt";
        File m = new File(s);
        int numTestCases;
        FileWriter fw = new FileWriter(".\\A1_test_cases\\r_A2_20.txt");
        Scanner sc = new Scanner(m);
        numTestCases = sc.nextInt();
        while(numTestCases-->0){
            int size;
            size = sc.nextInt();
            A2DynamicMem obj = new A2DynamicMem(size, 2);
            int numCommands = sc.nextInt();
            while(numCommands-->0) {
                String command;
                command = sc.next();
                int argument;
                argument = sc.nextInt();
                int result = -5;
                boolean toPrint = true;
                switch (command) {
                    case "Allocate":
                        result = obj.Allocate(argument);
                        break;
                    case "Free":
                        result = obj.Free(argument);
                        break;
                    case "Defragment":
                        obj.Defragment();
                        toPrint = false;
                        break;
                    default:
                        break;
                }
                if (toPrint){
                String k = Integer.toString(result);
                fw.write(k);
                fw.write("\n");
                }
            }
        }

        fw.close();
        sc.close();
    }
}


//import java.util.Random;
//import java.util.Scanner;
//public class Driver{
//    public static void main(String args[]){
//        int numTestCases;
//        Scanner sc = new Scanner(System.in);
//        numTestCases = sc.nextInt();
//        while(numTestCases-->0){
//            int size;
//            size = sc.nextInt();
//            A2DynamicMem obj = new A2DynamicMem(size, 3);
//            int numCommands = sc.nextInt();
//            while(numCommands-->0) {
//                String command;
//                command = sc.next();
//                int argument;
//                argument = sc.nextInt();
//                int result = -5;
//                boolean toPrint = true;
//                switch (command) {
//                    case "Allocate":
//                        result = obj.Allocate(argument);
//                        break;
//                    case "Free":
//                        result = obj.Free(argument);
//                        break;
//                    case "Defragment":
//                        obj.Defragment();
//                        toPrint = false;
//                        break;
//                    default:
//                        break;
//                }
//                if(toPrint)
//                    System.out.println(result);
//            }
//
//        }
//    }
//}
