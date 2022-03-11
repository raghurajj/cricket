package com.tekion.cricket.helpers;

import com.tekion.cricket.beans.Match;
import com.tekion.cricket.beans.Series;
import com.tekion.cricket.beans.Team;
import com.tekion.cricket.repository.SeriesRepository;

import java.sql.SQLException;
import java.util.List;


/*
Provides helper methods to Series class.
 */
public class SeriesHelper {


    /*
    play a series of matches and insert each match's
     data into db
     */
    public static void playSeries(Team firstTeam, Team secondTeam, int numberOfGames, int totalAvailableBalls, List<Match> matches, Series series) {
        int matchesWonByFirstTeam = 0, matchesWonBySecondTeam = 0, countTies = 0, countDraws = 0;
        Match match;
        for (int game = 1; game <= numberOfGames; game++) {
            System.out.println("\n\n----------- Match number: " + game + "-------------\n");
            match = MatchUtilHelper.playSingleMatch(firstTeam, secondTeam, totalAvailableBalls);
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


    /*
    insert series into database
     */
    public static void insertSeriesIntoDb(Series series)
    {
        try {
            SeriesRepository.insertSeries(series);
            System.out.println("Series inserted into db!!");
        } catch(SQLException sqle){
            System.out.println(sqle);
        } catch(Exception e){
            System.out.println("DB Error");
        }
    }
}
