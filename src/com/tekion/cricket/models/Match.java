package com.tekion.cricket.models;

import com.tekion.cricket.constants.StringUtils;
import com.tekion.cricket.interfaces.Subject;
import com.tekion.cricket.services.BallService;
import com.tekion.cricket.helpers.MatchHelper;

import java.util.concurrent.ThreadLocalRandom;

public class Match {
    private Team battingTeam;
    private  Team bowlingTeam;
    private String winner;
    private  int totalAvailableBalls;
    private String tossWinner;
    private String battingFirst;
    private Subject ballService; //subject


    public Match(Team firstTeam, Team secondTeam, int totalAvailableBalls)
    {
        this.battingTeam=firstTeam;
        this.bowlingTeam=secondTeam;
        this.totalAvailableBalls=totalAvailableBalls;
        this.setWinner("STARTED");
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

    public int simulateToss()
    {
        int random = ThreadLocalRandom.current().nextInt(0,100);
        return (random%2==0?1:2);
    }

    public int tossDecision(int tossWinner)
    {
        int random = ThreadLocalRandom.current().nextInt(0,100);
        return (tossWinner&random )%2==0?1:2;
    }

    public void startMatch()
    {
        int tossWinner = simulateToss();
        setTossWinner(tossWinner==1?battingTeam.getTeamName():bowlingTeam.getTeamName());
        int battingFirst = tossDecision(tossWinner);
        setBattingFirst(battingFirst==1?battingTeam.getTeamName():bowlingTeam.getTeamName());
        if(getBattingFirst().equals(bowlingTeam.getTeamName()))switchTeams();
        printTossResult();

        int firstInningScore;
        System.out.println(StringUtils.DOT_LINE);
        battingTeam.setBatting(true);

        MatchHelper.simulateInning(battingTeam,bowlingTeam,ballService,totalAvailableBalls,Integer.MAX_VALUE);
        battingTeam.setBatting(false);
        firstInningScore = battingTeam.getTeamScore();

        System.out.println("\n\n"+StringUtils.DOT_LINE);
        System.out.println(bowlingTeam.getTeamName()+" need "+(firstInningScore+1) +" runs to win ");
        System.out.println("\n\n"+StringUtils.DOT_LINE);

        switchTeams();
        battingTeam.setBatting(true);
        MatchHelper.simulateInning(battingTeam,bowlingTeam,ballService,totalAvailableBalls,firstInningScore);
        battingTeam.setBatting(false);
        MatchHelper.declareWinner(this,battingTeam,bowlingTeam);
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
        MatchHelper.printScoreCard(battingTeam,bowlingTeam);
    }
}
