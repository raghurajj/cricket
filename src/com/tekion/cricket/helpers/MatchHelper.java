package com.tekion.cricket.helpers;

import com.tekion.cricket.constants.StringUtils;
import com.tekion.cricket.dbconnector.MySqlConnector;
import com.tekion.cricket.models.Match;
import com.tekion.cricket.models.Player;
import com.tekion.cricket.interfaces.Ball;
import com.tekion.cricket.models.Team;
import com.tekion.cricket.services.BallService;

import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

public class MatchHelper {

    public static boolean hasLastWicketFallen(Team team)
    {
        return team.getNextPlayer() == 12;
    }

    public static int getMaxNumberOfBalls(int totalAvailableBalls)
    {
        return ((int) Math.ceil(((totalAvailableBalls/6))/5))*6;
    }

    public static boolean hasInningEnded(int scoreToChase, Team battingTeam)
    {
        return (hasLastWicketFallen(battingTeam) || scoreToChase < battingTeam.getTeamScore());
    }

    public static void printCurrentMatchStatus(Team battingTeam ,Team bowlingTeam ,int over, int totalAvailableBalls)
    {
        System.out.println("Overs: "+(over) +" || "+battingTeam.getTeamName()+ "  Score: "+battingTeam.getTeamScore()+"/"+(battingTeam.getNextPlayer()-2));

        Player striker = battingTeam.getPlayerByIndex(battingTeam.getStrikerPlayer());
        Player nonStriker = battingTeam.getPlayerByIndex(battingTeam.getNonStrikerPlayer());
        Player bowler = bowlingTeam.getPlayerByIndex(bowlingTeam.getCurrentBowler());

        System.out.println("*"+striker.getName()+" "+striker.getRunScored()+"("+striker.getNumberOfBallPlayed()+")");
        System.out.println(nonStriker.getName()+" "+ nonStriker.getRunScored()+"("+nonStriker.getNumberOfBallPlayed()+")");

        int totalBallsBowled = getMaxNumberOfBalls(totalAvailableBalls) - bowler.getNumberOfBallsLeftToBowl();

        System.out.println(bowler.getName()+" : "+(totalBallsBowled / 6) + "." + (totalBallsBowled % 6)+" - "+bowler.getRunsGiven()+" - "+bowler.getNumberOfWicketsTaken());
        System.out.println(StringUtils.SMALL_DOT_LINE);
    }

    public static void playOver(Team battingTeam, Team bowlingTeam, int scoreToChase, BallService ballService, int over)
    {
        for(int ball=1;ball<=6;ball++)
        {
            if(hasInningEnded(scoreToChase, battingTeam))
                return;

            battingTeam.incrementTotalPlayedBalls();
            bowlingTeam.manageBowlersBalls();
            System.out.print(over+"."+ball+"| ");
            ballService.playBall();

        }

        System.out.println(StringUtils.SMALL_DOT_LINE);
        battingTeam.changeStrike();
    }

    public static void simulateInning(Team battingTeam, Team bowlingTeam, int totalAvailableBalls, int scoreToChase)
    {
        Ball ballService = new BallService(battingTeam, bowlingTeam);
        int overs = totalAvailableBalls/6;
        battingTeam.getReadyToPlay();
        int currentBowler=5;
        ballService.registerObserver(battingTeam);
        ballService.registerObserver(bowlingTeam);
        for(int over=0;over<overs;over++)
        {
            if(hasInningEnded(scoreToChase,battingTeam))
                return;
            bowlingTeam.setCurrentBowler(currentBowler);
            printCurrentMatchStatus(battingTeam,bowlingTeam,over,totalAvailableBalls);
            playOver(battingTeam,bowlingTeam,scoreToChase,(BallService) ballService,over);
            if(currentBowler==10)currentBowler=4;
            currentBowler++;
        }
        ballService.removeObserver(battingTeam);
        ballService.removeObserver(bowlingTeam);
    }

