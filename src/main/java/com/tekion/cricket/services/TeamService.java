package com.tekion.cricket.services;

import com.tekion.cricket.models.TeamDb;
import com.tekion.cricket.repository.TeamRepository;
import com.tekion.cricket.services.interfaces.ITeamService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class TeamService implements ITeamService {

    @Override
    public TeamDb getTeamInfo(int team_id) throws SQLException, ClassNotFoundException {
        return TeamRepository.getTeamById(team_id);
    }
}
