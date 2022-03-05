package com.tekion.cricket.controllers;

import com.tekion.cricket.dataTypes.TeamDb;
import com.tekion.cricket.repository.TeamRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping(path="/teams")
public class TeamController {

    @GetMapping("{id}")
    public TeamDb getTeamInfo(@PathVariable int id) throws SQLException, ClassNotFoundException {
        return TeamRepository.getTeamById(id);
    }
}
