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
@RequestMapping(path="/api/match")
public class MatchController {

    @RequestMapping(value="", method = RequestMethod.POST)
    public @ResponseBody int startMatch(@RequestBody Map<String, Integer> data)
    {
        int overs = (data.get("overs"));
        MatchService matchService = new MatchService();
        return matchService.initialiseGame("single",overs,1);
    }


    @RequestMapping(value="{id}", method = RequestMethod.GET)
    public @ResponseBody
    MatchDb getMatch(@PathVariable(name="id") int id) throws SQLException, ClassNotFoundException {
        return MatchRepository.getMatchById(id);
    }
}
