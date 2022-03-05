package com.tekion.cricket.dataTypes;

public class MatchRequest {
    int number_of_overs;
    String first_team_name;
    String second_team_name;

    public int getNumber_of_overs() {
        return number_of_overs;
    }

    public String getFirst_team_name() {
        return first_team_name;
    }

    public String getSecond_team_name() {
        return second_team_name;
    }
}
