package com.tekion.cricket.helpers;

import com.tekion.cricket.dbconnector.MySqlConnector;
import com.tekion.cricket.enums.MatchType;
import com.tekion.cricket.models.Match;
import com.tekion.cricket.models.Series;
import com.tekion.cricket.models.Team;
import com.tekion.cricket.constants.StringUtils;
import com.tekion.cricket.repository.MatchRepository;
import com.tekion.cricket.services.MatchService;

import java.sql.SQLException;
import java.util.Scanner;

public class MatchServiceHelper {
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

    /*
    to fetch match data from database
     */
    public static void databaseOps(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Match id to fetch scorecard");
        int matchId = sc.nextInt();

        try {
            MatchRepository.fetchScorecard(matchId);
        } catch(SQLException sqle){
            System.out.println(sqle);
        } catch(Exception e){
            System.out.println("DB Error");
        }
    }

    /*
    to decide whether to play a match or series
    and get match data like number of overs
     */
    public static void getMatchData(MatchService matchService)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println(StringUtils.NUMBER_OF_OVERS);
        matchService.setTotalAvailableBalls(sc.nextInt() * 6);
        matchService.setTeams();
        System.out.println(StringUtils.MATCH_TYPE);
        int choice = sc.nextInt();
        MatchType matchType = (choice == 1 ? MatchType.SINGLE : MatchType.SERIES);
        switch (matchType) {
            case SINGLE:
                matchService.playSingleMatch();
                break;
            case SERIES:
                matchService.playSeries();
                break;
            default:
                System.out.println(StringUtils.MAIN_SWITCH_DEFAULT_MESSAGE);

        }
    }

    /*
    initialise the game
     */
    public static void initialiseGame(MatchService matchService)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter 1 to play matches , Enter 2 to fetch data from database");
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                getMatchData(matchService);
                break;
            case 2:
                databaseOps();
                break;
            default:
                System.out.println(StringUtils.MAIN_SWITCH_DEFAULT_MESSAGE);

        }
    }
}
