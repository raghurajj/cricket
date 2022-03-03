package com.tekion.cricket.dataTypes;

import java.util.List;

public class TeamDb {
    int id;
    String name;
    List<Integer> match_ids;

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

    public List<Integer> getMatch_ids() {
        return match_ids;
    }

    public void setMatch_ids(List<Integer> match_ids) {
        this.match_ids = match_ids;
    }
}
