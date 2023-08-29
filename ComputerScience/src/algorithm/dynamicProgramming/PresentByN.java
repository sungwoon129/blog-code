package algorithm.dynamicProgramming;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

class PresentByN {

    private void unionSet(Set<Integer> union,Set<Integer> aSet, Set<Integer> bSet) {
        for(int a : aSet) {
            for(int b : bSet) {
                union.add(a + b);
                union.add(a - b);
                union.add(a * b);
                if(b != 0) {
                    union.add(a / b);
                }
            }
        }
    }

    public int solution(int N, int number) {
        List<Set<Integer>> setList = new ArrayList<>();


        if(N == number)  return 1;

        for(int i=0; i<9; i++) {
            setList.add(new HashSet<>());
        }
        setList.get(1).add(N);

        for(int i=2; i<9; i++) {
            for(int j=1; j < i; j++) {
                unionSet(setList.get(i), setList.get(i-j), setList.get(j));

            }
            String n = Integer.toString(N);
            setList.get(i).add(Integer.parseInt(n.repeat(i)));
            for(int num : setList.get(i)) {
                if( num == number) return i;
            }
        }
        return -1;

    }
}
