package main;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class Universe implements Serializable{

    LinkedList<League> leagueList;
    LinkedList<squad> squadList;
    LinkedList<player> playerList;

    public Universe() {
        this.leagueList = new LinkedList<League>();
        this.squadList = new LinkedList<squad>();
        playerList= new LinkedList<player>();

    }

    public void loadUniverse() {

        loadPlayers();
        deserializeLeagues();
        deserializeSquads();
    }

    public void saveUniverse(LinkedList<League> leagues, LinkedList<squad> squads) {

     serializeLeagues(leagues);
     serializeSquads(squads);
    }


    public LinkedList<League> returnLeague() {
        return leagueList;
    }

    public LinkedList<player> returnPlayers() {
        return playerList;
    }

    public LinkedList<squad> returnSquad() {
        return squadList;
    }

    private void deserializeLeagues() {
        try {
            FileInputStream fileIn = new FileInputStream("HigherUp/leagues.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            leagueList = (LinkedList<League>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            //i.printStackTrace();
            System.out.println("league file not found");
        } catch (ClassNotFoundException c) {
            System.out.println("league class not found");
            c.printStackTrace();
        }
    }

    private void deserializeSquads() {
        try {
            FileInputStream fileIn = new FileInputStream("HigherUp/squads.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            squadList = (LinkedList<squad>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            //i.printStackTrace();
            System.out.println("squad file not found");
        } catch (ClassNotFoundException c) {
            System.out.println("squad class not found");
            //c.printStackTrace();
        }
    }

    /**
     * where list of leagues is given from game class and this method is called
     * after the game is quit so as to save the updated leagues in file
     * @param leagues
     */
    private void serializeLeagues(LinkedList<League> leagues) {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("HigherUp/leagues.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(leagues);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in leagues.ser");
        } catch (IOException i) {
            //i.printStackTrace();
            System.out.println("unable to serialize league");
        }
    }

    private void serializeSquads(LinkedList<squad> squads) {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("HigherUp/squads.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(squads);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in squads.ser");
        } catch (IOException i) {
           // i.printStackTrace();
            System.out.println("unable to serializes squad");
        }
    }

    private void loadPlayers() {
        String filename = "HigherUp/listOfPlayers.txt";
        Scanner playerreader;
        try {
            playerreader = new Scanner(new File(filename));
            while (playerreader.hasNext()) {
                String name = playerreader.next();
                String club = playerreader.next();
                String pos = playerreader.next();
                player temp = new player(name, club, pos);
                playerList.add(temp);
            }
        } catch (FileNotFoundException e){
            System.out.println("Could not load players");
        }

    }
}
