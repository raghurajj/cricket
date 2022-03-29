package com.tekion.cricket.rules;

import com.tekion.cricket.beans.Match;
import com.tekion.cricket.beans.Team;
import com.tekion.cricket.helpers.MatchHelper;
import com.tekion.cricket.helpers.MatchUtilHelper;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RuleBuilder;

import java.util.List;

public class GameRuleEngine {

    public int initialiseGame(String matchType, int overs, int totalGames,String firstTeamName, String secondTeamName)
    {
        Facts facts = new Facts();
        facts.put("matchType", matchType);
        List<Team> teams =  MatchUtilHelper.setTeams(firstTeamName,secondTeamName,overs*6);
        Rule singleMatchRule = new RuleBuilder()
                .name("single match rule")
                .description("checks if matchType==single and then calls playSingleMatch function")
                .when( tmp -> facts.get("matchType").equals("single"))
                .then(tmp -> this.playSingleMatch(teams.get(0),teams.get(1),facts, overs*6))
                .build();

        Rule seriesRule = new RuleBuilder()
                .name("series match rule")
                .description("checks if matchType==series and then calls playSeries function")
                .when(tmp -> facts.get("matchType").equals("series"))
                .then(tmp-> this.playSeries(teams.get(0), teams.get(1), totalGames,facts, overs*6))
                .build();

        Rules rules = new Rules();
        rules.register(singleMatchRule);
        rules.register(seriesRule);

        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);

        return facts.get("gameId");
    }

    public void playSingleMatch( Team firstTeam, Team secondTeam, Facts facts, int totalAvailableBalls)
    {
        Match match = MatchUtilHelper.playSingleMatch(firstTeam, secondTeam,totalAvailableBalls);
        MatchHelper.insertMatchIntoDb(match);
        facts.put("gameId",match.getId());
    }

    public void playSeries( Team firstTeam, Team secondTeam, int totalGames, Facts facts, int totalAvailableBalls)
    {
        facts.put("gameId",(MatchUtilHelper.playSeries(firstTeam,secondTeam,totalGames,totalAvailableBalls)).getId());
    }

}
