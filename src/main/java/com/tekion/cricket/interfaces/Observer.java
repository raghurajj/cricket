package com.tekion.cricket.interfaces;

import com.tekion.cricket.beans.Team;

public interface Observer {
    void update(int runs, Team opposition);
}
