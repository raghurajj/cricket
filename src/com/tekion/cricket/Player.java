package com.tekion.cricket;

import com.tekion.cricket.enums.PlayerState;
import com.tekion.cricket.enums.PlayerType;

public class Player {

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
    private Player gotOutTo;


    public Player(String name)
    {
        this.name = name;
        playerState = PlayerState.YET_TO_PLAY;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    public Player getGotOutTo() {
        return gotOutTo;
    }

    public void setGotOutTo(Player gotOutTo) {
        this.gotOutTo = gotOutTo;
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
        this.gotOutTo=null;
        this.numberOfBallPlayed=0;
        this.numberOfBallsLeftToBowl = numberOfBallsLeftToBowl;
    }

}
