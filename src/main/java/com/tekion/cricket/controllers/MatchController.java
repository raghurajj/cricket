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
@RequestMapping(path="/api/match")
public class MatchController {

    @PostMapping("")
    public int startMatch(@RequestBody Map<String, Integer> data)
    {
        int overs = (data.get("overs"));
        MatchService matchService = new MatchService();
        return matchService.initialiseGame("single",overs,1);
    }


    @GetMapping("{id}")
    public MatchDb getMatch(@PathVariable(name="id") int id) throws SQLException, ClassNotFoundException {
        return MatchRepository.getMatchById(id);
    }
}
