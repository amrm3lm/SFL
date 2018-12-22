package tests;

import main.PointsGenerator;
import main.player;
import main.squad;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class tests {

    private LinkedList<player> playerList;
    private String read;
    private squad squad;
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

        String user = "user1";
        squad = new squad(user);

        for (int i = 0; i < 15; i++){
            squad.addPlayer(playerList.get(i));
        }
    }

    @Test
    public void changeposition(){

        try {
            squad.interSquadSwitch(playerList.get(1), playerList.get(3));
        } catch (Exception e) {

        }
        squad.printSquad();
    }

    @Test
    public void switchplayer(){

        try {
            squad.interSquadSwitch(playerList.get(18), playerList.get(3));
        } catch (Exception e) {

        }
        squad.printSquad();
    }

    @Test
    public void switchplayer2(){

        try {
            squad.interSquadSwitch(playerList.get(19), playerList.get(3));
        } catch (Exception e) {
            System.out.println("exception caught"); //expected, since player 19 isnt in the squad of 15
        }
        squad.printSquad();
    }

    @Test
    public void switchplayer3(){

        try {
            squad.replacePlayer(playerList.get(19), playerList.get(3));
        } catch (Exception e) {
            System.out.println("exception caught"); //expect exception, since players arent in same position
        }
        squad.printSquad();
    }
    @Test
    public void switchplayer4(){

        try {
            squad.interSquadSwitch(playerList.get(3), playerList.get(11));
        } catch (Exception e) {
            System.out.println("exception caught");
        }
        squad.printSquad();
    }

    @Test
    public void playerpointmap() { //test to see if correctly getting player points from file
        PointsGenerator pG = new PointsGenerator(playerList, 1);
        pG.generateMap();
        System.out.println(pG.getMap());
        System.out.println(pG.getNotPlayed());
    }





}
