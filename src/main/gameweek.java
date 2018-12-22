package main;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class gameweek {

    private int num;
    private LinkedList<squad> squadList;
    private LinkedList<player> playerList;
    private HashMap<squad,Integer> squadPointMap;

    public gameweek (int num, LinkedList<squad> squadList, LinkedList<player> playerList) {
        this.num=num;
        this.squadList=squadList;
        this.playerList=playerList;

    }

    public void runGameweek() {

        PointsGenerator PG = new PointsGenerator(playerList, num);
        PG.generateMap();
        HashMap<player, Integer> playerPointMap = PG.getMap();
        HashSet<player> notPlayed = PG.getNotPlayed();

        for (int i = 0; i < squadList.size(); i++) {
            GameweekSquad gwSquad = new GameweekSquad(squadList.get(i),num, notPlayed, playerList.get(5));
            gwSquad.calcPoints(playerPointMap);
            int points = gwSquad.getPoints();
            squadPointMap.put(squadList.get(i), points);
        }

        ////update leageue standings
    }


}
