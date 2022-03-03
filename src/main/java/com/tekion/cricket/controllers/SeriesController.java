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

@RestController
@RequestMapping(path="/series")
public class SeriesController {

    @PostMapping("")
    public int startSeries(@RequestBody Map<String, Integer> data)
    {
        int overs = data.get("overs");
        int totalGames = data.get("matches");
        MatchService matchService = new MatchService();
        return matchService.initialiseGame("series",overs,totalGames);
    }

    @GetMapping("{id}")
    public SeriesDb getSeries(@PathVariable(name="id") int id) throws SQLException, ClassNotFoundException {
        return SeriesRepository.getSeriesById(id);
    }
}
