package com.tekion.cricket.dataTypes;

import java.util.List;

public class SeriesDb {
    int id;
    int first_team_id;
    int second_team_id;
    int total_matches;
    int overs;
    List<Integer> match_ids;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFirst_team_id() {
        return first_team_id;
    }

    public void setFirst_team_id(int first_team_id) {
        this.first_team_id = first_team_id;
    }

    public int getSecond_team_id() {
        return second_team_id;
    }

    public void setSecond_team_id(int second_team_id) {
        this.second_team_id = second_team_id;
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
