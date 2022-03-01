package com.tekion.cricket.dbmodels;

import java.util.ArrayList;
import java.util.List;

public class MatchDb {
    int id;
    int firstTeamId;
    int secondTeamId;
    int winner;
    int tossWinner;
    int battingFirst;
    float overs;
    int seriesId;
    MatchData firstTeamMatchData;
    MatchData secondTeamMatchData;
    List<PlayerData> playerData;

    public MatchDb()
    {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFirstTeamId() {
        return firstTeamId;
    }

    public void setFirstTeamId(int firstTeamId) {
        this.firstTeamId = firstTeamId;
    }

    public int getSecondTeamId() {
        return secondTeamId;
    }

    public void setSecondTeamId(int secondTeamId) {
        this.secondTeamId = secondTeamId;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public int getTossWinner() {
        return tossWinner;
    }

    public void setTossWinner(int tossWinner) {
        this.tossWinner = tossWinner;
    }

    public int getBattingFirst() {
        return battingFirst;
    }

    public void setBattingFirst(int battingFirst) {
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

    public List<PlayerData> getPlayerData() {
        return playerData;
    }

    public void setPlayerData(List<PlayerData> playerData) {
        this.playerData = playerData;
    }
}
