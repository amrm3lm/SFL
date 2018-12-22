package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class League {

    private String name;
    private HashSet<squad> squadSet;
    private ArrayList<SquadPointPair> table;

    public League (String name) {
        name = name;
        squadSet = new HashSet<>();
        table = new ArrayList<>();
    }

    public void addTo(squad toAdd) {
        if (squadSet.add(toAdd)) {
            table.add(new SquadPointPair(toAdd));
            sortTable();
        }
    }

    private void writeFile(){

    }

    private void sortTable(){
        Collections.sort(table, SquadPointPairComparator.ASCENDING.reversed());
    }
}
