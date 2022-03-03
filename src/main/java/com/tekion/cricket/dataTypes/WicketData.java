package com.tekion.cricket.dataTypes;

public class WicketData {
    int helper_id;
    int bowler_id;
    int batsman_id;
    String wicket_type;

    public int getHelper_id() {
        return helper_id;
    }

    public void setHelper_id(int helper_id) {
        this.helper_id = helper_id;
    }

    public int getBowler_id() {
        return bowler_id;
    }

    public void setBowler_id(int bowler_id) {
        this.bowler_id = bowler_id;
    }

    public int getBatsman_id() {
        return batsman_id;
    }

    public void setBatsman_id(int batsman_id) {
        this.batsman_id = batsman_id;
    }

    public String getWicket_type() {
        return wicket_type;
    }

    public void setWicket_type(String wicket_type) {
        this.wicket_type = wicket_type;
    }
}
