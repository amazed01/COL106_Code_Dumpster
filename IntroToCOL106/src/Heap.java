public class Heap {
    private int size;
    private int[] heap ;

    public Heap(int size) {
        this.size = size;
        heap = new int[size];
    }

    public static void heapify(int[] A, int index) {
        // Heap violated at A[index]
        int i = index, l = 2*index, r = 2*index + 1, temp = A[index];
        while (A[i] > A[l] || A[i] > A[r]) {
            if (A[l] <= A[r]) {
                temp = A[i];
                A[i] = A[l];
                A[l] = temp;
//                System.out.println(A[i]);
                i = l;
                l = 2*i;
                r = 2*i + 1;
            } else {
                temp = A[i];
                A[i] = A[r];
                A[r] = temp;
//                System.out.println(A[i]);
                i = r;
                l = 2*i;
                r = 2*i + 1;
            }
        }
//        for (int j = 1; j < A.length; j++) {
//            System.out.println(A[j]);
//        }
    }
}


//        Heap test = new Heap(1);
//        int[] A = {-1, 17,10,11,16,21,13,12,43,23,26,29,31,19};
//        Heap.heapify(A,1);
