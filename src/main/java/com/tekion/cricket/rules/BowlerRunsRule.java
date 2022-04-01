package com.tekion.cricket.rules;

import com.tekion.cricket.constants.StringUtils;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RuleBuilder;

import static com.tekion.cricket.helpers.TeamHelper.getRandom;

public class BowlerRunsRule {

    public static Rule zeroRunRule()
    {
        return new RuleBuilder()
                .name(StringUtils.BW0RULE)
                .description(StringUtils.BW0DESC)
                .when( facts -> (int)facts.get("random")>=1 && (int)facts.get("random")<=35)
                .then(facts -> facts.put("result",0))
                .build();
    }

    public static Rule oneRunRule()
    {
        return new RuleBuilder()
                .name(StringUtils.BW1RULE)
                .description(StringUtils.BW1DESC)
                .when( facts -> (int)facts.get("random")>35 && (int)facts.get("random")<=60)
                .then(facts -> facts.put("result", 1))
                .build();
    }

    public static Rule twoRunRule()
    {
        return new RuleBuilder()
                .name(StringUtils.BW2RULE)
                .description(StringUtils.BW2DESC)
                .when( facts -> (int)facts.get("random")>60 && (int)facts.get("random")<=77)
                .then(facts -> facts.put("result",2))
                .build();
    }

    public static Rule threeRunRule()
    {
        return new RuleBuilder()
                .name(StringUtils.BW3RULE)
                .description(StringUtils.BW3DESC)
                .when( facts -> (int)facts.get("random")>77 && (int)facts.get("random")<=82)
                .then(facts -> facts.put("result",3))
                .build();
    }


    public static Rule fourRunRule()
    {
        return new RuleBuilder()
                .name(StringUtils.BW4RULE)
                .description(StringUtils.BW4DESC)
                .when( facts -> (int)facts.get("random")>82 && (int)facts.get("random")<=87)
                .then(facts -> facts.put("result",4))
                .build();
    }


    public static Rule fiveRunRule()
    {
        return new RuleBuilder()
                .name(StringUtils.BW5RULE)
                .description(StringUtils.BW5DESC)
                .when( facts -> (int)facts.get("random")==88)
                .then(facts -> facts.put("result",5))
                .build();
    }

    public static Rule sixRunRule()
    {
        return new RuleBuilder()
                .name(StringUtils.BW6RULE)
                .description(StringUtils.BW6DESC)
                .when( facts -> (int)facts.get("random")>88 && (int)facts.get("random")<=90)
                .then(facts -> facts.put("result",6))
                .build();
    }

    public static Rule wicketRule()
    {
        return new RuleBuilder()
                .name(StringUtils.BWWRULE)
                .description(StringUtils.BWWDESC)
                .when( facts -> (int)facts.get("random")>90)
                .then(facts -> facts.put("result",7))
                .build();
    }



    /*
        returns ball result for bowler according to
        this probability distribution:
        0-35, 1-25, 2-17, 3-5, 4-5, 5-1, 6-2, out-10
    */
    public static int getBallResult()
    {
        int random = getRandom(1,100);
        Facts facts = new Facts();
        facts.put("random",random);
        Rules rules = new Rules();

        rules.register(zeroRunRule());
        rules.register(oneRunRule());
        rules.register(twoRunRule());
        rules.register(threeRunRule());
        rules.register(fourRunRule());
        rules.register(fiveRunRule());
        rules.register(sixRunRule());
        rules.register(wicketRule());

        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);

        return facts.get("result");
    }

}
