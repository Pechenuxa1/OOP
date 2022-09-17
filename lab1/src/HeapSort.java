import java.util.Scanner;

public class HeapSort {
    public static int[] array, heap;
    public static int size, i, v1, v2, n;
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        size = input.nextInt();
        array = new int[size];
        heap = new int[size];
        for(i = 0; i < size; i++) {
            array[i] = input.nextInt();
            heap[i] = array[i];
            v1 = i;
            v2 = (i - 1) / 2;
            siftUp();
        }
        n = size;
        while(n > 1) {
            v1 = 0;
            v2 = n - 1;
            swap();
            n -= 1;
            siftDown();
        }
        for(i = size - 1; i >= 0; i--) {
            System.out.print(heap[i] + " ");
        }
    }

    public static void siftDown() {
        if (((v1 * 2) + 2) < n) {
            if((heap[v1] > heap[(v1 * 2) + 1]) || (heap[v1] > heap[(v1 * 2) + 2])) {
                if(heap[(v1 * 2) + 1] <= heap[(v1 * 2) + 2]) {
                    v2 = (v1 * 2) + 1;
                    swap();
                    v1 = v2;
                    siftDown();
                } else if (heap[(v1 * 2) + 1] > heap[(v1 * 2) + 2]) {
                    v2 = (v1 * 2) + 2;
                    swap();
                    v1 = v2;
                    siftDown();
                }
            }
        } else if (((v1 * 2) + 2) == n) {
            if((heap[v1] > heap[(v1 * 2) + 1])) {
                v2 = (v1 * 2) + 1;
                swap();
                v1 = v2;
                siftDown();
            }
        }
    }

    public static void siftUp() {
        if(heap[v1] < heap[v2] && v1 != 0) {
            swap();
            v1 = v2;
            v2 = (v2 - 1) / 2;
            siftUp();
        }
    }
    public static void swap() {
        int var = heap[v1];
        heap[v1] = heap[v2];
        heap[v2] = var;
    }
}

