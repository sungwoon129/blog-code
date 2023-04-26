package sort;

import common.Algorithm;

public class BubbleSort extends SortAlgorithm implements Algorithm {

    @Override
    public void run() {
        this.print();
        this.implLogic();
    }

    @Override
    public void implLogic() {

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i; j++) {

                if (j == arr.length - 1) break;


                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }

            if(i == 0) {
                System.out.println("▼ 정렬 전 배열");
            } else {
                System.out.println("▼ " + i + "회전");
            }
            for(int el : arr) {
                System.out.print(el + " ");
            }
            System.out.println();
        }

        System.out.println("정렬완료");
    }

    @Override
    public void print() {
        System.out.println("수행할 알고리즘 : Bubble Sort(버블정렬)");
    }


}
