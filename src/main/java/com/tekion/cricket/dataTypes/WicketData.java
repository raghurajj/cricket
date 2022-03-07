package com.tekion.cricket.dataTypes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WicketData {
    @JsonProperty("helper_id")
    int helperId;
    @JsonProperty("bowler_id")
    int bowlerId;
    @JsonProperty("batsman_id")
    int batsmanId;
    @JsonProperty("wicket_type")
    String wicketType;

    public int getHelperId() {
        return helperId;
    }

    public void setHelperId(int helperId) {
        this.helperId = helperId;
    }

    public int getBowlerId() {
        return bowlerId;
    }

    public void setBowlerId(int bowlerId) {
        this.bowlerId = bowlerId;
    }

    public int getBatsmanId() {
        return batsmanId;
    }

    public void setBatsmanId(int batsmanId) {
        this.batsmanId = batsmanId;
    }

    public String getWicketType() {
        return wicketType;
    }

    public void setWicketType(String wicketType) {
        this.wicketType = wicketType;
    }
}
