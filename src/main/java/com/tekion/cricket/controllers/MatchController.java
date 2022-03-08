package com.tekion.cricket.controllers;

import com.tekion.cricket.dataTypes.*;
import com.tekion.cricket.enums.MatchType;
import com.tekion.cricket.repository.MatchRepository;
import com.tekion.cricket.repository.PlayerRepository;
import com.tekion.cricket.repository.SeriesRepository;
import com.tekion.cricket.utils.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(path="/matches")
public class MatchController {

    @PostMapping("{matchType}")
    public ResponseEntity<Map<String,Object>> startMatch(@PathVariable("matchType") MatchType matchType, @RequestBody MatchRequest data, @RequestParam(name="number_of_matches",required = false, defaultValue = "1") String totalGames)
    {
        int overs = data.getNumberOfOvers();
        String firstTeamName = data.getFirstTeamName();
        String secondTeamName = data.getSecondTeamName();
        MatchService matchService = new MatchService();
        Map<String,Object>res = new LinkedHashMap<String, Object>();
        if(matchType==MatchType.SINGLE)
        res.put("match_id",matchService.initialiseGame("single",overs,1,firstTeamName,secondTeamName));
        else if(matchType==MatchType.SERIES) res.put("series_id",matchService.initialiseGame("series",overs,Integer.parseInt(totalGames),firstTeamName,secondTeamName));
        return new ResponseEntity<Map<String,Object>>(res, HttpStatus.CREATED);
    }


    @GetMapping("single/{matchId}")
    public MatchDb getMatch(@PathVariable(name="matchId") int matchId) throws SQLException, ClassNotFoundException {
        return MatchRepository.getMatchById(matchId);
    }


    @GetMapping("series/{seriesId}")
    public SeriesDb getSeries(@PathVariable(name="seriesId") int seriesId) throws SQLException, ClassNotFoundException {
        return SeriesRepository.getSeriesById(seriesId);
    }

    @GetMapping("{matchId}/players/{playerId}")
    public PlayerDb getPlayerData(@PathVariable int matchId, @PathVariable int playerId) throws SQLException, ClassNotFoundException {
        return PlayerRepository.getPlayerStats(matchId,playerId);
    }

}
