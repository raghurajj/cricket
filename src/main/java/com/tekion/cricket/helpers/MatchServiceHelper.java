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
    to play a single match
     */
    public static Match playSingleMatch(Team firstTeam, Team secondTeam, int totalAvailableBalls)
    {
        Match match = new Match(firstTeam, secondTeam,totalAvailableBalls);
        match.startMatch();
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
