package com.tekion.cricket.utils;

import com.tekion.cricket.constants.StringUtils;
import com.tekion.cricket.helpers.MatchHelper;
import com.tekion.cricket.beans.Match;
import com.tekion.cricket.beans.Team;
import com.tekion.cricket.helpers.MatchUtilHelper;
import com.tekion.cricket.repository.TeamRepository;
import org.jeasy.rules.api.*;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RuleBuilder;

import java.sql.SQLException;

public class MatchUtil {
    private Team firstTeam;
    private Team secondTeam;
    private  int totalAvailableBalls;

    public MatchUtil()
    {

    }


    public int getTotalAvailableBalls() {
        return totalAvailableBalls;
    }


    public void setTotalAvailableBalls(int totalAvailableBalls) {
        this.totalAvailableBalls = totalAvailableBalls;
    }




}
