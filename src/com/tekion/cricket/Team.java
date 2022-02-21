package com.tekion.cricket;

import com.tekion.cricket.enums.PlayerState;
import com.tekion.cricket.enums.PlayerType;

import java.util.ArrayList;
import java.util.List;

public class Team implements Observer{
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

    void getReadyToPlay()
    {
        this.setStrikerPlayer(0);
        this.setNonStrikerPlayer(1);
        this.setNextPlayer(2);
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

    public void handleWicket(Team opposition)
    {
        this.getPlayerByIndex(this.getStrikerPlayer()).setPlayerState(PlayerState.OUT);
        this.getPlayerByIndex(this.getStrikerPlayer()).setGotOutTo(opposition.getPlayerByIndex(opposition.getCurrentBowler()));
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

    public void printBattingStats()
    {
        System.out.println("\nteam "+this.getTeamName()+" : " + this.getTeamScore() +"/"+(this.getNextPlayer()-2) +" in "+(this.getTotalPlayedBalls()/6) +"."+(this.getTotalPlayedBalls()%6) +" overs");
        System.out.println("\n Batting Stats : ----\n");

        System.out.printf("%-20.20s  %-20.20s  %-20.20s  %-20.20s %-20.20s  %-20.20s%n", "PlayerName","State","Runs","Balls Played","4s","6s");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        for(int i=0;i<11;i++)
        {
            Player player = this.getPlayerByIndex(i);
            String gotOutTo=player.getPlayerState()== PlayerState.OUT?"got out to "+player.getGotOutTo().getName():"";
            System.out.printf("%-20.20s  %-20.20s  %-20.20s  %-20.20s %-20.20s %-20.20s  %-20.20s%n",player.getName(),player.getPlayerState(),player.getRunScored(),player.getNumberOfBallPlayed(),player.getFourCount(),player.getSixCount(),gotOutTo);
        }
    }

    public void printBowlingStats()
    {
        System.out.println("\n\n Bowling Stats : ----\n");

        int maxNumberOfBalls =((int) Math.ceil(((this.totalAvailableBalls/6))/5))*6;
        System.out.printf("%-20.20s  %-20.20s  %-20.20s  %-20.20s \n", "BowlerName","OversBowled","RunsGIven","WicketsTaken");
        System.out.println("------------------------------------------------------------------");
        for(int i=5;i<11;i++)
        {
            Player bowler = this.getPlayerByIndex(i);
            if((bowler.getPlayerType()== PlayerType.BOWLER) || (bowler.getPlayerType()== PlayerType.ALLROUNDER)) {
                if (bowler.getNumberOfBallsLeftToBowl() != maxNumberOfBalls) {
                    int totalBallsBowled = maxNumberOfBalls - bowler.getNumberOfBallsLeftToBowl();
                    System.out.printf( "%-20.20s  %-20.20s  %-20.20s  %-20.20s \n",bowler.getName(),(totalBallsBowled / 6) + "." + (totalBallsBowled % 6),bowler.getRunsGiven(),bowler.getNumberOfWicketsTaken());
                }
            }
        }

        System.out.println("------------------------------------------------------------------");
    }

    @Override
    public void update(int runs, boolean isBattingTeam, Team opposition) {
        if(isBattingTeam)
        {
            if(runs==7)
            {
                this.handleWicket(opposition);
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
