package com.tekion.cricket.dataTypes;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class SeriesDb {
    int id;
    @JsonProperty("first_team_name")
    String firstTeamName;
    @JsonProperty("second_team_name")
    String secondTeamName;
    @JsonProperty("total_matches")
    int totalMatches;
    int overs;
    List<Map<String,Object>> matches;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstTeamName() {
        return firstTeamName;
    }

    public void setFirstTeamName(String firstTeamName) {
        this.firstTeamName = firstTeamName;
    }

    public String getSecondTeamName() {
        return secondTeamName;
    }

    public void setSecondTeamName(String secondTeamName) {
        this.secondTeamName = secondTeamName;
    }

    public int getTotalMatches() {
        return totalMatches;
    }

    public void setTotalMatches(int totalMatches) {
        this.totalMatches = totalMatches;
    }

    public int getOvers() {
        return overs;
    }

    public void setOvers(int overs) {
        this.overs = overs;
    }

    public List<Map<String,Object>> getMatches() {
        return matches;
    }

    public void setMatches(List<Map<String,Object>> matches) {
        this.matches = matches;
    }
}
