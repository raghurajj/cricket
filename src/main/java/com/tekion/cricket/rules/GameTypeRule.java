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

public class GameTypeRule {


    public static Rule singleMatchRule()
    {
        return new RuleBuilder()
                .name("single match rule")
                .description("checks if matchType==single and then calls playSingleMatch function")
                .when( facts -> facts.get("matchType").equals("single"))
                .then(GameTypeRule::playSingleMatch)
                .build();
    }

    public static Rule seriesRule()
    {
        return new RuleBuilder()
                .name("series match rule")
                .description("checks if matchType==series and then calls playSeries function")
                .when(facts -> facts.get("matchType").equals("series"))
                .then(GameTypeRule::playSeries)
                .build();
    }

    public static int initialiseGame(String matchType, int overs, int totalGames,String firstTeamName, String secondTeamName)
    {
        Facts facts = new Facts();
        facts.put("matchType", matchType);
        List teams =  MatchUtilHelper.setTeams(firstTeamName,secondTeamName,overs*6);
        facts.put("firstTeam",teams.get(0));
        facts.put("secondTeam",teams.get(1));
        facts.put("totalGames", totalGames);
        facts.put("totalAvailableBalls",overs*6);
        Rules rules = new Rules();

        rules.register(singleMatchRule());
        rules.register(seriesRule());

        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);

        return facts.get("gameId");
    }

    public static void playSingleMatch(Facts facts)
    {
        Match match = MatchUtilHelper.playSingleMatch((Team)facts.get("firstTeam"),(Team)facts.get("secondTeam"),(int)facts.get("totalAvailableBalls"));
        MatchHelper.insertMatchIntoDb(match);
        facts.put("gameId",match.getId());
    }

    public static void playSeries( Facts facts)
    {
        facts.put("gameId",(MatchUtilHelper.playSeries((Team)facts.get("firstTeam"),(Team)facts.get("secondTeam"),(int)facts.get("totalGames"),(int)facts.get("totalAvailableBalls"))).getId());
    }

}
