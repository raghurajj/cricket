package com.tekion.cricket.helpers;

import com.tekion.cricket.beans.Match;
import com.tekion.cricket.beans.Series;
import com.tekion.cricket.beans.Team;
import com.tekion.cricket.constants.StringUtils;
import com.tekion.cricket.repository.TeamRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

    public static List setTeams(String firstTeamName, String secondTeamName, int totalAvailableBalls)
    {
        Team firstTeam = new Team(firstTeamName, totalAvailableBalls,StringUtils.FIRST_TEAM_PLAYERS);
        Team secondTeam = new Team(secondTeamName,totalAvailableBalls, StringUtils.SECOND_TEAM_PLAYERS);

        try {
            TeamRepository.insertTeam(firstTeam);
            TeamRepository.insertTeam(secondTeam);
            System.out.println("Teams inserted into db!!");
        } catch(SQLException sqle){
            System.out.println(sqle);
        } catch(Exception e){
            System.out.println("DB Error");
        }

        List<Team> teams= new ArrayList<>();
        teams.add(firstTeam);
        teams.add(secondTeam);
        return teams;
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
