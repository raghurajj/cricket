package com.tekion.cricket.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MatchRequest {
    @JsonProperty("number_of_overs")
    int numberOfOvers;

    @JsonProperty("first_team_name")
    String firstTeamName;

    @JsonProperty("second_team_name")
    String secondTeamName;

    public int getNumberOfOvers() {
        return numberOfOvers;
    }

    public String getFirstTeamName() {
        return firstTeamName;
    }

    public String getSecondTeamName() {
        return secondTeamName;
    }
}
