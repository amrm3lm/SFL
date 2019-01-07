package main;

import java.io.Serializable;
import java.util.LinkedList;

public class SquadPointPair implements Serializable{

    LinkedList<Object> pair;
    public SquadPointPair(squad squad){
        pair = new LinkedList<>();
        pair.add(squad.getUser());
        pair.add(squad.getTotalPoints());
    }

    public int getPoints() {
        return Integer.parseInt(pair.get(1).toString());
    }

    public String getName() {
        return (String) pair.get(0);
    }
}

