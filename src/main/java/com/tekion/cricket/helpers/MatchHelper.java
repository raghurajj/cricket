package com.tekion.cricket.helpers;

import com.tekion.cricket.constants.StringUtils;
import com.tekion.cricket.beans.Match;
import com.tekion.cricket.beans.Player;
import com.tekion.cricket.interfaces.Ball;
import com.tekion.cricket.beans.Team;
import com.tekion.cricket.repository.MatchRepository;
import com.tekion.cricket.utils.BallUtil;

import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;


/*
Provides helper methods to Match class.
 */
public class MatchHelper {


    /*
    return true if the team is all-out else false
    team is all-out if nextPlayer==12. means no
    other player is left to bat
     */
    public static boolean hasLastWicketFallen(Team team)
    {
        return team.getNextPlayer() == 12;
    }


    /*
    returns max number of balls a bowler can ball
     */
    public static int getMaxNumberOfBalls(int totalAvailableBalls)
    {
        return ((int) Math.ceil(((totalAvailableBalls/6))/5))*6;
    }


    /*
    returns true if the inning has ended
    Inning will end if either all the wickets
    has fallen or the target has been successfully
    chased.
     */
    public static boolean hasInningEnded(int scoreToChase, Team battingTeam)
    {
        return (hasLastWicketFallen(battingTeam) || scoreToChase < battingTeam.getTeamScore());
    }


    /*
    prints current status of the match i.e.
    current batsmen/bowler and their stats,
    both teams stats.
     */
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


    /*
    play over by using playBall() method 6 times.
    and change strike after each over.
    prints ball by ball updates too
     */
    public static void playOverHelper(Team battingTeam, Team bowlingTeam, int scoreToChase, BallUtil ballService, int over)
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

    /*
    if the match has not ended
    prints current match status
    uses playerOverHelper to play over
     */
    public static void playOver(Team battingTeam, Team bowlingTeam, BallUtil ballService, int scoreToChase, int totalAvailableBalls, int currentBowler, int over)
    {
        if(hasInningEnded(scoreToChase,battingTeam))
            return;
        bowlingTeam.setCurrentBowler(currentBowler);
        printCurrentMatchStatus(battingTeam,bowlingTeam,over,totalAvailableBalls);
        playOverHelper(battingTeam,bowlingTeam,scoreToChase,(BallUtil) ballService,over);
    }


    /*
    simulate innings of a match. scoreToChase is INT_MAX if
    the first Inning is going on.
     */
    public static void simulateInning(Team battingTeam, Team bowlingTeam, int totalAvailableBalls, int scoreToChase)
    {
        Ball ballService = new BallUtil(battingTeam, bowlingTeam);
        int overs = totalAvailableBalls/6;
        battingTeam.getReadyToPlay();
        int currentBowler=5;
        ballService.registerObserver(battingTeam);
        ballService.registerObserver(bowlingTeam);
        for(int over=0;over<overs;over++)
        {
            playOver(battingTeam,bowlingTeam,(BallUtil) ballService,scoreToChase,totalAvailableBalls,currentBowler,over);
            if(currentBowler==10)currentBowler=4;
            currentBowler++;
        }
        ballService.removeObserver(battingTeam);
        ballService.removeObserver(bowlingTeam);
    }


    /*
    declares the result of the match by
    comparing both teams score
     */
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


    /*
    prints the match scorecard
     */
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


    /*
    simulate toss by generating a random number
    0- firstTeam won the toss
    1 - second team won the toss
     */
    public static int simulateToss()
    {
        int random = ThreadLocalRandom.current().nextInt(0,2);
        return (random==0?1:2);
    }


    /*
    decide whether the toss winner will bat or bowl
     */
    public static int tossDecision(int tossWinner)
    {
        int random = ThreadLocalRandom.current().nextInt(0,2);
        return (tossWinner&random )%2==0?1:2;
    }


    /*
    simulate both the innings according to toss result
     */
    public static void simulateInnings(Match match)
    {
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
    }



    /*
    starts the match by simulating toss and innings
     */
    public static void startMatch(Match match)
    {
        int tossWinner = simulateToss();
        match.setTossWinner(tossWinner==1?match.getBattingTeam().getTeamName():match.getBowlingTeam().getTeamName());
        int battingFirst = tossDecision(tossWinner);
        match.setBattingFirst(battingFirst==1?match.getBattingTeam().getTeamName():match.getBowlingTeam().getTeamName());
        if(match.getBattingFirst().equals(match.getBowlingTeam().getTeamName()))match.switchTeams();
        match.printTossResult();
        simulateInnings(match);
        MatchHelper.declareWinner(match,match.getBattingTeam(),match.getBowlingTeam());

    }


    /*
    inserts match into database
     */
    public static void insertMatchIntoDb(Match match)
    {
        try {
            MatchRepository.insertMatch(match);
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


    /*
    inserts scorecard into database
     */
    public static void insertScorecardIntoDb(Match match, Team battingTeam, Team bowlingTeam)
    {
        battingTeam.insertIntoScorecard(match);
        bowlingTeam.insertIntoScorecard(match);
    }

}
