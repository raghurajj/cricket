package com.tekion.cricket.controllers;

import com.tekion.cricket.models.*;
import com.tekion.cricket.enums.MatchType;
import com.tekion.cricket.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path="/matches")
@CacheConfig(cacheNames = {"matches"})
public class MatchController {

    @Autowired
    private MatchService matchService;

    @PostMapping("/{match_type}")
    public ResponseEntity<Map<String,Object>> startMatch(@PathVariable("match_type") MatchType matchType, @RequestBody MatchRequest data, @RequestParam(name="number_of_matches",required = false, defaultValue = "1") int totalGames)
    {
        Map<String,Object>response = matchService.startMatch(new HashMap<>(), data, matchType,totalGames);
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }


    @GetMapping("/single/{match_id}")
    @Cacheable()
    public MatchDb getMatch(@PathVariable(name="match_id") int matchId) throws SQLException, ClassNotFoundException {
        return matchService.getMatch(matchId);
    }


    @GetMapping("/series/{series_id}")
    @Cacheable()
    public SeriesDb getSeries(@PathVariable(name="series_id") int seriesId) throws SQLException, ClassNotFoundException {
        return matchService.getSeries(seriesId);
    }

    @GetMapping("/{match_id}/players/{player_id}")
    @Cacheable()
    public PlayerDb getPlayerData(@PathVariable(name="match_id")  int matchId, @PathVariable(name="player_id") int playerId) throws SQLException, ClassNotFoundException {
        return matchService.getPlayerData(matchId,playerId);
    }

}
