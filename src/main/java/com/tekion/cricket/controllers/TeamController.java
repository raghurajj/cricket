package com.tekion.cricket.controllers;

import com.tekion.cricket.models.TeamDb;
import com.tekion.cricket.repository.TeamRepository;
import com.tekion.cricket.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping(path="/teams")
@CacheConfig(cacheNames = {"teams"})
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/{team_id}")
    @Cacheable()
    public TeamDb getTeamInfo(@PathVariable int team_id) throws SQLException, ClassNotFoundException {
        return teamService.getTeamInfo(team_id);
    }
}
