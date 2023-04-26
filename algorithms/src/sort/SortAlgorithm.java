package sort;

public class SortAlgorithm {

    protected int[] arr;


    public SortAlgorithm() {
        this.arr = getIntArr();
    }

    public int[] getArr() {
        return this.arr;
    }

    public void setArr(int[] arr) {
        this.arr = arr;
    }



    private int[] getIntArr() {
        int size = (int)(Math.random() * 10);
        int[] arr = new int[size * 2];
        for(int i = 0; i< arr.length; i++) {
            int element = (int)(Math.random() * 100);
            arr[i] = element;
        }
        return arr;
    }

    protected void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
