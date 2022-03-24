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

    public void setTeams(String firstTeamName, String secondTeamName)
    {
        firstTeam = new Team(firstTeamName, totalAvailableBalls,StringUtils.FIRST_TEAM_PLAYERS);
        secondTeam = new Team(secondTeamName,totalAvailableBalls, StringUtils.SECOND_TEAM_PLAYERS);

        try {
            TeamRepository.insertTeam(firstTeam);
            TeamRepository.insertTeam(secondTeam);
            System.out.println("Teams inserted into db!!");
        } catch(SQLException sqle){
            System.out.println(sqle);
        } catch(Exception e){
            System.out.println("DB Error");
        }

    }

    public int getTotalAvailableBalls() {
        return totalAvailableBalls;
    }


    public void setTotalAvailableBalls(int totalAvailableBalls) {
        this.totalAvailableBalls = totalAvailableBalls;
    }

    public int initialiseGame(String matchType, int overs, int totalGames,String firstTeamName, String secondTeamName)
    {
        Facts facts = new Facts();
        facts.put("matchType", matchType);
        this.setTeams(firstTeamName,secondTeamName);
        this.totalAvailableBalls = overs*6;
        Rule singleMatchRule = new RuleBuilder()
                .name("single match rule")
                .description("checks if matchType==single and then calls playSingleMatch function")
                .when( tmp -> facts.get("matchType").equals("single"))
                .then(tmp -> this.playSingleMatch(facts))
                .build();

        Rule seriesRule = new RuleBuilder()
                .name("series match rule")
                .description("checks if matchType==series and then calls playSeries function")
                .when(tmp -> facts.get("matchType").equals("series"))
                .then(tmp-> this.playSeries(totalGames,facts))
                .build();

        Rules rules = new Rules();
        rules.register(singleMatchRule);
        rules.register(seriesRule);

        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);

        return facts.get("gameId");
    }

    public void playSingleMatch(Facts facts)
    {
        Match match = MatchUtilHelper.playSingleMatch(firstTeam, secondTeam,totalAvailableBalls);
        MatchHelper.insertMatchIntoDb(match);
        facts.put("gameId",match.getId());
    }

    public void playSeries(int totalGames, Facts facts)
    {
        facts.put("gameId",(MatchUtilHelper.playSeries(firstTeam,secondTeam,totalGames,totalAvailableBalls)).getId());
    }
}
