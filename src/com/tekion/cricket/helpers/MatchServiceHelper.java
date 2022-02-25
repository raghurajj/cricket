package com.tekion.cricket.helpers;

import com.tekion.cricket.models.Match;
import com.tekion.cricket.models.Series;
import com.tekion.cricket.models.Team;
import com.tekion.cricket.constants.StringUtils;

import java.util.Scanner;

public class MatchServiceHelper {
    public static Team getTeam(String teamNumber, String[] teamPlayers, int totalAvailableBalls)
    {
        Scanner sc =  new Scanner(System.in);
        String teamName;
        System.out.println("Enter Name of the "+ teamNumber+" Team");
        teamName = sc.nextLine();
        return new Team(teamName, totalAvailableBalls, teamPlayers);
    }

    public static Match playSingleMatch(Team firstTeam, Team secondTeam, int totalAvailableBalls)
    {
        Match match = new Match(firstTeam, secondTeam,totalAvailableBalls);
        match.startMatch();
        match.printScoreCard();

        return match;
    }

    public static Series playSeries(Team firstTeam, Team secondTeam, int totalAvailableBalls)
    {
        Scanner sc =  new Scanner(System.in);
        int totalGames;
        System.out.println(StringUtils.MATCHES_TO_BE_PLAYED);
        totalGames = sc.nextInt();
        Series series = new Series(firstTeam,secondTeam,totalGames,totalAvailableBalls);
        series.playSeries();
        return series;
    }
}
