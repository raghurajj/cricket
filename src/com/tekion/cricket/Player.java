package com.tekion.cricket;

public class Player {

    enum State{
        OUT,
        NOT_OUT,
        YET_TO_PLAY
    }
    private String name;
    private int runScored;
    private State playerState;
    private int numberOfBallPlayed;
    private boolean isBatsmen;
    private boolean isBowler;
    private boolean isAllRounder;
    private int numberOfWicketsTaken;
    private int numberOfBallsLeftToBowl;
    private int fourCount;
    private int sixCount;
    private Player gotOutTo;


    public Player(String name)
    {
        this.name = name;
        playerState = State.YET_TO_PLAY;
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

    public State getPlayerState() {
        return playerState;
    }

    public void setPlayerState(State playerState) {
        this.playerState = playerState;
    }

    public int getNumberOfBallPlayed() {
        return numberOfBallPlayed;
    }

    public void incrementNumberOfBallPlayed() {
        this.numberOfBallPlayed = this.numberOfBallPlayed =1;
    }

    public boolean getBatsmen() {
        return isBatsmen;
    }

    public void setBatsmen(boolean batsmen) {
        isBatsmen = batsmen;
    }

    public boolean getBowler() {
        return isBowler;
    }

    public void setBowler(boolean bowler) {
        isBowler = bowler;
    }

    public boolean getAllRounder() {
        return isAllRounder;
    }

    public void setAllRounder(boolean allRounder) {
        isAllRounder = allRounder;
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
}
