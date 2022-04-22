package com.tekion.cricket.services;

import com.tekion.cricket.models.PlayerBasicInfo;
import com.tekion.cricket.repository.PlayerRepository;
import com.tekion.cricket.services.interfaces.IPlayerService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class PlayerService implements IPlayerService {

    @Override
    public PlayerBasicInfo getPlayerInfo(int player_id) throws SQLException, ClassNotFoundException {
        return PlayerRepository.getPlayerInfoById(player_id);
    }

}
