package com.tekion.cricket.helpers;

import com.tekion.cricket.enums.MatchType;
import com.tekion.cricket.models.Match;
import com.tekion.cricket.models.Series;
import com.tekion.cricket.models.Team;
import com.tekion.cricket.constants.StringUtils;
import com.tekion.cricket.utils.MatchService;

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
    public static Series playSeries(Team firstTeam, Team secondTeam,int totalGames, int totalAvailableBalls)
    {

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

//        try {
//            MatchRepository.fetchScorecard(matchId);
//        } catch(SQLException sqle){
//            System.out.println(sqle);
//        } catch(Exception e){
//            System.out.println("DB Error");
//        }
    }



    /*
    to decide whether to play a match or series
    and get match data like number of overs
     */
    public static int getMatchData(MatchService matchService,String choice,int overs, int totalGames)
    {
        matchService.setTeams();
        MatchType matchType = (choice == "single" ? MatchType.SINGLE : MatchType.SERIES);
        switch (matchType) {
            case SINGLE:
                return matchService.playSingleMatch(overs);
            case SERIES:
                return matchService.playSeries(overs,totalGames);
            default:
                System.out.println(StringUtils.MAIN_SWITCH_DEFAULT_MESSAGE);

        }

        return -1;
    }
}
