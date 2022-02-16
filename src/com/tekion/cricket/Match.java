package com.tekion.cricket;

import com.tekion.cricket.enums.PlayerState;
import com.tekion.cricket.enums.PlayerType;
import com.tekion.cricket.enums.Winner;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Match {
    private final Team firstTeam;
    private final Team secondTeam;
    private Winner winner;
    private final int totalAvailableBalls;
    private int tossWinner;
    private int battingFirst;

    public Match(String[] firstTeamPlayers, String[] secondTeamPlayers)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Number of Overs in the Match");
        totalAvailableBalls = sc.nextInt()*6;
        sc.nextLine();
        String teamName;
        System.out.println("Enter Name of the first Team");
        teamName = sc.nextLine();
        firstTeam = new Team(teamName, totalAvailableBalls,firstTeamPlayers);
        System.out.println("Enter Name of the Second Team");
        teamName = sc.nextLine();
        secondTeam = new Team(teamName,totalAvailableBalls,secondTeamPlayers);
        winner = Winner.STARTED;
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

    void handleWicket(Team firstTeam, Team secondTeam)
    {
        Player bowler = secondTeam.getPlayerByIndex(secondTeam.getCurrentBowler());
        bowler.incrementNumberOfWicketsTaken();
        Player batsmen = firstTeam.getPlayerByIndex(firstTeam.getStrikerPlayer());
        batsmen.setGotOutTo(bowler);
        batsmen.setPlayerState(PlayerState.OUT);

        firstTeam.handleWicket();

    }

    public static int rand(int min, int max)
    {
        if (min > max || (max - min + 1 > Integer.MAX_VALUE)) {
            throw new IllegalArgumentException("Invalid range");
        }
        return new Random().nextInt(max - min + 1) + min;
    }

//    batsmen  0-15%,1-30%, 2-24%, 3-10%,4-10%,5-1%,6-5%,7-5%
    public int getBallResultForBatsman(int random)
    {
        if(random>=1 && random <=20)
        {
            return 0;
        }
        else if(random>20 && random <=45)
        {
            return 1;
        }
        else if(random>45 && random<=69)
        {
            return 2;
        }
        else if(random>69 && random<=79)
        {
            return 3;
        }
        else if(random>79 && random<=89)
        {
            return 4;
        }
        else if(random==90)
        {
            return 5;
        }
        else if(random>90 && random<=95)
        {
            return 6;
        }
        else{
            return 7;
        }
    }


    //    bowler 0-30%, 1-23, 2-14, 3-5, 4-5%, 5-1%, 6-2%, 7-20%
    public int getBallResultForBowler(int random)
    {
        if(random>=1 && random <=30)
        {
            return 0;
        }
        else if(random>30 && random <=53)
        {
            return 1;
        }
        else if(random>53 && random<=67)
        {
            return 2;
        }
        else if(random>67 && random<=72)
        {
            return 3;
        }
        else if(random>72 && random<=77)
        {
            return 4;
        }
        else if(random==78)
        {
            return 5;
        }
        else if(random>78 && random<=80)
        {
            return 6;
        }
        else{
            return 7;
        }
    }

    int getBallResult(Player batsmen)
    {
        int runs;
        int random = rand(1,100);
        if((batsmen.getPlayerType() == PlayerType.BATSMAN) || (batsmen.getPlayerType() == PlayerType.ALLROUNDER))
        {
            runs = getBallResultForBatsman(random);
        }
        else{
            runs = getBallResultForBowler(random);
        }
        return runs;
    }

    void playBall(Team firstTeam, Team secondTeam)
    {
        Player bowler = secondTeam.getPlayerByIndex(secondTeam.getCurrentBowler());
        Player batsmen = firstTeam.getPlayerByIndex(firstTeam.getStrikerPlayer());
        int result = getBallResult(batsmen);
         switch(result){
             case 0:
                 System.out.println(bowler.getName()+" to "+batsmen.getName()+" "+result);
                 firstTeam.incrementTeamScore(0);
                 bowler.incrementRunsGiven(result);
                 break;
             case 1:
                 System.out.println(bowler.getName()+" to "+batsmen.getName()+" "+result);
                 firstTeam.incrementTeamScore(1);
                 bowler.incrementRunsGiven(result);
                 firstTeam.changeStrike();
                 break;
             case 2:
                 System.out.println(bowler.getName()+" to "+batsmen.getName()+" "+result);
                 firstTeam.incrementTeamScore(2);
                 bowler.incrementRunsGiven(result);
                 break;
             case 3:
                 System.out.println(bowler.getName()+" to "+batsmen.getName()+" "+result);
                 firstTeam.incrementTeamScore(3);
                 bowler.incrementRunsGiven(result);
                 firstTeam.changeStrike();
                 break;
             case 4:
                 System.out.println(bowler.getName()+" to "+batsmen.getName()+" "+result);
                 firstTeam.incrementTeamScore(4);
                 bowler.incrementRunsGiven(result);
                 break;
             case 5:
                 System.out.println(bowler.getName()+" to "+batsmen.getName()+" "+result);
                 firstTeam.incrementTeamScore(5);
                 bowler.incrementRunsGiven(result);
                 firstTeam.changeStrike();
                 break;
             case 6:
                 System.out.println(bowler.getName()+" to "+batsmen.getName()+" "+result);
                 firstTeam.incrementTeamScore(6);
                 bowler.incrementRunsGiven(result);
                 break;
             case 7:
                 System.out.println(bowler.getName()+" to "+batsmen.getName()+" Out");
                 handleWicket(firstTeam,secondTeam);
                 break;

         }

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

    void simulateFirstInning(Team firstTeam, Team secondTeam)
    {
        int overs = totalAvailableBalls/6;
        int maxNumberOfBalls =((int) Math.ceil(((this.totalAvailableBalls/6))/5))*6;
        firstTeam.setStrikerPlayer(0);
        firstTeam.setNonStrikerPlayer(1);
        firstTeam.setNextPlayer(2);
        int currentBowler=5;
        for(int over=0;over<overs;over++)
        {
            if(hasLastWicketFallen(firstTeam))
                return;
            secondTeam.setCurrentBowler(currentBowler);

            printCurrentMatchStatus(firstTeam, secondTeam, maxNumberOfBalls, over);

            for(int ball=1;ball<=6;ball++)
            {
                if(hasLastWicketFallen(firstTeam))
                    return;

                firstTeam.incrementTotalPlayedBalls();
                secondTeam.manageBowlersBalls();
                System.out.print(over+"."+ball+"| ");
                playBall(firstTeam,secondTeam);

            }
            System.out.println("--------------------------");
            firstTeam.changeStrike();
            if(currentBowler==10)currentBowler=4;
            currentBowler++;
        }
    }


    void simulateSecondInning(Team firstTeam, Team secondTeam, int scoreToChase)
    {
        int overs = totalAvailableBalls/6;
        int maxNumberOfBalls =((int) Math.ceil(((this.totalAvailableBalls/6))/5))*6;
        secondTeam.setStrikerPlayer(0);
        secondTeam.setNonStrikerPlayer(1);
        secondTeam.setNextPlayer(2);
        int currentBowler=5;
        for(int over=0;over<overs;over++)
        {
            if(hasLastWicketFallen(secondTeam) || scoreToChase < secondTeam.getTeamScore())
                return;
            firstTeam.setCurrentBowler(currentBowler);
            printCurrentMatchStatus(secondTeam,firstTeam, maxNumberOfBalls,over);
            for(int ball=1;ball<=6;ball++)
            {
                if(hasLastWicketFallen(secondTeam) || scoreToChase < secondTeam.getTeamScore())
                    return;

                secondTeam.incrementTotalPlayedBalls();
                firstTeam.manageBowlersBalls();
                System.out.print(over+"."+ball+"| ");
                playBall(secondTeam,firstTeam);

            }
            System.out.println("-------------------------------");
            secondTeam.changeStrike();
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

        simulateFirstInning(battingFirstTeam,battingSecondTeam);
        firstInningScore = battingFirstTeam.getTeamScore();

        System.out.println("\n\n ----------------------------------------------------");
        System.out.println(battingSecondTeam.getTeamName()+" need "+(firstInningScore+1) +" runs to win ");
        System.out.println("\n\n ----------------------------------------------------");

        simulateSecondInning(battingFirstTeam,battingSecondTeam,firstInningScore);

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

    public void printBattingStats(Team team)
    {
        System.out.println("\nteam "+team.getTeamName()+" : " + team.getTeamScore() +"/"+(team.getNextPlayer()-2) +" in "+(team.getTotalPlayedBalls()/6) +"."+(team.getTotalPlayedBalls()%6) +" overs");
        System.out.println("\n Batting Stats : ----\n");

        System.out.printf("%-20.20s  %-20.20s  %-20.20s  %-20.20s %-20.20s  %-20.20s%n", "PlayerName","State","Runs","Balls Played","4s","6s");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        for(int i=0;i<11;i++)
        {
            Player player = team.getPlayerByIndex(i);
            String gotOutTo=player.getPlayerState()== PlayerState.OUT?"got out to "+player.getGotOutTo().getName():"";
            System.out.printf("%-20.20s  %-20.20s  %-20.20s  %-20.20s %-20.20s %-20.20s  %-20.20s%n",player.getName(),player.getPlayerState(),player.getRunScored(),player.getNumberOfBallPlayed(),player.getFourCount(),player.getSixCount(),gotOutTo);
        }
    }

    public void printBowlingStats(Team team)
    {
        System.out.println("\n\n Bowling Stats : ----\n");

        int maxNumberOfBalls =((int) Math.ceil(((this.totalAvailableBalls/6))/5))*6;
        System.out.printf("%-20.20s  %-20.20s  %-20.20s  %-20.20s \n", "BowlerName","OversBowled","RunsGIven","WicketsTaken");
        System.out.println("------------------------------------------------------------------");
        for(int i=5;i<11;i++)
        {
            Player bowler = team.getPlayerByIndex(i);
            if((bowler.getPlayerType()== PlayerType.BOWLER) || (bowler.getPlayerType()== PlayerType.ALLROUNDER)) {
                if (bowler.getNumberOfBallsLeftToBowl() != maxNumberOfBalls) {
                    int totalBallsBowled = maxNumberOfBalls - bowler.getNumberOfBallsLeftToBowl();
                    System.out.printf( "%-20.20s  %-20.20s  %-20.20s  %-20.20s \n",bowler.getName(),(totalBallsBowled / 6) + "." + (totalBallsBowled % 6),bowler.getRunsGiven(),bowler.getNumberOfWicketsTaken());
                }
            }
        }

        System.out.println("------------------------------------------------------------------");
    }


    public void printTeamScoreCard(Team battingTeam, Team bowlingTeam)
    {
        printBattingStats(battingTeam);
        printBowlingStats(bowlingTeam);
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
