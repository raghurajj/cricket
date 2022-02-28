package com.tekion.cricket.models;

import com.tekion.cricket.interfaces.Observer;
import com.tekion.cricket.enums.PlayerState;
import com.tekion.cricket.helpers.TeamHelper;

import java.util.ArrayList;
import java.util.List;

public class Team implements Observer {
    int id;
    private String teamName;
    private int teamScore;
    private int totalPlayedBalls;
    private List<Player> players;
    private int strikerPlayer;
    private int nonStrikerPlayer;
    private int currentBowler;
    private int nextPlayer;
    private int totalAvailableBalls;
    private boolean isBatting;


    public Team(String teamName, int totalAvailableBalls, String[] teamPlayers)
    {
        this.teamName = teamName;
        this.totalAvailableBalls=totalAvailableBalls;
        players = new ArrayList<>();
        setPlayers(totalAvailableBalls,teamPlayers);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlayers(int totalAvailableBalls, String[] teamPlayers)
    {
        TeamHelper.addPlayers(this,teamPlayers,totalAvailableBalls);
    }

    public boolean isBatting() {
        return isBatting;
    }

    public void setBatting(boolean batting) {
        isBatting = batting;
    }

    public void addPlayer(Player player)
    {
        this.players.add(player);
    }

    public void getReadyToPlay()
    {
        this.setStrikerPlayer(0);
        this.setNonStrikerPlayer(1);
        this.setNextPlayer(2);
    }

    public void setPlayers(List<Player>players)
    {
        this.players = players;
    }

    public void setTeamScore(int teamScore) {
        this.teamScore = teamScore;
    }

    public void setTotalPlayedBalls(int totalPlayedBalls) {
        this.totalPlayedBalls = totalPlayedBalls;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTeamScore() {
        return teamScore;
    }

    public void incrementTeamScore(int runs) {
        if(runs==4)players.get(strikerPlayer).incrementFourCount();
        if(runs==6)players.get(strikerPlayer).incrementSixCount();
        this.teamScore = this.teamScore + runs;

        players.get(strikerPlayer).incrementRunScored(runs);
    }

    public int getTotalPlayedBalls() {
        return totalPlayedBalls;
    }

    public void incrementTotalPlayedBalls() {
        this.totalPlayedBalls = this.totalPlayedBalls +1;
        players.get(getStrikerPlayer()).incrementNumberOfBallPlayed();
    }

    public int getStrikerPlayer() {
        return strikerPlayer;
    }

    public void setStrikerPlayer(int strikerPlayer) {
        players.get(strikerPlayer).setPlayerState(PlayerState.NOT_OUT);
        this.strikerPlayer = strikerPlayer;
    }

    public int getNonStrikerPlayer() {
        return nonStrikerPlayer;
    }

    public void setNonStrikerPlayer(int nonStrikerPlayer) {
        players.get(nonStrikerPlayer).setPlayerState(PlayerState.NOT_OUT);
        this.nonStrikerPlayer = nonStrikerPlayer;
    }

    public int getCurrentBowler() {
        return currentBowler;
    }

    public void setCurrentBowler(int currentBowler) {
        this.currentBowler = currentBowler;
    }

    public int getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(int nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public int getTotalAvailableBalls() {
        return totalAvailableBalls;
    }

    public void setTotalAvailableBalls(int totalAvailableBalls) {
        this.totalAvailableBalls = totalAvailableBalls;
    }

    public void manageBowlersBalls()
    {
        players.get(getCurrentBowler()).decrementNumberOfBallsLeftToBowl();
    }

    public void changeStrike()
    {
        int striker = this.strikerPlayer;
        this.strikerPlayer = this.nonStrikerPlayer;
        this.nonStrikerPlayer = striker;
    }

    public Player getPlayerByIndex(int index)
    {
        return players.get(index);
    }


    public void reset()
    {
        TeamHelper.reset(this,totalAvailableBalls);
    }

    public void printBattingStats()
    {
        TeamHelper.printBattingStats(this);
    }

    public void printBowlingStats()
    {
        TeamHelper.printBowlingStats(this);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void insertIntoScorecard(Match match)
    {
        TeamHelper.insertIntoScorecard(match,this);
    }

    public Player getWicketKeeper(){
        return players.get(4);
    }

    @Override
    public void update(int runs, Team opposition) {
        if(isBatting)
        {
            if(runs==7)
            {
                TeamHelper.handleWicket(this,opposition);
            }
            else{
                this.incrementTeamScore(runs);
                if(runs==1 || runs==3 || runs==5)
                {
                    this.changeStrike();
                }
            }

        }
        else{
            if(runs==7)
            {
                this.getPlayerByIndex(this.getCurrentBowler()).incrementNumberOfWicketsTaken();
            }
            else{
                this.getPlayerByIndex(this.getCurrentBowler()).incrementRunsGiven(runs);
            }
        }
    }

}
