package com.tekion.cricket.services.interfaces;

import com.tekion.cricket.enums.MatchType;
import com.tekion.cricket.models.MatchDb;
import com.tekion.cricket.models.MatchRequest;
import com.tekion.cricket.models.PlayerDb;
import com.tekion.cricket.models.SeriesDb;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;
import java.util.Map;

public interface IMatchService {
    public Map<String,Object> startMatch(Map<String,Object>res, MatchRequest matchRequestObject,
                                         MatchType matchType, int totalGames);

    public MatchDb getMatch(int matchId) throws SQLException, ClassNotFoundException;

    public SeriesDb getSeries(int seriesId) throws SQLException, ClassNotFoundException;

    public PlayerDb getPlayerData(int matchId, @PathVariable(name="player_id") int playerId) throws SQLException, ClassNotFoundException;
}
