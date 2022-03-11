package com.tekion.cricket.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PlayerDb {
    @JsonProperty("player_data")
    PlayerData playerData;
    @JsonProperty("wickets_list")
    List<WicketData> wicketsList;

    public PlayerData getPlayerData() {
        return playerData;
    }

    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
    }

    public List<WicketData> getWicketsList() {
        return wicketsList;
    }

    public void setWicketsList(List<WicketData> wicketsList) {
        this.wicketsList = wicketsList;
    }
}
