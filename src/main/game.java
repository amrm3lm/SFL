package main;

import java.util.LinkedList;

public class game {


    public static void main(String args[]) {

        /*
        at the start of each game:
        -load players
        -load leagues
        -option to load 1 squad at time
         */
        System.out.println("welcome to fantasy football!");
        System.out.println("Loading...");
        Universe uni = new Universe();
        uni.loadUniverse();

        LinkedList<player> playerLinkedList = uni.returnPlayers();
        LinkedList<squad> squadLinkedList = uni.returnSquad();
        LinkedList<League> leagueLinkedList = uni.leagueList;

        System.out.println("What would you like to do?");
        System.out.println("1 : create squad");
        System.out.println("2 : load squad");
        System.out.println("3 : load league");
    }

}
