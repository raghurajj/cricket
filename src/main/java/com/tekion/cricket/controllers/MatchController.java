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
    public ResponseEntity<Map<String,Object>> startMatch(@RequestBody Map<String, String> data)
    {
        int overs = Integer.parseInt(data.get("number_of_overs"));
        String firstTeamName = data.get("first_team_name");
        String secondTeamName = data.get("second_team_name");
        MatchService matchService = new MatchService();
        Map<String,Object>res = new LinkedHashMap<String, Object>();
        res.put("match_id",matchService.initialiseGame("single",overs,1,firstTeamName,secondTeamName));
        return new ResponseEntity<Map<String,Object>>(res, HttpStatus.CREATED);
    }

    @PostMapping("series")
    public ResponseEntity<Map<String,Object>> startSeries(@RequestBody Map<String, String> data)
    {
        int overs = Integer.parseInt(data.get("number_of_overs"));
        int totalGames = Integer.parseInt(data.get("number_of_matches"));
        String firstTeamName = data.get("first_team_name");
        String secondTeamName = data.get("second_team_name");
        MatchService matchService = new MatchService();
        Map<String,Object>res = new LinkedHashMap<String, Object>();
        res.put("series_id",matchService.initialiseGame("series",overs,totalGames,firstTeamName,secondTeamName));
        return new ResponseEntity<Map<String,Object>>(res, HttpStatus.CREATED);
    }

    @GetMapping("single/{id}")
    public MatchDb getMatch(@PathVariable(name="id") int id) throws SQLException, ClassNotFoundException {
        return MatchRepository.getMatchById(id);
    }


    @GetMapping("series/{id}")
    public SeriesDb getSeries(@PathVariable(name="id") int id) throws SQLException, ClassNotFoundException {
        return SeriesRepository.getSeriesById(id);
    }

    @GetMapping("{matchId}/players/{id}")
    public PlayerDb getPlayerData(@PathVariable int matchId, @PathVariable int id) throws SQLException, ClassNotFoundException {
        return PlayerRepository.getPlayerStats(matchId,id);
    }

}
