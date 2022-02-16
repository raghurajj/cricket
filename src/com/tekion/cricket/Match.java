package com.tekion.cricket;

import com.tekion.cricket.enums.Winner;
import java.util.concurrent.ThreadLocalRandom;

public class Match {
    private  Team firstTeam;
    private  Team secondTeam;
    private Winner winner;
    private  int totalAvailableBalls;
    private int tossWinner;
    private int battingFirst;

    public Match()
    {

    }

    public Match(Team firstTeam, Team secondTeam, int totalAvailableBalls)
    {
        this.firstTeam=firstTeam;
        this.secondTeam=secondTeam;
        this.totalAvailableBalls=totalAvailableBalls;
        winner=Winner.STARTED;
    }

    public Winner getWinner() {
        return winner;
    }

    public void setWinner(Winner winner) {
        this.winner = winner;
    }

    void reset()
    {
        this.firstTeam.reset();
        this.secondTeam.reset();
    }

    public int getTossWinner() {
        return tossWinner;
    }

    public void setTossWinner(int tossWinner) {
        this.tossWinner = tossWinner;
    }

    public int getBattingFirst() {
        return battingFirst;
    }

    public void setBattingFirst(int battingFirst) {
        this.battingFirst = battingFirst;
    }

    int simulateToss()
    {
        int random = ThreadLocalRandom.current().nextInt(0,100);
        return (random%2==0?1:2);
    }

    int tossDecision(int tossWinner)
    {
        int random = ThreadLocalRandom.current().nextInt(0,100);
        return (tossWinner&random )%2==0?1:2;
    }

    boolean hasLastWicketFallen(Team team)
    {
        return team.getNextPlayer() == 12;
    }

    public void printCurrentMatchStatus(Team firstTeam, Team secondTeam, int maxNumberOfBalls,int over)
    {
        System.out.println("Over Number: "+(over+1) +" || "+firstTeam.getTeamName()+ "  Score: "+firstTeam.getTeamScore()+"/"+(firstTeam.getNextPlayer()-2));
        Player striker = firstTeam.getPlayerByIndex(firstTeam.getStrikerPlayer());
        Player nonStriker = firstTeam.getPlayerByIndex(firstTeam.getNonStrikerPlayer());
        Player bowler = secondTeam.getPlayerByIndex(secondTeam.getCurrentBowler());
        System.out.println("*"+striker.getName()+" "+striker.getRunScored()+"("+striker.getNumberOfBallPlayed()+")");
        System.out.println(nonStriker.getName()+" "+ nonStriker.getRunScored()+"("+nonStriker.getNumberOfBallPlayed()+")");
        int totalBallsBowled = maxNumberOfBalls - bowler.getNumberOfBallsLeftToBowl();
        System.out.println(bowler.getName()+" : "+(totalBallsBowled / 6) + "." + (totalBallsBowled % 6)+" - "+bowler.getRunsGiven()+" - "+bowler.getNumberOfWicketsTaken());
        System.out.println("-------------------------");
    }

    public void playOver(Team battingTeam, Team bowlingTeam, int scoreToChase, BallHelper ballHelper, int over)
    {
        for(int ball=1;ball<=6;ball++)
        {
            if(hasLastWicketFallen(battingTeam) || scoreToChase < battingTeam.getTeamScore())
                return;

            battingTeam.incrementTotalPlayedBalls();
            bowlingTeam.manageBowlersBalls();
            System.out.print(over+"."+ball+"| ");
            ballHelper.playBall();

        }

        System.out.println("-------------------------------");
        battingTeam.changeStrike();
    }

    void simulateInning(Team battingTeam, Team bowlingTeam, int scoreToChase)
    {
        int overs = totalAvailableBalls/6;
        int maxNumberOfBalls =((int) Math.ceil(((this.totalAvailableBalls/6))/5))*6;
        battingTeam.setStrikerPlayer(0);
        battingTeam.setNonStrikerPlayer(1);
        battingTeam.setNextPlayer(2);
        int currentBowler=5;
        BallHelper ballHelper = new BallHelper(battingTeam,bowlingTeam);
        for(int over=0;over<overs;over++)
        {
            if(hasLastWicketFallen(battingTeam) || scoreToChase < battingTeam.getTeamScore())
                return;
            bowlingTeam.setCurrentBowler(currentBowler);
            printCurrentMatchStatus(battingTeam,bowlingTeam, maxNumberOfBalls,over);
            playOver(battingTeam,bowlingTeam,scoreToChase,ballHelper,over);
            if(currentBowler==10)currentBowler=4;
            currentBowler++;
        }
    }

