package com.tekion.cricket.services.interfaces;

import com.tekion.cricket.models.TeamDb;

import java.sql.SQLException;

public interface ITeamService {
    public TeamDb getTeamInfo(int team_id) throws SQLException, ClassNotFoundException;
}
