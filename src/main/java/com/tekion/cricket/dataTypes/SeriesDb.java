package com.tekion.cricket.dataTypes;

import java.util.List;

public class SeriesDb {
    int id;
    String first_team_name;
    String second_team_name;
    int total_matches;
    int overs;
    List<Integer> match_ids;

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

    public int getTotal_matches() {
        return total_matches;
    }

    public void setTotal_matches(int total_matches) {
        this.total_matches = total_matches;
    }

    public int getOvers() {
        return overs;
    }

    public void setOvers(int overs) {
        this.overs = overs;
    }

    public List<Integer> getMatch_ids() {
        return match_ids;
    }

    public void setMatch_ids(List<Integer> match_ids) {
        this.match_ids = match_ids;
    }
}
