import java.util.HashSet;
import java.util.Vector;

public class Main {

    public static void main(String[] args) {
        // write your code here
        int[] A = {9,8,7,6,5,4,3,2,1,5};
        int t = partition(A, 0, A.length - 1);
        System.out.println(t);
        printArray(A, A.length);
        t = partition(A, 0, t);
        System.out.println(t);
        quickSort(A, 0, A.length-1);
        printArray(A, A.length);
    }

    /* Function to print an array */
    static void printArray(int[] arr, int n)
    {
        for (int i = 0; i < n; i++)
            System.out.print(" " + arr[i]);
        System.out.println();
    }


    public static void quickSort(int[] A, int p, int q){
        if (p < q) {
            int t = partition(A, p, q);
            quickSort(A, p, t);
            quickSort(A, t+1, q);
        }
    }

    public static int partition(int[] A, int p, int q) {
        int pivot = A[p], temp;
        int i = p-1, j = q+1;
        while (true) {
            //System.out.println(i);
            do {
                i++;
            } while (A[i] < pivot && i < j);
            do {
                j--;
            } while (A[j] > pivot && i < j);

            if (i >= j) {
                return j;
            }
            temp = A[i];
            A[i] = A[j];
            A[j] = temp;
        }
    }

}

class Solution {
    public int findJudge(int N, int[][] trust) {
        int[] indegree = new int[N+1];
        boolean[] outdegre = new boolean[N+1];
        int judge = -1;
        for (int[] pair:
                trust) {
            outdegre[pair[0]] = true;
            indegree[pair[1]] += 1;
        }

        for (int i = 1; i <= N; i++) {
            if (indegree[i] == N-1 && outdegre[i] == false) judge = i;
        }
        return judge;

    }
}
