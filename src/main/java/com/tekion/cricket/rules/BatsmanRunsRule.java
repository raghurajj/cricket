package com.tekion.cricket.rules;

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
                .name("0 run rule")
                .description("checks if random number is greater than equal to 1 and less than equal to 20")
                .when( facts -> (int)facts.get("random")>=1 && (int)facts.get("random")<=20)
                .then(facts -> facts.put("result",0))
                .build();
    }

    public static Rule oneRunRule()
    {
        return new RuleBuilder()
                .name("1 run rule")
                .description("checks if random number is greater than 20 and less than equal to 45")
                .when( facts -> (int)facts.get("random")>20 && (int)facts.get("random")<=45)
                .then(facts -> facts.put("result", 1))
                .build();
    }

    public static Rule twoRunRule()
    {
        return new RuleBuilder()
                .name("2 runs rule")
                .description("checks if random number is greater than 45 and less than equal to 69")
                .when( facts -> (int)facts.get("random")>45 && (int)facts.get("random")<=69)
                .then(facts -> facts.put("result",2))
                .build();
    }

    public static Rule threeRunRule()
    {
        return new RuleBuilder()
                .name("3 runs rule")
                .description("checks if random number is greater than 69 and less than equal to 79")
                .when( facts -> (int)facts.get("random")>69 && (int)facts.get("random")<=79)
                .then(facts -> facts.put("result",3))
                .build();
    }


    public static Rule fourRunRule()
    {
        return new RuleBuilder()
                .name("4 runs rule")
                .description("checks if random number is greater 79 and less than equal to 89")
                .when( facts -> (int)facts.get("random")>79 && (int)facts.get("random")<=89)
                .then(facts -> facts.put("result",4))
                .build();
    }


    public static Rule fiveRunRule()
    {
        return new RuleBuilder()
                .name("5 runs rule")
                .description("checks if random number is equal to 90")
                .when( facts -> (int)facts.get("random")==90)
                .then(facts -> facts.put("result",5))
                .build();
    }

    public static Rule sixRunRule()
    {
        return new RuleBuilder()
                .name("6 runs rule")
                .description("checks if random number is greater than 90 and less than equal to 95")
                .when( facts -> (int)facts.get("random")>90 && (int)facts.get("random")<=95)
                .then(facts -> facts.put("result",6))
                .build();
    }

    public static Rule wicketRule()
    {
        return new RuleBuilder()
                .name("Wicket rule")
                .description("checks if random number is greater than 95")
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
