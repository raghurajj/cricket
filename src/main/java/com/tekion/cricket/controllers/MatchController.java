package com.tekion.cricket.controllers;

import com.tekion.cricket.models.*;
import com.tekion.cricket.enums.MatchType;
import com.tekion.cricket.repository.MatchRepository;
import com.tekion.cricket.repository.PlayerRepository;
import com.tekion.cricket.repository.SeriesRepository;
import com.tekion.cricket.utils.MatchUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(path="/matches")
@CacheConfig(cacheNames = {"cricket"})
public class MatchController {

    @PostMapping("{match_type}")
    public ResponseEntity<Map<String,Object>> startMatch(@PathVariable("match_type") MatchType matchType, @RequestBody MatchRequest data, @RequestParam(name="number_of_matches",required = false, defaultValue = "1") String totalGames)
    {
        int overs = data.getNumberOfOvers();
        String firstTeamName = data.getFirstTeamName();
        String secondTeamName = data.getSecondTeamName();
        MatchUtil matchService = new MatchUtil();
        Map<String,Object>res = new LinkedHashMap<String, Object>();
        if(matchType==MatchType.SINGLE)
        res.put("match_id",matchService.initialiseGame("single",overs,1,firstTeamName,secondTeamName));
        else if(matchType==MatchType.SERIES) res.put("series_id",matchService.initialiseGame("series",overs,Integer.parseInt(totalGames),firstTeamName,secondTeamName));
        return new ResponseEntity<Map<String,Object>>(res, HttpStatus.CREATED);
    }


    @GetMapping("single/{match_id}")
    @Cacheable()
    public MatchDb getMatch(@PathVariable(name="match_id") int matchId) throws SQLException, ClassNotFoundException {
        return MatchRepository.getMatchById(matchId);
    }


    @GetMapping("series/{series_id}")
    @Cacheable()
    public SeriesDb getSeries(@PathVariable(name="series_id") int seriesId) throws SQLException, ClassNotFoundException {
        return SeriesRepository.getSeriesById(seriesId);
    }

    @GetMapping("{match_id}/players/{player_id}")
    @Cacheable()
    public PlayerDb getPlayerData(@PathVariable(name="match_id")  int matchId, @PathVariable(name="player_id") int playerId) throws SQLException, ClassNotFoundException {
        return PlayerRepository.getPlayerStats(matchId,playerId);
    }

}
