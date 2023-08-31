package algorithm.dfs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TravelPath {
    List<String> list = new ArrayList<>();
    private boolean[] useTickets;

    public String[] solution(String[][] tickets) {
        String[] answer = {};

        useTickets = new boolean[tickets.length];

        dfs(tickets, 0, "ICN", "ICN");

        Collections.sort(list);

        return list.get(0).split(",");
    }

    private void dfs(String[][] tickets, int depth, String from, String path) {
        if(depth == tickets.length) {
            list.add(path);
            return;
        }

        for(int i=0; i<tickets.length; i++) {
            if(!useTickets[i] && tickets[i][0].equals(from)) {
                useTickets[i] = true;
                dfs(tickets, depth + 1, tickets[i][1], path + "," + tickets[i][1]);
                useTickets[i] = false;
            }
        }

    }
}
