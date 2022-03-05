package com.tekion.cricket.dataTypes;

public class SeriesRequest {
    int number_of_overs;
    int number_of_matches;
    String first_team_name;
    String second_team_name;

    public int getNumber_of_overs() {
        return number_of_overs;
    }

    public int getNumber_of_matches() {
        return number_of_matches;
    }

    public String getFirst_team_name() {
        return first_team_name;
    }

    public String getSecond_team_name() {
        return second_team_name;
    }
}
