package com.tekion.cricket.helpers;

import com.tekion.cricket.constants.StringUtils;
import com.tekion.cricket.dbconnector.MySqlConnector;
import com.tekion.cricket.models.Match;
import com.tekion.cricket.models.Player;
import com.tekion.cricket.interfaces.Ball;
import com.tekion.cricket.models.Team;
import com.tekion.cricket.repository.MatchRepository;
import com.tekion.cricket.services.BallService;

import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

public class MatchHelper {

    /*
    return true if the team is all-out else false
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
     */
    public static boolean hasInningEnded(int scoreToChase, Team battingTeam)
    {
        return (hasLastWicketFallen(battingTeam) || scoreToChase < battingTeam.getTeamScore());
    }

    /*
    play over by using playBall() method 6 times.
     */
    public static void playOverHelper(Team battingTeam, Team bowlingTeam, int scoreToChase, BallService ballService, int over)
    {
        for(int ball=1;ball<=6;ball++)
        {
            if(hasInningEnded(scoreToChase, battingTeam))
                return;

            battingTeam.incrementTotalPlayedBalls();
            bowlingTeam.manageBowlersBalls();
            ballService.playBall();

        }
        battingTeam.changeStrike();
    }

    public static void playOver(Team battingTeam,Team bowlingTeam, int over, int scoreToChase, int totalAvailableBalls,Ball ballService,int currentBowler)
    {
        if(hasInningEnded(scoreToChase,battingTeam))
            return;
        bowlingTeam.setCurrentBowler(currentBowler);
        playOverHelper(battingTeam,bowlingTeam,scoreToChase,(BallService) ballService,over);
    }


    /*
    simulate inning of a match. scoreToChase is INT_MAX if
    the first Inning is going on.
     */
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
            playOver(battingTeam,bowlingTeam,over,scoreToChase,totalAvailableBalls,ballService,currentBowler);
            if(currentBowler==10)currentBowler=4;
            currentBowler++;
        }
        ballService.removeObserver(battingTeam);
        ballService.removeObserver(bowlingTeam);
    }

    /*
    declares the result of the match
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


    public static void startInnings(Match match)
    {
        int firstInningScore;
        match.getBattingTeam().setBatting(true);

        MatchHelper.simulateInning(match.getBattingTeam(),match.getBowlingTeam(), match.getTotalAvailableBalls(),Integer.MAX_VALUE);
        match.getBattingTeam().setBatting(false);
        firstInningScore = match.getBattingTeam().getTeamScore();

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

        startInnings(match);
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
