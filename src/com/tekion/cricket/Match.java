package com.tekion.cricket;

import java.util.concurrent.ThreadLocalRandom;

public class Match {
    private  Team battingTeam;
    private  Team bowlingTeam;
    private String winner;
    private  int totalAvailableBalls;
    private String tossWinner;
    private String battingFirst;

    public Match()
    {

    }

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

    public void switchTeams()
    {
        Team temp = this.battingTeam;
        this.battingTeam = this.bowlingTeam;
        this.bowlingTeam = temp;
    }

    public void setWinner(String winner) {
        this.winner = winner;
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

    public boolean hasLastWicketFallen(Team team)
    {
        return team.getNextPlayer() == 12;
    }

    public void printCurrentMatchStatus(int maxNumberOfBalls,int over)
    {
        System.out.println("Overs: "+(over) +" || "+this.battingTeam.getTeamName()+ "  Score: "+this.battingTeam.getTeamScore()+"/"+(this.battingTeam.getNextPlayer()-2));
        Player striker = this.battingTeam.getPlayerByIndex(this.battingTeam.getStrikerPlayer());
        Player nonStriker = this.battingTeam.getPlayerByIndex(this.battingTeam.getNonStrikerPlayer());
        Player bowler = this.bowlingTeam.getPlayerByIndex(this.bowlingTeam.getCurrentBowler());
        System.out.println("*"+striker.getName()+" "+striker.getRunScored()+"("+striker.getNumberOfBallPlayed()+")");
        System.out.println(nonStriker.getName()+" "+ nonStriker.getRunScored()+"("+nonStriker.getNumberOfBallPlayed()+")");
        int totalBallsBowled = maxNumberOfBalls - bowler.getNumberOfBallsLeftToBowl();
        System.out.println(bowler.getName()+" : "+(totalBallsBowled / 6) + "." + (totalBallsBowled % 6)+" - "+bowler.getRunsGiven()+" - "+bowler.getNumberOfWicketsTaken());
        System.out.println("-------------------------");
    }

    public void playOver(int scoreToChase, BallHelper ballHelper, int over)
    {
        for(int ball=1;ball<=6;ball++)
        {
            if(hasLastWicketFallen(this.battingTeam) || scoreToChase < this.battingTeam.getTeamScore())
                return;

            this.battingTeam.incrementTotalPlayedBalls();
            this.bowlingTeam.manageBowlersBalls();
            System.out.print(over+"."+ball+"| ");
            ballHelper.playBall();

        }

        System.out.println("-------------------------------");
        battingTeam.changeStrike();
    }

    public void simulateInning(int scoreToChase)
    {
        int overs = totalAvailableBalls/6;
        int maxNumberOfBalls =((int) Math.ceil(((this.totalAvailableBalls/6))/5))*6;
        this.battingTeam.setStrikerPlayer(0);
        this.battingTeam.setNonStrikerPlayer(1);
        this.battingTeam.setNextPlayer(2);
        int currentBowler=5;
        BallHelper ballHelper = new BallHelper(this.battingTeam,this.bowlingTeam);
        for(int over=0;over<overs;over++)
        {
            if(hasLastWicketFallen(this.battingTeam) || scoreToChase < this.battingTeam.getTeamScore())
                return;
            this.bowlingTeam.setCurrentBowler(currentBowler);
            printCurrentMatchStatus(maxNumberOfBalls,over);
            playOver(scoreToChase,ballHelper,over);
            if(currentBowler==10)currentBowler=4;
            currentBowler++;
        }
    }

    public void declareWinner()
    {
        if(this.battingTeam.getTeamScore() > this.bowlingTeam.getTeamScore())
        {
            this.setWinner(this.battingTeam.getTeamName());
        }
        else if(this.battingTeam.getTeamScore() < this.bowlingTeam.getTeamScore())
        {
            this.setWinner(this.bowlingTeam.getTeamName());
        }
        else if(this.battingTeam.getTeamScore() == this.bowlingTeam.getTeamScore())
        {
            this.setWinner("TIE");
        }
        else{
            this.setWinner("DRAW");
        }

        System.out.println("\n----------------------------------------------------");
        if(this.getWinner() == "DRAW" || this.getWinner() == "TIE")
            System.out.println(" ||    match result: "+winner+"     ||");
        else
            System.out.println("||   team "+ this.getWinner() +" won the Match      ||");
    }

    public void startMatch()
    {
        int tossWinner = simulateToss();
        this.setTossWinner(tossWinner==1?this.battingTeam.getTeamName():this.bowlingTeam.getTeamName());
        int battingFirst = tossDecision(tossWinner);
        this.setBattingFirst(battingFirst==1?this.battingTeam.getTeamName():this.bowlingTeam.getTeamName());
        if(this.getBattingFirst()== this.bowlingTeam.getTeamName())this.switchTeams();
        printTossResult();

        int firstInningScore;
        System.out.println("----------------------------------------------------");

        simulateInning(Integer.MAX_VALUE);
        firstInningScore = this.battingTeam.getTeamScore();

        System.out.println("\n\n ----------------------------------------------------");
        System.out.println(this.bowlingTeam.getTeamName()+" need "+(firstInningScore+1) +" runs to win ");
        System.out.println("\n\n ----------------------------------------------------");

        this.switchTeams();
        simulateInning(firstInningScore);

        declareWinner();
    }

    public void printTeams()
    {
        System.out.println("team 1:--------------------");
        this.battingTeam.printPlayers();
        System.out.println("team 2:--------------------");
        this.bowlingTeam.printPlayers();
    }


    public void printTossResult()
    {
        if(this.getBattingFirst() == this.getTossWinner())
        {
            System.out.println(this.getTossWinner()+" won the toss and decided to bat first");
        }
        else{
            System.out.println(this.getTossWinner()+" won the toss and decided to bowl first");
        }
    }

    public void printTeamScoreCard()
    {
        this.bowlingTeam.printBattingStats();
        this.battingTeam.printBowlingStats();
        this.battingTeam.printBattingStats();
        this.bowlingTeam.printBowlingStats();
    }


    public void printScoreCard()
    {
        System.out.println("\n--------------------SCORECARD------------------------------\n");

        printTossResult();

        System.out.println("team "+this.bowlingTeam.getTeamName()+" : " + this.bowlingTeam.getTeamScore() +"/"+(this.bowlingTeam.getNextPlayer()-2) +" in "+(this.bowlingTeam.getTotalPlayedBalls()/6) +"."+(this.bowlingTeam.getTotalPlayedBalls()%6) +" overs");
        System.out.println("team "+this.battingTeam.getTeamName()+" : " + this.battingTeam.getTeamScore() +"/"+(this.battingTeam.getNextPlayer()-2) +" in "+(this.battingTeam.getTotalPlayedBalls()/6)+"."+(this.battingTeam.getTotalPlayedBalls()%6) +" overs");

        printTeamScoreCard();

    }
}
