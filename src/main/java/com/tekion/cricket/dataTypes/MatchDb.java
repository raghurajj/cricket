package com.tekion.cricket.dataTypes;

import java.util.List;

public class MatchDb {
    int id;
    String first_team_name;
    String second_team_name;
    String winner;
    String toss_winner;
    String batting_first;
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

    public String getFirst_team_name() {
        return first_team_name;
    }

    public void setFirst_team_name(String first_team_name) {
        this.first_team_name = first_team_name;
    }

    public String getSecond_team_name() {
        return second_team_name;
    }

    public void setSecond_team_name(String second_team_name) {
        this.second_team_name = second_team_name;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getToss_winner() {
        return toss_winner;
    }

    public void setToss_winner(String toss_winner) {
        this.toss_winner = toss_winner;
    }

    public String getBatting_first() {
        return batting_first;
    }

    public void setBatting_first(String batting_first) {
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
