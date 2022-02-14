package com.tekion.cricket;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Match {
    private final Team team1;
    private final Team team2;
    enum Winner{
        TEAM1,
        TEAM2,
        TIE,
        STARTED,
        DRAW,
    }
    private Winner winner;
    private final int totalAvailableBalls;

    public Match()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Number of Overs in the Match");
        totalAvailableBalls = sc.nextInt()*6;
        sc.nextLine();
        String teamName;
        System.out.println("Enter Name of the first Team");
        teamName = sc.nextLine();
        team1 = new Team(teamName, totalAvailableBalls);
        System.out.println("Enter Name of the Second Team");
        teamName = sc.nextLine();
        team2 = new Team(teamName,totalAvailableBalls);
        winner = Winner.STARTED;
    }

    int simulateToss()
    {
        int rand = ThreadLocalRandom.current().nextInt(0,100);
        if(rand%2==0)
            return 1;
        else return 2;
    }

    int tossDecision(int tossWinner)
    {
        int rand = ThreadLocalRandom.current().nextInt(0,100);
        if(rand%2==0)
            return tossWinner==1?1:2;
        else return tossWinner==1?2:1;
    }

    boolean hasLastWicketFallen(Team team)
    {
        return team.getNextPlayer() == 12;
    }

    void handleWicket(Team team1, Team team2)
    {
        Player bowler = team2.getPlayerByIndex(team2.getCurrentBowler());
        bowler.incrementNumberOfWicketsTaken();
        Player batsmen = team1.getPlayerByIndex(team1.getStrikerPlayer());
        batsmen.setGotOutTo(bowler);
        batsmen.setPlayerState(Player.State.OUT);

        team1.handleWicket();

    }

    void playBall(Team team1, Team team2)
    {
        Player bowler = team2.getPlayerByIndex(team2.getCurrentBowler());
        Player batsmen = team1.getPlayerByIndex(team1.getStrikerPlayer());
        int result = ThreadLocalRandom.current().nextInt(0,8);
         switch(result){
             case 0:
                 System.out.println(bowler.getName()+" to "+batsmen.getName()+" "+result);
                 team1.incrementTeamScore(0);
                 break;
             case 1:
                 System.out.println(bowler.getName()+" to "+batsmen.getName()+" "+result);
                 team1.incrementTeamScore(1);
                 team1.changeStrike();
                 break;
             case 2:
                 System.out.println(bowler.getName()+" to "+batsmen.getName()+" "+result);
                 team1.incrementTeamScore(2);
                 break;
             case 3:
                 System.out.println(bowler.getName()+" to "+batsmen.getName()+" "+result);
                 team1.incrementTeamScore(3);
                 team1.changeStrike();
                 break;
             case 4:
                 System.out.println(bowler.getName()+" to "+batsmen.getName()+" "+result);
                 team1.incrementTeamScore(4);
                 break;
             case 5:
                 System.out.println(bowler.getName()+" to "+batsmen.getName()+" "+result);
                 team1.incrementTeamScore(5);
                 team1.changeStrike();
                 break;
             case 6:
                 System.out.println(bowler.getName()+" to "+batsmen.getName()+" "+result);
                 team1.incrementTeamScore(6);
                 break;
             case 7:
                 System.out.println(bowler.getName()+" to "+batsmen.getName()+" Out");
                 handleWicket(team1,team2);
                 break;

         }

    }

    void simulateFirstInning(Team team1, Team team2)
    {
        int overs = totalAvailableBalls/6;
        team1.setStrikerPlayer(0);
        team1.setNonStrikerPlayer(1);
        team1.setNextPlayer(2);
        int currentBowler=5;
        for(int over=0;over<overs;over++)
        {
            team2.setCurrentBowler(currentBowler);
            System.out.println("Over Number: "+(over+1));
            System.out.println("-------------------------");

            for(int ball=1;ball<=6;ball++)
            {
                if(hasLastWicketFallen(team1))
                    return;

                team1.incrementTotalPlayedBalls();
                team2.manageBowlersBalls();

                playBall(team1,team2);

            }
            System.out.println("-------------------------------");
            team1.changeStrike();
            if(currentBowler==10)currentBowler=4;
            currentBowler++;
        }
    }

    void simulateSecondInning(Team team1, Team team2, int scoreToChase)
    {
        int overs = totalAvailableBalls/6;
        team2.setStrikerPlayer(0);
        team2.setNonStrikerPlayer(1);
        team2.setNextPlayer(2);
        int currentBowler=5;
        for(int over=0;over<overs;over++)
        {
            team1.setCurrentBowler(currentBowler);
            System.out.println("Over Number: "+(over+1));
            System.out.println("-------------------------");
            for(int ball=1;ball<=6;ball++)
            {
                if(hasLastWicketFallen(team2) || scoreToChase < team2.getTeamScore())
                    return;

                team2.incrementTotalPlayedBalls();
                team1.manageBowlersBalls();

                playBall(team2,team1);

            }
            System.out.println("-------------------------------");
            team2.changeStrike();
            if(currentBowler==10)currentBowler=4;
            currentBowler++;
        }
    }

    void declareWinner(Team team1, Team team2)
    {
        if(team1.getTeamScore() > team2.getTeamScore())
        {
            winner = Winner.TEAM1;
        }
        else if(team1.getTeamScore() < team2.getTeamScore())
        {
            winner = Winner.TEAM2;
        }
        else if(team1.getTeamScore() == team2.getTeamScore())
        {
            winner = Winner.TIE;
        }
        else{
            winner = Winner.DRAW;
        }
    }

    void startMatch()
    {
        int tossWinner = simulateToss();
        int BattingFirst = tossDecision(tossWinner);
        int firstInningScore;
        if(BattingFirst==1)
        {
            System.out.println(team1.getTeamName()+" will bat first ");
            System.out.println("-----------------------------------------------------");
            simulateFirstInning(team1,team2);
            firstInningScore = team1.getTeamScore();
            System.out.println("\n\n\n ----------------------------------------------------");
            System.out.println(team2.getTeamName()+" need "+(firstInningScore+1) +" runs to win ");
            System.out.println("\n\n ------------------------------------------------------");
            simulateSecondInning(team1,team2,firstInningScore);
        }
        else{
            System.out.println(team2.getTeamName()+" will bat first ");
            System.out.println("-----------------------------------------------------");
            simulateFirstInning(team2,team1);
            firstInningScore = team2.getTeamScore();
            System.out.println("\n\n\n ----------------------------------------------------");
            System.out.println(team1.getTeamName()+" need "+(firstInningScore+1) +" runs to win ");
            System.out.println("\n\n ------------------------------------------------------");
            simulateSecondInning(team2,team1,firstInningScore);
        }

        declareWinner(team1,team2);
        System.out.println("\n\n ------------------------------------------------------");
        if(winner == Winner.DRAW || winner == Winner.TIE)
            System.out.println(" ||    match result: "+winner+"     ||");
        else
            System.out.println("||   "+winner +" won the Match      ||");
        System.out.println("\n\n ------------------------------------------------------");
        System.out.println("Final Scores are: ");
        System.out.println("team1 : " + team1.getTeamScore() +"/"+(team1.getNextPlayer()-2) +" in "+(team1.getTotalPlayedBalls()/6) +"."+(team1.getTotalPlayedBalls()%6) +" overs");
        System.out.println("team2 : " + team2.getTeamScore() +"/"+(team2.getNextPlayer()-2) +" in "+(team2.getTotalPlayedBalls()/6)+"."+(team2.getTotalPlayedBalls()%6) +" overs");

    }

    public void printTeams()
    {
        System.out.println("team 1:--------------------");
        team1.printPlayers();
        System.out.println("team 2:--------------------");
        team2.printPlayers();
    }

    public void printScoreCard()
    {
        System.out.println("----------SCORECARD------------------------------");

        System.out.println("team1 : " + team1.getTeamScore() +"/"+(team1.getNextPlayer()-2) +" in "+(team1.getTotalPlayedBalls()/6) +"."+(team1.getTotalPlayedBalls()%6) +" overs");
        System.out.println("PlayerName  State     Runs   4s    6s");
        System.out.println("----------------------------------------");
        for(int i=0;i<11;i++)
        {
            Player player = team1.getPlayerByIndex(i);
            System.out.println("  "+player.getName() +"       " +player.getPlayerState()+ " " +player.getRunScored()+"      "+player.getFourCount()+"      "+player.getSixCount()+"  "+(player.getPlayerState()== Player.State.OUT?"got out by "+player.getGotOutTo().getName():""));
        }

        System.out.println("team2 : " + team2.getTeamScore() +"/"+(team2.getNextPlayer()-2) +" in "+(team2.getTotalPlayedBalls()/6)+"."+(team2.getTotalPlayedBalls()%6) +" overs");
        System.out.println("PlayerName  State     Runs    4s   6s");
        System.out.println("---------------------------------------");
        for(int i=0;i<11;i++)
        {
            Player player = team2.getPlayerByIndex(i);
            System.out.println("  "+player.getName() +"      " +player.getPlayerState()+ " " +player.getRunScored()+"     "+player.getFourCount()+"     "+player.getSixCount()+"  "+(player.getPlayerState()== Player.State.OUT?"got out by "+player.getGotOutTo().getName():""));
        }
    }



}
