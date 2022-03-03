package com.tekion.cricket.dataTypes;

public class PlayerData {
    int id;
    String name;
    String type;
    int team_id;
    String state;
    int runs_scored;
    int balls_played;
    int four_count;
    int six_count;
    int bowled_by;
    int wicket_helper;
    String wicket_type;
    float overs_bowled;
    int runs_given;
    int wickets_taken;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
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

    public int getRuns_scored() {
        return runs_scored;
    }

    public void setRuns_scored(int runs_scored) {
        this.runs_scored = runs_scored;
    }

    public int getBalls_played() {
        return balls_played;
    }

    public void setBalls_played(int balls_played) {
        this.balls_played = balls_played;
    }

    public int getFour_count() {
        return four_count;
    }

    public void setFour_count(int four_count) {
        this.four_count = four_count;
    }

    public int getSix_count() {
        return six_count;
    }

    public void setSix_count(int six_count) {
        this.six_count = six_count;
    }

    public int getBowled_by() {
        return bowled_by;
    }

    public void setBowled_by(int bowled_by) {
        this.bowled_by = bowled_by;
    }

    public int getWicket_helper() {
        return wicket_helper;
    }

    public void setWicket_helper(int wicket_helper) {
        this.wicket_helper = wicket_helper;
    }

    public String getWicket_type() {
        return wicket_type;
    }

    public void setWicket_type(String wicket_type) {
        this.wicket_type = wicket_type;
    }

    public float getOvers_bowled() {
        return overs_bowled;
    }

    public void setOvers_bowled(float overs_bowled) {
        this.overs_bowled = overs_bowled;
    }

    public int getRuns_given() {
        return runs_given;
    }

    public void setRuns_given(int runs_given) {
        this.runs_given = runs_given;
    }

    public int getWickets_taken() {
        return wickets_taken;
    }

    public void setWickets_taken(int wickets_taken) {
        this.wickets_taken = wickets_taken;
    }
}
