package com.tekion.cricket.services;

import com.tekion.cricket.enums.MatchType;
import com.tekion.cricket.models.MatchRequest;
import com.tekion.cricket.rules.GameRuleEngine;
import com.tekion.cricket.services.interfaces.IMatchService;
import com.tekion.cricket.utils.MatchUtil;
import org.springframework.stereotype.Service;
import java.util.Map;


@Service
public class MatchService implements IMatchService {

    @Override
    public Map<String,Object> startMatch(Map<String,Object>res, MatchRequest matchRequestObject, MatchType matchType, String totalGames)
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
                        ("series",overs,Integer.parseInt(totalGames),firstTeamName,secondTeamName));
                break;
        }

        return res;
    }
}
