package com.tekion.cricket.dataTypes;

import java.util.List;

public class MatchDb {
    int id;
    int first_team_id;
    int second_team_id;
    int winner;
    int toss_winner;
    int batting_first;
    float overs;
    int series_id;
    MatchData first_team_match_data;
    MatchData second_team_match_data;
    List<PlayerData> players_data;

    public MatchDb()
    {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFirst_team_id() {
        return first_team_id;
    }

    public void setFirst_team_id(int first_team_id) {
        this.first_team_id = first_team_id;
    }

    public int getSecond_team_id() {
        return second_team_id;
    }

    public void setSecond_team_id(int second_team_id) {
        this.second_team_id = second_team_id;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public int getToss_winner() {
        return toss_winner;
    }

    public void setToss_winner(int toss_winner) {
        this.toss_winner = toss_winner;
    }

    public int getBatting_first() {
        return batting_first;
    }

    public void setBatting_first(int batting_first) {
        this.batting_first = batting_first;
    }

    public float getOvers() {
        return overs;
    }

    public void setOvers(float overs) {
        this.overs = overs;
    }

    public int getSeries_id() {
        return series_id;
    }

    public void setSeries_id(int series_id) {
        this.series_id = series_id;
    }

    public MatchData getFirst_team_match_data() {
        return first_team_match_data;
    }

    public void setFirst_team_match_data(MatchData first_team_match_data) {
        this.first_team_match_data = first_team_match_data;
    }

    public MatchData getSecond_team_match_data() {
        return second_team_match_data;
    }

    public void setSecond_team_match_data(MatchData second_team_match_data) {
        this.second_team_match_data = second_team_match_data;
    }

    public List<PlayerData> getPlayers_data() {
        return players_data;
    }

    public void setPlayers_data(List<PlayerData> players_data) {
        this.players_data = players_data;
    }
}
