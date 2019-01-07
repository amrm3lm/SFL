package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class League implements Serializable{

    private String name;
    private HashSet<squad> squadSet;
    private ArrayList<SquadPointPair> table;

    public League (String name) {
        this.name = name;
        squadSet = new HashSet<>();
        table = new ArrayList<>();
    }

    public void addTo(squad toAdd) {
        if (squadSet.add(toAdd)) {
            table.add(new SquadPointPair(toAdd));
            sortTable();
        }
    }

    public void printLeagueTable() {
        sortTable();
        for (int i = 0; i < table.size() ; i++) {
            String name = table.get(i).getName();
            if (name.length() <= 15 ) {
                System.out.print(table.get(i).getName());
                for (int k = 0; k < 30 - name.length(); k++) {
                    System.out.print(" ");
                }
                System.out.println(table.get(i).getPoints());
            } else {
                System.out.print(table.get(i).getName().substring(0,14));
                for (int k = 0; k < 14; k++) {
                    System.out.print(" ");
                }
                System.out.println(table.get(i).getPoints());
            }

        }

    }

    private void sortTable(){
        Collections.sort(table, SquadPointPairComparator.ASCENDING.reversed());
    }
}
