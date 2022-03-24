package com.tekion.cricket.helpers;

import com.tekion.cricket.enums.MatchType;
import com.tekion.cricket.beans.Match;
import com.tekion.cricket.beans.Series;
import com.tekion.cricket.beans.Team;
import com.tekion.cricket.constants.StringUtils;
import com.tekion.cricket.utils.MatchUtil;

import java.util.Scanner;

/*
Provides helper methods to MatchUtil class.
 */
public class MatchUtilHelper {


    /*
    initialise and returns a new Team
     */
    public static Team getTeam(String teamNumber, String[] teamPlayers, int totalAvailableBalls)
    {
        Scanner sc =  new Scanner(System.in);
        String teamName;
        System.out.println("Enter Name of the "+ teamNumber+" Team");
        teamName = sc.nextLine();
        return new Team(teamName, totalAvailableBalls, teamPlayers);
    }


    /*
    to play a single match
     */
    public static Match playSingleMatch(Team firstTeam, Team secondTeam, int totalAvailableBalls)
    {
        Match match = new Match(firstTeam, secondTeam,totalAvailableBalls);
        match.startMatch();
        match.printScoreCard();

        return match;
    }


    /*
    to play a series of matches
     */
    public static Series playSeries(Team firstTeam, Team secondTeam,int totalGames, int totalAvailableBalls)
    {

        Series series = new Series.Builder(firstTeam,secondTeam)
                .numberOfGames(totalGames)
                .totalBalls(totalAvailableBalls)
                .build();
        series.playSeries();
        return series;
    }

}
