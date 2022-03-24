package com.tekion.cricket.beans;
import com.tekion.cricket.enums.PlayerState;
import com.tekion.cricket.enums.PlayerType;
import org.springframework.stereotype.Component;

/*
holds all the necessary info about a player
 */
public class Player {
    int id;
    private String name;
    private int runScored;
    private PlayerState playerState;
    private int numberOfBallPlayed;
    PlayerType playerType;
    private int numberOfWicketsTaken;
    private int numberOfBallsLeftToBowl;
    private int fourCount;
    private int sixCount;
    private int runsGiven;
    private Wicket wicket;


    public Player(){

    }

    public Player(String name)
    {
        this.name = name;
        playerState = PlayerState.YET_TO_PLAY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    public Wicket getWicket() {
        return wicket;
    }

    public void setWicket(Wicket wicket) {
        this.wicket = wicket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRunScored() {
        return runScored;
    }

    public void incrementRunScored(int runs) {
        this.runScored = this.runScored + runs;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public int getNumberOfBallPlayed() {
        return numberOfBallPlayed;
    }

    public void incrementNumberOfBallPlayed() {
        this.numberOfBallPlayed = this.numberOfBallPlayed +1;
    }


    public int getNumberOfWicketsTaken() {
        return numberOfWicketsTaken;
    }

    public void incrementNumberOfWicketsTaken() {
        this.numberOfWicketsTaken = this.numberOfWicketsTaken +1;
    }

    public int getNumberOfBallsLeftToBowl() {
        return numberOfBallsLeftToBowl;
    }

    public void decrementNumberOfBallsLeftToBowl() {
        this.numberOfBallsLeftToBowl = this.numberOfBallsLeftToBowl-1;
    }

    public void setNumberOfBallsLeftToBowl(int numberOfBallsLeftToBowl) {
        this.numberOfBallsLeftToBowl = numberOfBallsLeftToBowl;
    }

    public int getFourCount() {
        return fourCount;
    }

    public void incrementFourCount() {
        this.fourCount = this.fourCount+1;
    }

    public int getSixCount() {
        return sixCount;
    }

    public void incrementSixCount() {
        this.sixCount = this.sixCount+1;
    }

    public int getRunsGiven() {
        return runsGiven;
    }

    public void incrementRunsGiven(int runs) {
        this.runsGiven = this.runsGiven + runs;
    }



    public void reset(int numberOfBallsLeftToBowl)
    {
        this.playerState= PlayerState.YET_TO_PLAY;
        this.fourCount=0;
        this.sixCount=0;
        this.runScored=0;
        this.runsGiven=0;
        this.numberOfWicketsTaken=0;
        this.wicket =null;
        this.numberOfBallPlayed=0;
        this.numberOfBallsLeftToBowl = numberOfBallsLeftToBowl;
    }



}
