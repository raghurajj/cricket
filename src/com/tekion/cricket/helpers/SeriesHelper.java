package com.tekion.cricket.helpers;

import com.tekion.cricket.constants.StringUtils;
import com.tekion.cricket.dbconnector.MySqlConnector;
import com.tekion.cricket.models.Match;
import com.tekion.cricket.models.Series;
import com.tekion.cricket.models.Team;

import java.sql.SQLException;
import java.util.List;

public class SeriesHelper {
    public static void playSeries(Team firstTeam, Team secondTeam, int numberOfGames, int totalAvailableBalls, List<Match> matches, Series series) {
        int matchesWonByFirstTeam = 0, matchesWonBySecondTeam = 0, countTies = 0, countDraws = 0;
        Match match;
        for (int game = 1; game <= numberOfGames; game++) {
            System.out.println("\n\n----------- Match number: " + game + "-------------\n");
            match = MatchServiceHelper.playSingleMatch(firstTeam, secondTeam, totalAvailableBalls);
            match.setSeriesId(series.getId());
            if (match.getWinner().equals(firstTeam.getTeamName())) matchesWonByFirstTeam++;
            else if (match.getWinner().equals(secondTeam.getTeamName())) matchesWonBySecondTeam++;
            else if (match.getWinner().equals("TIE")) countTies++;
            else countDraws++;
            matches.add(match);
            MatchHelper.insertMatchIntoDb(match);
            firstTeam.reset();
            secondTeam.reset();

        }
        if(matchesWonByFirstTeam>matchesWonBySecondTeam)series.setWinner(firstTeam.getTeamName());
        else if(matchesWonByFirstTeam<matchesWonBySecondTeam)series.setWinner(secondTeam.getTeamName());
        else series.setWinner("TIE");
        System.out.println("\n\n ||   Series Result: "+firstTeam.getTeamName()+" "+matchesWonByFirstTeam+" - "+matchesWonBySecondTeam+" "+secondTeam.getTeamName()+"  || \n\n\n");
    }

    public static void printSeriesScorecard(List<Match>matches)
    {
        for(int game=0;game<matches.size();game++)
        {
            System.out.println("--------- Match number "+(game+1)+"-----------");
            Match match = matches.get(game);
            match.printScoreCard();
        }
    }

    public static void insertSeriesIntoDb(Series series)
    {
        try {
            MySqlConnector.insertSeries(series);
            System.out.println("Series inserted into db!!");
        } catch(SQLException sqle){
            System.out.println(sqle);
        } catch(Exception e){
            System.out.println("DB Error");
        }
    }
}
