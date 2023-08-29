package algorithm.greedy;

import java.util.Arrays;
import java.util.Comparator;

public class MinimumSpanningTree {
    int[] parent;

    public int solution(int n, int[][] costs) {
        Arrays.sort(costs, Comparator.comparingInt((int[] c) -> c[2]));

        parent = new int[n];

        for(int i=0; i<n; i++) {
            parent[i] = i;
        }

        int total = 0;
        int selectedEdgeCount = 0;

        for(int[] edge : costs) {
            int from = edge[0];
            int to = edge[1];
            int cost = edge[2];

            int fromParent = findParent(from);
            int toParent = findParent(to);

            if(fromParent == toParent) continue;

            total += cost;
            parent[toParent] = fromParent;

            selectedEdgeCount++;
            if(selectedEdgeCount == n -1) break;
        }
        return total;
    }

    private int findParent(int node) {
        if(parent[node] == node) return node;
        return parent[node] = findParent(parent[node]);
    }

}
