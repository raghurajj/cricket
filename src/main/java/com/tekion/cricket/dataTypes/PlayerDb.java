package com.tekion.cricket.dataTypes;

import java.util.List;

public class PlayerDb {
    PlayerData player_data;
    List<WicketData> wickets_list;

    public PlayerData getPlayer_data() {
        return player_data;
    }

    public void setPlayer_data(PlayerData player_data) {
        this.player_data = player_data;
    }

    public List<WicketData> getWickets_list() {
        return wickets_list;
    }

    public void setWickets_list(List<WicketData> wickets_list) {
        this.wickets_list = wickets_list;
    }
}
