package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PointsGenerator {
    /*
    this class reads file gameweekxStats.txt, and makes a hashmap
     of the points each player scored, writes result into gameweekxPointMap.txt
     */

    private LinkedList<player> playerList;
    private HashMap<player,Integer> playerPointMap;
    private int gameweek;
    private Scanner statScanner;
    private int numStatCols = 12;
    private HashSet<player> notPlayed;

    public PointsGenerator(LinkedList<player> playerList, int gameweek){
        this.playerList=playerList;
        this.gameweek=gameweek;
        notPlayed = new HashSet<>();
        playerPointMap = new HashMap<>();

        String statsFilename = "HigherUp/gameweek"+String.valueOf(gameweek)+"Stats.txt";
        try {
            statScanner = new Scanner(new File(statsFilename));
        } catch (FileNotFoundException e){
            System.out.println("Could not load points");
        }
    }

    public HashMap<player, Integer> getMap(){

        return new HashMap<player, Integer>(playerPointMap);
    }
    /**
     * requires that statList and playerList both be arranged in the same order of players
     */
    public void generateMap(){
        int j=0;
        do {
            ArrayList<Integer> playerStats = parseLine();
            player p = playerList.get(j);
            int points = calcPoints(p, playerStats);
            playerPointMap.put(p, points);

            j++;
        } while (statScanner.hasNext());

        //write output to file for testing?
    }

    public HashSet<player> getNotPlayed(){
        return this.notPlayed;
    }

    /**
     * IMP: IF player plays 0 points, player added to not played set
     * @param p
     * @param playerStats
     * @return
     */
    private int calcPoints(player p, ArrayList<Integer> playerStats){
        int pointsSum=0;
        int position = p.getPosNum();

        if (playerStats.get(0)==0){
            notPlayed.add(p);
            return 0;
        }

        switch (position) {
            case (0):
                pointsSum = calcPointsGK(playerStats);
            case (1):
                pointsSum = calcPointsD(playerStats);
            case (2):
                pointsSum = calcPointsM(playerStats);
            case (3):
                pointsSum = calcPointsF(playerStats);
        }

        return pointsSum;
    }

    private int calcPointsGK(ArrayList<Integer> playerstats){
        int sumPoints=0;
        int goalMultiplier = 10;
        int assistMultiplier = 6;
        int cleanSheet = 4;
        int goalsConceded=-1/2;
        int yellow = -1;
        int red = -3;
        int saves = 1/4;
        int bonus = 1;
        sumPoints+= addPlaytimePoints(playerstats.get(0)); //adding points due to play time
        sumPoints+= playerstats.get(1)*goalMultiplier; //goals
        sumPoints+= playerstats.get(2)*assistMultiplier;
        sumPoints+= playerstats.get(3)*cleanSheet;
        sumPoints+= playerstats.get(4)*goalsConceded;
        sumPoints+= playerstats.get(5)*yellow;
        sumPoints+= playerstats.get(6)*red;
        sumPoints+= playerstats.get(7)*saves;
        sumPoints+= playerstats.get(8)*bonus;

        return sumPoints;
    }

    private int calcPointsD(ArrayList<Integer> playerstats){
        int sumPoints=0;
        int goalMultiplier = 6;
        int assistMultiplier = 4;
        int cleanSheet = 4;
        int goalsConceded=1/2;
        int yellow = -1;
        int red = -3;
        int saves = 0;
        int bonus = 1;
        sumPoints+= addPlaytimePoints(playerstats.get(0)); //adding points due to play time
        sumPoints+= playerstats.get(1)*goalMultiplier; //goals
        sumPoints+= playerstats.get(2)*assistMultiplier;
        sumPoints+= playerstats.get(3)*cleanSheet;
        sumPoints+= playerstats.get(4)*goalsConceded;
        sumPoints+= playerstats.get(5)*yellow;
        sumPoints+= playerstats.get(6)*red;
        sumPoints+= playerstats.get(7)*saves;
        sumPoints+= playerstats.get(8)*bonus;

        return sumPoints;
    }

    private int calcPointsM(ArrayList<Integer> playerstats){
        int sumPoints=0;
        int goalMultiplier = 5;
        int assistMultiplier = 3;
        int cleanSheet = 1;
        int goalsConceded=0;
        int yellow = -1;
        int red = -3;
        int saves = 0;
        int bonus = 1;
        sumPoints+= addPlaytimePoints(playerstats.get(0)); //adding points due to play time
        sumPoints+= playerstats.get(1)*goalMultiplier; //goals
        sumPoints+= playerstats.get(2)*assistMultiplier;
        sumPoints+= playerstats.get(3)*cleanSheet;
        sumPoints+= playerstats.get(4)*goalsConceded;
        sumPoints+= playerstats.get(5)*yellow;
        sumPoints+= playerstats.get(6)*red;
        sumPoints+= playerstats.get(7)*saves;
        sumPoints+= playerstats.get(8)*bonus;

        return sumPoints;
    }

    private int calcPointsF(ArrayList<Integer> playerstats){
        int sumPoints=0;
        int goalMultiplier = 4;
        int assistMultiplier = 3;
        int cleanSheet = 0;
        int goalsConceded=0;
        int yellow = -1;
        int red = -3;
        int saves = 0;
        int bonus = 1;
        sumPoints+= addPlaytimePoints(playerstats.get(0)); //adding points due to play time
        sumPoints+= playerstats.get(1)*goalMultiplier; //goals
        sumPoints+= playerstats.get(2)*assistMultiplier;
        sumPoints+= playerstats.get(3)*cleanSheet;
        sumPoints+= playerstats.get(4)*goalsConceded;
        sumPoints+= playerstats.get(5)*yellow;
        sumPoints+= playerstats.get(6)*red;
        sumPoints+= playerstats.get(7)*saves;
        sumPoints+= playerstats.get(8)*bonus;

        return sumPoints;
    }

    private int addPlaytimePoints(int mins){
        int maxPlaytimePoints = 2;
        int Under60Points = 1;
        int returnval;
        if (mins >= 60){
            return maxPlaytimePoints;
        } else if (mins > 0){
            return Under60Points;
        } else {
            return 0;
        }
    }
    /**
     * in arraylist :
     *  0 - mins played
     *  1 - goals scored
     *  2 - assists
     *  3 - cleansheet
     *  4 - goals conceded
     *  5 - yellow cards
     *  6 - red cards
     *  7 - saves
     *  8 - bonus
     * @param
     * @return
     */
    private ArrayList<Integer> parseLine() {

        ArrayList<Integer> playerStats = new ArrayList<>();
        for (int i = 0; i < numStatCols ; i++){
            if (i==0 || i==1 || i==2){
                statScanner.next();
            } else {
                Integer val = Integer.parseInt(statScanner.next());
                playerStats.add(val);
            }
        }
        return playerStats;
    }
}
