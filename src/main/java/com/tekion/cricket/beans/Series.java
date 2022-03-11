package com.tekion.cricket.beans;
import com.tekion.cricket.helpers.SeriesHelper;

import java.util.ArrayList;
import java.util.List;

/*
holds all info about a series i.e.
teams,oversCount,matchCount,winner etc.
 */
public class Series {
    int id;
    private int numberOfGames;
    private List<Match> matches;
    private Team firstTeam;
    private Team secondTeam;
    private int totalAvailableBalls;
    String winner;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }


    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Team getFirstTeam() {
        return firstTeam;
    }

    public Team getSecondTeam() {
        return secondTeam;
    }

    public int getTotalAvailableBalls() {
        return totalAvailableBalls;
    }

    public Series(){}

    public Series(Team firstTeam, Team secondTeam, int numberOfGames, int totalAvailableBalls)
    {
        this.firstTeam=firstTeam;
        this.secondTeam = secondTeam;
        this.totalAvailableBalls = totalAvailableBalls;
        this.numberOfGames = numberOfGames;
        matches = new ArrayList<Match>(numberOfGames);
        SeriesHelper.insertSeriesIntoDb(this);
    }

    public void playSeries() {
        SeriesHelper.playSeries(firstTeam,secondTeam,numberOfGames,totalAvailableBalls,matches,this);
    }
}
