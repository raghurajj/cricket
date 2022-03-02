package com.tekion.cricket.controllers;

import com.tekion.cricket.dataTypes.*;
import com.tekion.cricket.repository.MatchRepository;
import com.tekion.cricket.repository.PlayerRepository;
import com.tekion.cricket.repository.SeriesRepository;
import com.tekion.cricket.repository.TeamRepository;
import com.tekion.cricket.services.MatchService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;

@Controller
@RequestMapping(path="/api/player")
public class PlayerController {

    @RequestMapping(value="{matchId}/{id}", method = RequestMethod.GET)
    public @ResponseBody
    PlayerDb getPlayerData(@PathVariable int matchId, @PathVariable int id) throws SQLException, ClassNotFoundException {
        return PlayerRepository.getPlayerStats(matchId,id);
    }

    @RequestMapping(value="{id}", method = RequestMethod.GET)
    public @ResponseBody
    PlayerInfo getPlayerInfo(@PathVariable int id) throws SQLException, ClassNotFoundException {
        return PlayerRepository.getPlayerInfoById(id);
    }
}
