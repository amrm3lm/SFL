package main;

import Exceptions.SwitchPlayerException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class GameweekSquad {

    private squad squad;
    private int gameweekNum;
    private int points;
    private HashMap<player, Integer> pointMap;
    private HashSet<player> notPlayed;
    private player captain;

    public GameweekSquad(squad squad, int gameweekNUM, HashSet notplayed, player captain) {
        this.squad = squad;
        this.gameweekNum = gameweekNUM;
        this.notPlayed = notplayed;
        this.captain = captain;
    }

    public int calcPoints(HashMap<player, Integer> pointMap) {
        this.pointMap = pointMap;
        ArrayList<LinkedList<player>> formation = squad.getFormation();
        int sumPoints = 0;

        for (int i = 0; i < 4; i++) {
            here:
            for (int j = 0; j < formation.get(i).size(); j++) {
                player p = formation.get(i).get(j);
                int points = pointMap.get(p);
                sumPoints += points;
                if (notPlayed.contains(p)) {
                    for (int k = 0; k < 4; k++) { //goes through sub bench

                        player subP = formation.get(4).get(k);
                        if (squad.isValidFormation(p, subP)) {

                            try {
                                squad.interSquadSwitch(p, subP);
                            } catch (SwitchPlayerException e) {

                            }
                            int pointSub = pointMap.get(subP);
                            sumPoints += pointSub;
                            break here;
                        }
                    }
                }

                if (captain.equals(p)){
                    sumPoints+=points;
                }
            }

        }
        this.points=sumPoints;
        squad.addPoints(points);
        return points;
    }

    public int getPoints() {
        return points;
    }
}