    public static void declareWinner(Match match, Team battingTeam, Team bowlingTeam)
    {
        if(battingTeam.getTeamScore() > bowlingTeam.getTeamScore())
        {
            match.setWinner(battingTeam.getTeamName());
        }
        else if(battingTeam.getTeamScore() < bowlingTeam.getTeamScore())
        {
            match.setWinner(bowlingTeam.getTeamName());
        }
        else if(battingTeam.getTeamScore() == bowlingTeam.getTeamScore())
        {
            match.setWinner(StringUtils.TIE);
        }
        else{
            match.setWinner(StringUtils.DRAW);
        }

        System.out.println(StringUtils.DOT_LINE);
        if(match.getWinner().equals(StringUtils.DRAW) || match.getWinner().equals(StringUtils.TIE))
            System.out.println(" ||    match result: "+match.getWinner()+"     ||");
        else
            System.out.println("||   team "+ match.getWinner() +" won the Match      ||");
    }

    public static  void printScoreCard(Team battingTeam, Team bowlingTeam, Match match)
    {
        System.out.println("\n"+ StringUtils.SCORECARD+"\n");

        System.out.println("team "+bowlingTeam.getTeamName()+" : " + bowlingTeam.getTeamScore() +"/"+(bowlingTeam.getNextPlayer()-2) +" in "+(bowlingTeam.getTotalPlayedBalls()/6) +"."+(bowlingTeam.getTotalPlayedBalls()%6) +" overs");
        System.out.println("team "+battingTeam.getTeamName()+" : " + battingTeam.getTeamScore() +"/"+(battingTeam.getNextPlayer()-2) +" in "+(battingTeam.getTotalPlayedBalls()/6)+"."+(battingTeam.getTotalPlayedBalls()%6) +" overs");

        bowlingTeam.printBattingStats();
        battingTeam.printBowlingStats();
        battingTeam.printBattingStats();
        bowlingTeam.printBowlingStats();

    }
    public static int simulateToss()
    {
        int random = ThreadLocalRandom.current().nextInt(0,100);
        return (random%2==0?1:2);
    }

    public static int tossDecision(int tossWinner)
    {
        int random = ThreadLocalRandom.current().nextInt(0,100);
        return (tossWinner&random )%2==0?1:2;
    }

    public static void startMatch(Match match)
    {
        int tossWinner = simulateToss();
        match.setTossWinner(tossWinner==1?match.getBattingTeam().getTeamName():match.getBowlingTeam().getTeamName());
        int battingFirst = tossDecision(tossWinner);
        match.setBattingFirst(battingFirst==1?match.getBattingTeam().getTeamName():match.getBowlingTeam().getTeamName());
        if(match.getBattingFirst().equals(match.getBowlingTeam().getTeamName()))match.switchTeams();
        match.printTossResult();

        int firstInningScore;
        System.out.println(StringUtils.DOT_LINE);
        match.getBattingTeam().setBatting(true);

        MatchHelper.simulateInning(match.getBattingTeam(),match.getBowlingTeam(), match.getTotalAvailableBalls(),Integer.MAX_VALUE);
        match.getBattingTeam().setBatting(false);
        firstInningScore = match.getBattingTeam().getTeamScore();

        System.out.println("\n\n"+StringUtils.DOT_LINE);
        System.out.println(match.getBowlingTeam().getTeamName()+" need "+(firstInningScore+1) +" runs to win ");
        System.out.println("\n\n"+StringUtils.DOT_LINE);

        match.switchTeams();
        match.getBattingTeam().setBatting(true);
        MatchHelper.simulateInning(match.getBattingTeam(),match.getBowlingTeam(), match.getTotalAvailableBalls(),firstInningScore);
        match.getBattingTeam().setBatting(false);
        MatchHelper.declareWinner(match,match.getBattingTeam(),match.getBowlingTeam());

    }

    public static void insertMatchIntoDb(Match match)
    {
        try {
            MySqlConnector.insertMatch(match);
            match.insertScorecardIntoDb();
            TeamHelper.insertTeamMatchData(match,match.getBattingTeam());
            TeamHelper.insertTeamMatchData(match,match.getBowlingTeam());
            System.out.println("Match inserted into db!!");
        } catch(SQLException sqle){
            System.out.println(sqle);
        } catch(Exception e){
            System.out.println("DB Error");
        }
    }

    public static void insertScorecardIntoDb(Match match, Team battingTeam, Team bowlingTeam)
    {
        battingTeam.insertIntoBattingScorecard(match);
        battingTeam.insertIntoBowlingScorecard(match);
        bowlingTeam.insertIntoBattingScorecard(match);
        bowlingTeam.insertIntoBowlingScorecard(match);
    }

}
