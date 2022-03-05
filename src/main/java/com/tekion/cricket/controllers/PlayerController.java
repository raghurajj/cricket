package com.tekion.cricket.controllers;

import com.tekion.cricket.dataTypes.PlayerInfo;
import com.tekion.cricket.repository.PlayerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping(path="/players")
public class PlayerController {
    @GetMapping("{id}")
    public PlayerInfo getPlayerInfo(@PathVariable int id) throws SQLException, ClassNotFoundException {
        return PlayerRepository.getPlayerInfoById(id);
    }
}
