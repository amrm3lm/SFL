package main;

import java.util.LinkedList;

public class SquadPointPair {

    LinkedList<Object> pair;
    public SquadPointPair(squad squad){
        pair = new LinkedList<>();
        pair.add(squad.getUser());
        pair.add(squad.getTotalPoints());
    }

    public int getPoints() {
        return Integer.parseInt(pair.get(1).toString());
    }
}

