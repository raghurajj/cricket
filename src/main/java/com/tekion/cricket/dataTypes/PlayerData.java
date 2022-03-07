package com.tekion.cricket.dataTypes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerData {
    int id;
    String name;
    String type;

    @JsonProperty("team_id")
    int teamId;
    String state;
    @JsonProperty("runs_scored")
    int runsScored;
    @JsonProperty("balls_played")
    int ballsPlayed;
    @JsonProperty("four_count")
    int fourCount;
    @JsonProperty("six_count")
    int sixCount;
    @JsonProperty("bowled_by")
    int bowledBy;
    @JsonProperty("wicket_helper")
    int wicketHelper;
    @JsonProperty("wicket_type")
    String wicketType;
    @JsonProperty("overs_bowled")
    float oversBowled;
    @JsonProperty("runs_given")
    int runsGiven;
    @JsonProperty("wickets_taken")
    int wicketsTaken;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getRunsScored() {
        return runsScored;
    }

    public void setRunsScored(int runsScored) {
        this.runsScored = runsScored;
    }

    public int getBallsPlayed() {
        return ballsPlayed;
    }

    public void setBallsPlayed(int ballsPlayed) {
        this.ballsPlayed = ballsPlayed;
    }

    public int getFourCount() {
        return fourCount;
    }

    public void setFourCount(int fourCount) {
        this.fourCount = fourCount;
    }

    public int getSixCount() {
        return sixCount;
    }

    public void setSixCount(int sixCount) {
        this.sixCount = sixCount;
    }

    public int getBowledBy() {
        return bowledBy;
    }

    public void setBowledBy(int bowledBy) {
        this.bowledBy = bowledBy;
    }

    public int getWicketHelper() {
        return wicketHelper;
    }

    public void setWicketHelper(int wicketHelper) {
        this.wicketHelper = wicketHelper;
    }

    public String getWicketType() {
        return wicketType;
    }

    public void setWicketType(String wicketType) {
        this.wicketType = wicketType;
    }

    public float getOversBowled() {
        return oversBowled;
    }

    public void setOversBowled(float oversBowled) {
        this.oversBowled = oversBowled;
    }

    public int getRunsGiven() {
        return runsGiven;
    }

    public void setRunsGiven(int runsGiven) {
        this.runsGiven = runsGiven;
    }

    public int getWicketsTaken() {
        return wicketsTaken;
    }

    public void setWicketsTaken(int wicketsTaken) {
        this.wicketsTaken = wicketsTaken;
    }
}
