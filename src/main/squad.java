package main;

import Exceptions.NotinSquad;
import Exceptions.SwitchPlayerException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class squad implements Serializable{
    private int MAXGK= 1;
    private int MAXD = 5;
    private int MAXM = 5;
    private int MAXF = 3;
    private int BENCH = 4;
    private int MINGK = 1;
    private int MIND = 3;
    private int MINM = 3;
    private int MINF = 1;
    private int MAXSQUAD = 15;
    private HashSet<player> squad;
    private String user;
    private ArrayList<LinkedList<player>> formation;
    private player captain;
    private player viceCaptain;
    private int totalPoints;

    public squad(String user){
        this.user=user;
        squad=new HashSet<player>();
        formation = new ArrayList<LinkedList<player>>();

        for (int i=0; i<5; i++){
            LinkedList<player> pos = new LinkedList();
            formation.add(pos);
        }

        totalPoints=0;
    }

    public void addPoints(int points) {
        totalPoints+=points;
    }

    /**
     * main.squad size must be less than 15
     * @param player
     */
    public void addPlayer(player player){
        int pos= player.getPosNum();
        if (squad.size()<MAXSQUAD){
            squad.add(player);
//need to add something that prevents adding 10 midfielders for example
            if (formation.get(pos).size()<initialmaxPos(pos)){
                formation.get(pos).add(player);
            } else{
                formation.get(4).add(player);
            }
        } else {
            System.out.println("Error : Cannot add main.player, main.squad size full");
        }

    }



    /**
     * players must have same positions
     * @param toAdd
     * @param toSwitch
     */
    public void replacePlayer(player toAdd, player toSwitch) throws SwitchPlayerException{
        if (toAdd.getPosNum()!=toSwitch.getPosNum()){
            throw new SwitchPlayerException("Error: players not in same positions cannot be switched");
        } else{
            squad.remove(toAdd);
            squad.add(toSwitch);

            int[] playerPos = formationPos(toAdd);
            formation.get(playerPos[1]).set(playerPos[1], toSwitch);
        }
    }

    /**
     * players must be ordered in the list, ie m --- m , d --- d
     * @param playerstoAdd
     * @param playerToSwitch
     */
    public void switchPlayers (List<player> playerstoAdd, List<player> playerToSwitch) throws SwitchPlayerException{
        int numPlayers = playerstoAdd.size();
        if (numPlayers != playerToSwitch.size()){
            throw new SwitchPlayerException("List of added players does not match list of replaced players");
        }

        for (int i=0; i<numPlayers; i++){
            try {
                replacePlayer(playerstoAdd.get(i), playerToSwitch.get(i));
            } catch (Exception ex) {
                System.out.println("quitting process");
                break;
            }
        }
    }

    public void printSquad() {
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < formation.get(j).size(); i++) {
                if (j<4) {
                    player p = formation.get(j).get(i);
                    System.out.print(p.getName() + "              ");
                } else {
                    player p = formation.get(j).get(i);
                    System.out.print(p.getName() + "  ");
                }
            }
            System.out.println();
        }
        System.out.print("Bench:    ");
        for (int i = 0; i<formation.get(4).size(); i++){
            player p = formation.get(4).get(i);
            System.out.print(p.getName()+ "  ");
        }
    }

    /**
     * one main.player must be in the bench, or both players must play in the same outfield position
     * @param p1
     * @param p2
     * @throws NotinSquad
     */
    public void interSquadSwitch(player p1, player p2) throws SwitchPlayerException{
        if (!(squad.contains(p1) || squad.contains(p2))){
            throw new SwitchPlayerException("at least one of the players isnt in the squad");
        }

        int[] p1FP = formationPos(p1);
        int[] p2FP = formationPos(p2);
        player benchP, starterP;
        int[] starterFP;
        int[] benchFP;

        if (p1.getPosition().equals(p2.getPosition())){
            formation.get(p1FP[0]).set(p1FP[1], p2);
            formation.get(p2FP[0]).set(p2FP[1], p1);

        } else {
            if (!(formation.get(4).contains(p1) || formation.get(4).contains(p2))){
                throw new SwitchPlayerException("Players not in the same position"); //one of the players isnt on the bench
            } else {
                if (p2FP[0]==4){
                     benchP = p2;
                     starterP = p1;
                     starterFP=formationPos(starterP);
                     benchFP = formationPos(benchP);


                } else {
                     benchP= p1;
                     starterP= p2;
                    starterFP=formationPos(starterP);
                    benchFP = formationPos(benchP);
                }

                if (isValidFormation(p1,benchP)){
                    formation.get(benchFP[0]).set(benchFP[1], starterP);
                    formation.get(starterFP[0]).remove(starterP);
                    formation.get(benchP.getPosNum()).add(benchP);
                } else {
                    throw new SwitchPlayerException("switch results in invalid formation");
                }
            }
        }
    }

    public void addtoLeague(League league) {
        league.addTo(this);
    }

    public ArrayList getFormation(){
        ArrayList<LinkedList<player>> returnlist = new ArrayList<LinkedList<player>>(formation);
        return returnlist;
    }

    public String getUser() {
        return user;
    }
    public int getTotalPoints() {
        return this.totalPoints;
    }
    private void writeFormation() {
        String filename = user+"Squad.txt";
    }

    private String checkFormation(){
        int numD, numM, numF;
        numD=formation.get(1).size();
        numM=formation.get(2).size();
        numF=formation.get(3).size();

        StringBuilder formationNum = new StringBuilder();
        formationNum.append(numD+numM+numF);
        return formationNum.toString();
    }


    private int maxPos(int pos){
        switch (pos){
            case 0:
                return MAXGK;
            case 1:
                return MAXD;
            case 2:
                return MAXM;
            case 3:
                return MAXF;
        }
        return BENCH;
    }

    /**
     * used when initializing a squad, defualt formation will be 442
     * @param pos
     * @return
     */
    private int initialmaxPos(int pos){
        switch (pos){
            case 0:
                return 1;
            case 1:
                return 4;
            case 2:
                return 4;
            case 3:
                return 2;
        }
        return BENCH;
    }

    private int[] formationPos(player player){
        int position = player.getPosNum();
        int[] formation_pos = new int[2];
        for (int i=0; i<formation.get(player.getPosNum()).size(); i++){
            if (formation.get(position).get(i).equals(player)){
                formation_pos[0]=position;
                formation_pos[1]=i;
                return formation_pos;
            }
        }

        for (int i = 0; i< BENCH; i++){
            if (formation.get(4).get(i).equals(player)){
                formation_pos[0]=4;
                formation_pos[1]=i;
                return formation_pos;
            }
        }
        return formation_pos;
    }

    /**
     * checks if switching a main.player in the bench (benchP) with a main.player in the formation is valid
     * @param p1
     * @param benchP
     * @return
     */
    public Boolean isValidFormation( player p1, player benchP){


        if (p1.getPosNum()==benchP.getPosNum()){
                return true;
        }

        String formation = checkFormation();
        int[] p1FP = formationPos(p1);
        int[] p2FP = formationPos(benchP);

        int numPlayersinP1pos = this.formation.get(p1FP[0]).size();
        int numPlayerinP2pos = this.formation.get(p2FP[0]).size();
        int targetPosSize = this.formation.get(benchP.getPosNum()).size();

        if (numPlayersinP1pos==minPos(p1.getPosNum()) || targetPosSize == maxPos(benchP.getPosNum())){
            return false;
        }

        return true;
    }

    private int minPos(int position){
        switch (position){
            case 0:
                return MINGK;
            case 1:
                return MIND;
            case 2:
                return MINM;
            case 3:
                return MINF;
        }
        return BENCH;
    }

}