    void declareWinner(Team firstTeam, Team secondTeam)
    {
        if(firstTeam.getTeamScore() > secondTeam.getTeamScore())
        {
            winner = Winner.FIRSTTEAM;
        }
        else if(firstTeam.getTeamScore() < secondTeam.getTeamScore())
        {
            winner = Winner.SECONDTEAM;
        }
        else if(firstTeam.getTeamScore() == secondTeam.getTeamScore())
        {
            winner = Winner.TIE;
        }
        else{
            winner = Winner.DRAW;
        }

        System.out.println("\n----------------------------------------------------");
        if(winner == Winner.DRAW || winner == Winner.TIE)
            System.out.println(" ||    match result: "+winner+"     ||");
        else
            System.out.println("||   team "+(winner==Winner.FIRSTTEAM?firstTeam.getTeamName():secondTeam.getTeamName()) +" won the Match      ||");
    }

    void startMatch()
    {
        int tossWinner = simulateToss();
        this.setTossWinner(tossWinner);
        int battingFirst = tossDecision(tossWinner);
        this.setBattingFirst(battingFirst);
        printTossResult();
        int firstInningScore;
        System.out.println("----------------------------------------------------");
        Team battingFirstTeam = (battingFirst==1?firstTeam:secondTeam);
        Team battingSecondTeam = (battingFirst==1?secondTeam:firstTeam);

        simulateInning(battingFirstTeam,battingSecondTeam,Integer.MAX_VALUE);
        firstInningScore = battingFirstTeam.getTeamScore();

        System.out.println("\n\n ----------------------------------------------------");
        System.out.println(battingSecondTeam.getTeamName()+" need "+(firstInningScore+1) +" runs to win ");
        System.out.println("\n\n ----------------------------------------------------");

        simulateInning(battingSecondTeam,battingFirstTeam,firstInningScore);

        declareWinner(firstTeam,secondTeam);
    }

    public void printTeams()
    {
        System.out.println("team 1:--------------------");
        firstTeam.printPlayers();
        System.out.println("team 2:--------------------");
        secondTeam.printPlayers();
    }


    public void printTossResult()
    {
        if(this.getBattingFirst() == this.getTossWinner())
        {
            if(this.getTossWinner()==1)
            {
                System.out.println(firstTeam.getTeamName()+" won the toss and decided to Bat First");
            }
            else{
                System.out.println(secondTeam.getTeamName()+" won the toss and decided to Bat First");
            }
        }
        else{
            if(this.getTossWinner()==1)
            {
                System.out.println(firstTeam.getTeamName()+" won the toss and decided to Bowl First");
            }
            else{
                System.out.println(secondTeam.getTeamName()+" won the toss and decided to Bowl First");
            }
        }
    }

    public void printTeamScoreCard(Team battingTeam, Team bowlingTeam)
    {
        battingTeam.printBattingStats();
        bowlingTeam.printBowlingStats();
    }


    public void printScoreCard()
    {
        System.out.println("\n--------------------SCORECARD------------------------------\n");

        printTossResult();

        Team battingFirst = this.getBattingFirst()==1?firstTeam:secondTeam;
        Team battingSecond = this.getBattingFirst()==1?secondTeam:firstTeam;

        System.out.println("team "+battingFirst.getTeamName()+" : " + battingFirst.getTeamScore() +"/"+(battingFirst.getNextPlayer()-2) +" in "+(battingFirst.getTotalPlayedBalls()/6) +"."+(battingFirst.getTotalPlayedBalls()%6) +" overs");
        System.out.println("team "+battingSecond.getTeamName()+" : " + battingSecond.getTeamScore() +"/"+(battingSecond.getNextPlayer()-2) +" in "+(battingSecond.getTotalPlayedBalls()/6)+"."+(battingSecond.getTotalPlayedBalls()%6) +" overs");

        printTeamScoreCard(battingFirst,battingSecond);
        printTeamScoreCard(battingSecond,battingFirst);

    }
}
