package com.tekion.cricket.dataTypes;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MatchDb {
    int id;

    @JsonProperty("first_team_name")
    String firstTeamName;

    @JsonProperty("second_team_name")
    String secondTeamName;
    String winner;

    @JsonProperty("toss_winner")
    String tossWinner;

    @JsonProperty("batting_first")
    String battingFirst;
    float overs;

    @JsonProperty("series_id")
    int seriesId;

    @JsonProperty("first_team_match_data")
    MatchData firstTeamMatchData;

    @JsonProperty("second_team_match_data")
    MatchData secondTeamMatchData;

    @JsonProperty("players_data")
    List<PlayerData> playersData;

    public MatchDb()
    {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstTeamName() {
        return firstTeamName;
    }

    public void setFirstTeamName(String firstTeamName) {
        this.firstTeamName = firstTeamName;
    }

    public String getSecondTeamName() {
        return secondTeamName;
    }

    public void setSecondTeamName(String secondTeamName) {
        this.secondTeamName = secondTeamName;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getTossWinner() {
        return tossWinner;
    }

    public void setTossWinner(String tossWinner) {
        this.tossWinner = tossWinner;
    }

    public String getBattingFirst() {
        return battingFirst;
    }

    public void setBattingFirst(String battingFirst) {
        this.battingFirst = battingFirst;
    }

    public float getOvers() {
        return overs;
    }

    public void setOvers(float overs) {
        this.overs = overs;
    }

    public int getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(int seriesId) {
        this.seriesId = seriesId;
    }

    public MatchData getFirstTeamMatchData() {
        return firstTeamMatchData;
    }

    public void setFirstTeamMatchData(MatchData firstTeamMatchData) {
        this.firstTeamMatchData = firstTeamMatchData;
    }

    public MatchData getSecondTeamMatchData() {
        return secondTeamMatchData;
    }

    public void setSecondTeamMatchData(MatchData secondTeamMatchData) {
        this.secondTeamMatchData = secondTeamMatchData;
    }

    public List<PlayerData> getPlayersData() {
        return playersData;
    }

    public void setPlayersData(List<PlayerData> playersData) {
        this.playersData = playersData;
    }
}
