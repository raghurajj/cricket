package com.tekion.cricket.services.interfaces;

import com.tekion.cricket.models.PlayerBasicInfo;

import java.sql.SQLException;

public interface IPlayerService {

    public PlayerBasicInfo getPlayerInfo(int player_id) throws SQLException, ClassNotFoundException;

}
