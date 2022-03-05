package com.tekion.cricket.controllers;

import com.tekion.cricket.dataTypes.*;
import com.tekion.cricket.repository.MatchRepository;
import com.tekion.cricket.repository.PlayerRepository;
import com.tekion.cricket.repository.SeriesRepository;
import com.tekion.cricket.repository.TeamRepository;
import com.tekion.cricket.utils.MatchService;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(path="/matches")
public class MatchController {

    @PostMapping("single")
    public ResponseEntity<Map<String,Object>> startMatch(@RequestBody MatchRequest data)
    {
        int overs = data.getNumber_of_overs();
        String firstTeamName = data.getFirst_team_name();
        String secondTeamName = data.getSecond_team_name();
        MatchService matchService = new MatchService();
        Map<String,Object>res = new LinkedHashMap<String, Object>();
        res.put("match_id",matchService.initialiseGame("single",overs,1,firstTeamName,secondTeamName));
        return new ResponseEntity<Map<String,Object>>(res, HttpStatus.CREATED);
    }

    @PostMapping("series")
    public ResponseEntity<Map<String,Object>> startSeries(@RequestBody SeriesRequest data)
    {
        int overs = data.getNumber_of_overs();
        int totalGames = data.getNumber_of_matches();
        String firstTeamName = data.getFirst_team_name();
        String secondTeamName = data.getSecond_team_name();
        MatchService matchService = new MatchService();
        Map<String,Object>res = new LinkedHashMap<String, Object>();
        res.put("series_id",matchService.initialiseGame("series",overs,totalGames,firstTeamName,secondTeamName));
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
