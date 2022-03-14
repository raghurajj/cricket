package com.tekion.cricket.controllers;

import com.tekion.cricket.models.PlayerBasicInfo;
import com.tekion.cricket.repository.PlayerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping(path="/players")
public class PlayerController {
    @GetMapping("/{player_id}")
    public PlayerBasicInfo getPlayerInfo(@PathVariable int player_id) throws SQLException, ClassNotFoundException {
        return PlayerRepository.getPlayerInfoById(player_id);
    }
}
