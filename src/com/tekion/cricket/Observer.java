package com.tekion.cricket;

public interface Observer {
    void update(int runs, boolean isBattingTeam, Team opposition);
}
