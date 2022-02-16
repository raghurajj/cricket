package com.tekion.cricket;

import com.tekion.cricket.enums.PlayerState;
import com.tekion.cricket.enums.PlayerType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Team {
    private String teamName;
    private int teamScore;
    private int totalPlayedBalls;
    private final List<Player> players;
    private int strikerPlayer;
    private int nonStrikerPlayer;
    private int currentBowler;
    private int nextPlayer;
    private int totalAvailableBalls;


    public Team(String teamName, int totalAvailableBalls, String[] teamPlayers)
    {
        this.teamName = teamName;
        this.totalAvailableBalls=totalAvailableBalls;
        players = new ArrayList<>();
        setPlayers(totalAvailableBalls,teamPlayers);

    }

    void setPlayers(int totalAvailableBalls, String[] teamPlayers)
    {
        String playerName;
        Scanner sc= new Scanner(System.in);
        int overs = totalAvailableBalls/6;
        int oversPerBowler = (int) Math.ceil(overs/5);
        for(int i=0;i<11;i++)
        {
            playerName = teamPlayers[i];
            Player player = new Player(playerName);
            if(i<5){
                player.setPlayerType(PlayerType.BATSMAN);
            }
            else if(i==5)
            {
                player.setPlayerType(PlayerType.ALLROUNDER);
                player.setNumberOfBallsLeftToBowl(oversPerBowler*6);
            }
            else{
                player.setPlayerType(PlayerType.BOWLER);
                player.setNumberOfBallsLeftToBowl(oversPerBowler*6);
            }
            players.add(player);
        }
    }

    void printPlayers()
    {
        for(int i=0;i<11;i++)
            System.out.println(players.get(i).getName());
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

    public void handleWicket()
    {

        this.strikerPlayer = this.nextPlayer;
        if(this.nextPlayer<11){
            players.get(this.nextPlayer).setPlayerState(PlayerState.NOT_OUT);
        }
        this.nextPlayer = this.nextPlayer+1;
    }

    void reset()
    {
        this.teamScore=0;
        this.nextPlayer=0;
        this.strikerPlayer=0;
        this.nonStrikerPlayer=0;
        this.currentBowler=0;
        this.totalPlayedBalls=0;
        int overs = totalAvailableBalls/6;
        int oversPerBowler = (int) Math.ceil(overs/5);
        for(int i=0;i<11;i++)
        {
            this.players.get(i).reset(oversPerBowler*6);
        }
    }

}
