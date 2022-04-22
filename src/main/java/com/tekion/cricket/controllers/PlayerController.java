package com.tekion.cricket.controllers;

import com.tekion.cricket.models.PlayerBasicInfo;
import com.tekion.cricket.repository.PlayerRepository;
import com.tekion.cricket.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping(path="/players")
@CacheConfig(cacheNames = {"players"})
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/{player_id}")
    @Cacheable()
    public PlayerBasicInfo getPlayerInfo(@PathVariable int player_id) throws SQLException, ClassNotFoundException {
        return playerService.getPlayerInfo(player_id);
    }
}
