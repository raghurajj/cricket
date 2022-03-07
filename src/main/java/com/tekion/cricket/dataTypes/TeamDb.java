package com.tekion.cricket.dataTypes;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TeamDb {
    int id;
    String name;
    @JsonProperty("match_ids")
    List<Integer> matchIds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getMatchIds() {
        return matchIds;
    }

    public void setMatchIds(List<Integer> matchIds) {
        this.matchIds = matchIds;
    }
}
