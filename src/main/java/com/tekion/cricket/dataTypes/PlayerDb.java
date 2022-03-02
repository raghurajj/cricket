package com.tekion.cricket.dataTypes;

import java.util.List;

public class PlayerDb {
    PlayerData playerData;
    List<WicketHelper> wicketHelperList;

    public PlayerData getPlayerData() {
        return playerData;
    }

    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
    }

    public List<WicketHelper> getWicketHelperList() {
        return wicketHelperList;
    }

    public void setWicketHelperList(List<WicketHelper> wicketHelperList) {
        this.wicketHelperList = wicketHelperList;
    }
}
