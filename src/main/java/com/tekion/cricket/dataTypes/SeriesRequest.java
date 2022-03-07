package com.tekion.cricket.dataTypes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SeriesRequest {

    @JsonProperty("number_of_overs")
    int numberOfOvers;

    @JsonProperty("number_of_matches")
    int numberOfMatches;

    @JsonProperty("first_team_name")
    String firstTeamName;

    @JsonProperty("second_team_name")
    String secondTeamName;

    public int getNumberOfOvers() {
        return numberOfOvers;
    }

    public int getNumberOfMatches() {
        return numberOfMatches;
    }

    public String getFirstTeamName() {
        return firstTeamName;
    }

    public String getSecondTeamName() {
        return secondTeamName;
    }
}
