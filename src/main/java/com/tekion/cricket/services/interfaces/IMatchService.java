package com.tekion.cricket.services.interfaces;

import com.tekion.cricket.enums.MatchType;
import com.tekion.cricket.models.MatchRequest;

import java.util.Map;

public interface IMatchService {
    public Map<String,Object> startMatch(Map<String,Object>res, MatchRequest matchRequestObject,
                                         MatchType matchType, String totalGames);
}
