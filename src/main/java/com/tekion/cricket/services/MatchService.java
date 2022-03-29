package com.tekion.cricket.services;

import com.tekion.cricket.enums.MatchType;
import com.tekion.cricket.models.MatchDb;
import com.tekion.cricket.models.MatchRequest;
import com.tekion.cricket.models.PlayerDb;
import com.tekion.cricket.models.SeriesDb;
import com.tekion.cricket.repository.MatchRepository;
import com.tekion.cricket.repository.PlayerRepository;
import com.tekion.cricket.repository.SeriesRepository;
import com.tekion.cricket.rules.GameRuleEngine;
import com.tekion.cricket.services.interfaces.IMatchService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;
import java.util.Map;


@Service
public class MatchService implements IMatchService {

    @Override
    public Map<String,Object> startMatch(Map<String,Object>res, MatchRequest matchRequestObject, MatchType matchType, int totalGames)
    {
        int overs = matchRequestObject.getNumberOfOvers();
        String firstTeamName = matchRequestObject.getFirstTeamName();
        String secondTeamName = matchRequestObject.getSecondTeamName();
        GameRuleEngine gameRuleEngine = new GameRuleEngine();

        switch (matchType){
            case SINGLE:
                res.put("match_id",gameRuleEngine.initialiseGame
                        ("single",overs,1,firstTeamName,secondTeamName));
                break;
            case SERIES:
                res.put("series_id",gameRuleEngine.initialiseGame
                        ("series",overs,totalGames,firstTeamName,secondTeamName));
                break;
        }

        return res;
    }

    @Override
    public MatchDb getMatch(int matchId) throws SQLException, ClassNotFoundException {
        return MatchRepository.getMatchById(matchId);
    }

    @Override
    public SeriesDb getSeries(int seriesId) throws SQLException, ClassNotFoundException {
        return SeriesRepository.getSeriesById(seriesId);
    }

    @Override
    public PlayerDb getPlayerData(int matchId, @PathVariable(name="player_id") int playerId) throws SQLException, ClassNotFoundException {
        return PlayerRepository.getPlayerStats(matchId,playerId);
    }
}
