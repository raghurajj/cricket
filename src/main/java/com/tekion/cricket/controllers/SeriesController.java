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
@RequestMapping(path="/api/series")
public class SeriesController {

    @RequestMapping(value="", method = RequestMethod.POST)
    public @ResponseBody int startSeries(@RequestBody Map<String, Integer> data)
    {
        int overs = data.get("overs");
        int totalGames = data.get("matches");
        MatchService matchService = new MatchService();
        return matchService.initialiseGame("series",overs,totalGames);
    }

    @RequestMapping(value="{id}", method = RequestMethod.GET)
    public @ResponseBody
    SeriesDb getSeries(@PathVariable(name="id") int id) throws SQLException, ClassNotFoundException {
        return SeriesRepository.getSeriesById(id);
    }
}
