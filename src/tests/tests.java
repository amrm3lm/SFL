package tests;

import main.*;
import org.junit.Before;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class tests {

    private LinkedList<player> playerList;
    private String read;
    private squad squad1;
    private squad squad2;
    private squad squad3;
    private squad squad4;
    private LinkedList<squad> squadlist;
    private League league1;
    private League league2;
    private League league3;
    private LinkedList<League> leaguelist;
    @Before
    public void setup(){

        playerList = new LinkedList<>();
        String filename = "HigherUp/listOfPlayers.txt";
        Scanner playerScanner;

        try {
            playerScanner = new Scanner(new File(filename));
            while (playerScanner.hasNext()){
                String name=playerScanner.next();
                String club= playerScanner.next();
                String position = playerScanner.next();

                player p = new player(name, club, position);
                playerList.add(p);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("could not locate file");
        }

        //-------------------------- making squads
        String user1 = "user1";
        squad1 = new squad(user1);

        for (int i = 0; i < 15; i++){
            squad1.addPlayer(playerList.get(i));
        }

        /**
         * for now the next  squads are correct, but later they wont be when we add
         * exceptions to adding random players to a squad in the squad class
         */
        String user2 = "user2";
        squad2 = new squad(user2);

        for (int i = 1; i < 16; i++){
            squad2.addPlayer(playerList.get(i));
        }

        String user3 = "user3";
        squad3 = new squad(user3);

        for (int i = 2; i < 17; i++){
            squad3.addPlayer(playerList.get(i));
        }

        String user4 = "user4";
        squad4 = new squad(user4);

        for (int i = 3; i < 18; i++){
            squad4.addPlayer(playerList.get(i));
        }

        squadlist = new LinkedList<>();
        squadlist.add(squad1);
        squadlist.add(squad2);
        squadlist.add(squad3);
        squadlist.add(squad4);

        //----------------- making leagues
        league1 = new League("league1");
        league1.addTo(squad1);
        league1.addTo(squad2);
        league1.addTo(squad3);
        league1.addTo(squad4);

        league2 = new League("league2");
        league2.addTo(squad2);
        league2.addTo(squad4);

        league3 = new League("league3");
        league3.addTo(squad3);

        leaguelist= new LinkedList<>();
        leaguelist.add(league1);
        leaguelist.add(league2);
        leaguelist.add(league3);
    }

    @Test
    public void changeposition(){

        try {
            squad1.interSquadSwitch(playerList.get(1), playerList.get(3));
        } catch (Exception e) {

        }
        squad1.printSquad();
    }

    @Test
    public void switchplayer(){

        try {
            squad1.interSquadSwitch(playerList.get(18), playerList.get(3));
        } catch (Exception e) {

        }
        squad1.printSquad();
    }

    @Test
    public void switchplayer2(){

        try {
            squad1.interSquadSwitch(playerList.get(19), playerList.get(3));
        } catch (Exception e) {
            System.out.println("exception caught"); //expected, since player 19 isnt in the squad of 15
        }
        squad1.printSquad();
    }

    @Test
    public void switchplayer3(){

        try {
            squad1.replacePlayer(playerList.get(19), playerList.get(3));
        } catch (Exception e) {
            System.out.println("exception caught"); //expect exception, since players arent in same position
        }
        squad1.printSquad();
    }
    @Test
    public void switchplayer4(){

        try {
            squad1.interSquadSwitch(playerList.get(3), playerList.get(11));
        } catch (Exception e) {
            System.out.println("exception caught");
        }
        squad1.printSquad();
    }

    @Test
    public void playerpointmap() { //test to see if correctly getting player points from file
        PointsGenerator pG = new PointsGenerator(playerList, 1);
        pG.generateMap();
        System.out.println(pG.getMap());
        System.out.println(pG.getNotPlayed());
    }

    @Test
    public void serializingtest() { //testing whether serializing and deserializing is working
        Universe uni = new Universe();
        uni.loadUniverse();
        System.out.println(uni.returnPlayers());

        uni.saveUniverse(leaguelist,squadlist);

        Universe uni2 = new Universe();
        uni2.loadUniverse();
        LinkedList<League> leaguesreturn = uni2.returnLeague();
        leaguesreturn.get(1).printLeagueTable();

    }



}
