package com.tekion.cricket.rules;

import com.tekion.cricket.constants.StringUtils;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RuleBuilder;

import static com.tekion.cricket.helpers.TeamHelper.getRandom;

public class BatsmanRunsRule {

    public static Rule zeroRunRule()
    {
        return new RuleBuilder()
                .name(StringUtils.BT0RULE)
                .description(StringUtils.BT0DESC)
                .when( facts -> (int)facts.get("random")>=1 && (int)facts.get("random")<=20)
                .then(facts -> facts.put("result",0))
                .build();
    }

    public static Rule oneRunRule()
    {
        return new RuleBuilder()
                .name(StringUtils.BT1RULE)
                .description(StringUtils.BT1DESC)
                .when( facts -> (int)facts.get("random")>20 && (int)facts.get("random")<=45)
                .then(facts -> facts.put("result", 1))
                .build();
    }

    public static Rule twoRunRule()
    {
        return new RuleBuilder()
                .name(StringUtils.BT2RULE)
                .description(StringUtils.BT2DESC)
                .when( facts -> (int)facts.get("random")>45 && (int)facts.get("random")<=69)
                .then(facts -> facts.put("result",2))
                .build();
    }

    public static Rule threeRunRule()
    {
        return new RuleBuilder()
                .name(StringUtils.BT3RULE)
                .description(StringUtils.BT3DESC)
                .when( facts -> (int)facts.get("random")>69 && (int)facts.get("random")<=79)
                .then(facts -> facts.put("result",3))
                .build();
    }


    public static Rule fourRunRule()
    {
        return new RuleBuilder()
                .name(StringUtils.BT4RULE)
                .description(StringUtils.BT4DESC)
                .when( facts -> (int)facts.get("random")>79 && (int)facts.get("random")<=89)
                .then(facts -> facts.put("result",4))
                .build();
    }


    public static Rule fiveRunRule()
    {
        return new RuleBuilder()
                .name(StringUtils.BT5RULE)
                .description(StringUtils.BT5DESC)
                .when( facts -> (int)facts.get("random")==90)
                .then(facts -> facts.put("result",5))
                .build();
    }

    public static Rule sixRunRule()
    {
        return new RuleBuilder()
                .name(StringUtils.BT6RULE)
                .description(StringUtils.BT6DESC)
                .when( facts -> (int)facts.get("random")>90 && (int)facts.get("random")<=95)
                .then(facts -> facts.put("result",6))
                .build();
    }

    public static Rule wicketRule()
    {
        return new RuleBuilder()
                .name(StringUtils.BTWRULE)
                .description(StringUtils.BTWDESC)
                .when( facts -> (int)facts.get("random")>95)
                .then(facts -> facts.put("result",7))
                .build();
    }



    /*
    returns ball result for batsman according to
    this probability distribution:
    0-10,1-25,2-24,3-10,4-10,5-1,6-5,out-5
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
