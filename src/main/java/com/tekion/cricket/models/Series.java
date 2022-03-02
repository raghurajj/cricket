package com.tekion.cricket.models;
import com.tekion.cricket.helpers.SeriesHelper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Team getFirstTeam() {
        return firstTeam;
    }

    public void setFirstTeam(Team firstTeam) {
        this.firstTeam = firstTeam;
    }

    public Team getSecondTeam() {
        return secondTeam;
    }

    public void setSecondTeam(Team secondTeam) {
        this.secondTeam = secondTeam;
    }

    public int getTotalAvailableBalls() {
        return totalAvailableBalls;
    }

    public void setTotalAvailableBalls(int totalAvailableBalls) {
        this.totalAvailableBalls = totalAvailableBalls;
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
