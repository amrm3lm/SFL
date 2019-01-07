package main;

import java.io.Serializable;

public class player implements Serializable{

    private String name;
    private String Position;
    private String club;

    public player (String name, String club, String position){
        this.name = name;
        this.Position=position;
        this.club=club;
    }


    public int getPosNum(){
        switch (Position){
            case "gk":
                return 0;
            case "d":
                return 1;
            case "m":
                return 2;
            case "f":
                return 3;
        }
        return -1;
    }

    public String getName(){
        return name;
    }

    public String getPosition(){
        return Position;
    }
}
