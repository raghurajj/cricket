package com.tekion.cricket.beans;
import com.tekion.cricket.helpers.MatchHelper;
import org.springframework.stereotype.Component;


/*
stores data of a match
i.e. batting team,bowling team [switched after an inning]
winner etc.
 */
public class Match {
    int id;
    private Team battingTeam;
    private  Team bowlingTeam;
    private String winner;
    private  int totalAvailableBalls;
    private String tossWinner;
    private String battingFirst;
    int seriesId;

    public Match(){

    }

    public Match(Team firstTeam, Team secondTeam, int totalAvailableBalls)
    {
        this.battingTeam=firstTeam;
        this.bowlingTeam=secondTeam;
        this.totalAvailableBalls=totalAvailableBalls;
        this.setWinner("STARTED");
        this.seriesId=-1;
    }

    public int getId() {
        return id;
    }

    public int getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(int seriesId) {
        this.seriesId = seriesId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void switchTeams()
    {
        Team temp = this.battingTeam;
        this.battingTeam = this.bowlingTeam;
        this.bowlingTeam = temp;
    }

    public Team getBattingTeam() {
        return battingTeam;
    }


    public Team getBowlingTeam() {
        return bowlingTeam;
    }


    public int getTotalAvailableBalls() {
        return totalAvailableBalls;
    }

    public void reset()
    {
        this.battingTeam.reset();
        this.bowlingTeam.reset();
    }

    public String getTossWinner() {
        return tossWinner;
    }

    public void setTossWinner(String tossWinner) {
        this.tossWinner = tossWinner;
    }

    public String getBattingFirst() {
        return battingFirst;
    }

    public void setBattingFirst(String battingFirst) {
        this.battingFirst = battingFirst;
    }

    public void startMatch()
    {
        MatchHelper.startMatch(this);
    }

    public void printTossResult()
    {
        if(this.getBattingFirst().equals(this.getTossWinner()))
        {
            System.out.println(this.getTossWinner()+" won the toss and decided to bat first");
        }
        else{
            System.out.println(this.getTossWinner()+" won the toss and decided to bowl first");
        }
    }

    public void printScoreCard()
    {
        printTossResult();
        MatchHelper.printScoreCard(battingTeam,bowlingTeam,this);
    }

    public void insertScorecardIntoDb()
    {
        MatchHelper.insertScorecardIntoDb(this,bowlingTeam,battingTeam);
    }
}
