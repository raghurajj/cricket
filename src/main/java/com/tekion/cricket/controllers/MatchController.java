package com.tekion.cricket.controllers;

import com.tekion.cricket.dbmodels.MatchDb;
import com.tekion.cricket.dbmodels.SeriesDb;
import com.tekion.cricket.repository.MatchRepository;
import com.tekion.cricket.repository.SeriesRepository;
import com.tekion.cricket.services.MatchService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Map;

@Controller
@RequestMapping(path="/api")
public class MatchController {

    @RequestMapping(value="match", method = RequestMethod.POST)
    public @ResponseBody int match(@RequestBody Map<String, Integer> data)
    {
        int overs = (data.get("overs"));
        MatchService matchService = new MatchService();
        return matchService.initialiseGame("single",overs,1);
    }

    @RequestMapping(value="series", method = RequestMethod.POST)
    public @ResponseBody int series(@RequestBody Map<String, Integer> data)
    {
        int overs = data.get("overs");
        int totalGames = data.get("games");
        MatchService matchService = new MatchService();
        return matchService.initialiseGame("series",overs,totalGames);
    }


    @RequestMapping(value="match/{id}", method = RequestMethod.GET)
    public @ResponseBody
    MatchDb getMatch(@PathVariable(name="id") int id) throws SQLException, ClassNotFoundException {
        return MatchRepository.getMatchById(id);
    }

    @RequestMapping(value="series/{id}", method = RequestMethod.GET)
    public @ResponseBody
    SeriesDb getSeries(@PathVariable(name="id") int id) throws SQLException, ClassNotFoundException {
        System.out.println(id);
        return SeriesRepository.getSeriesById(id);
    }
}
