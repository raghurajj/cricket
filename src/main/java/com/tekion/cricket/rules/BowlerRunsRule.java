package com.tekion.cricket.rules;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RuleBuilder;

import static com.tekion.cricket.helpers.TeamHelper.getRandom;

public class BowlerRunsRule {

    public static Rule zeroRule()
    {
        return new RuleBuilder()
                .name("0 run rule")
                .description("checks if random number is greater than equal to 1 and less than equal to 35")
                .when( facts -> (int)facts.get("random")>=1 && (int)facts.get("random")<=35)
                .then(facts -> facts.put("result",0))
                .build();
    }

    public static Rule oneRunRule()
    {
        return new RuleBuilder()
                .name("1 run rule")
                .description("checks if random number is greater than 35 and less than equal to 60")
                .when( facts -> (int)facts.get("random")>35 && (int)facts.get("random")<=60)
                .then(facts -> facts.put("result", 1))
                .build();
    }

    public static Rule twoRunRule()
    {
        return new RuleBuilder()
                .name("2 runs rule")
                .description("checks if random number is greater than 60 and less than equal to 77")
                .when( facts -> (int)facts.get("random")>60 && (int)facts.get("random")<=77)
                .then(facts -> facts.put("result",2))
                .build();
    }

    public static Rule threeRule()
    {
        return new RuleBuilder()
                .name("3 runs rule")
                .description("checks if random number is greater than 77 and less than equal to 82")
                .when( facts -> (int)facts.get("random")>77 && (int)facts.get("random")<=82)
                .then(facts -> facts.put("result",3))
                .build();
    }


    public static Rule fourRule()
    {
        return new RuleBuilder()
                .name("4 runs rule")
                .description("checks if random number is greater 82 and less than equal to 87")
                .when( facts -> (int)facts.get("random")>82 && (int)facts.get("random")<=87)
                .then(facts -> facts.put("result",4))
                .build();
    }


    public static Rule fiveRule()
    {
        return new RuleBuilder()
                .name("5 runs rule")
                .description("checks if random number is equal to 88")
                .when( facts -> (int)facts.get("random")==88)
                .then(facts -> facts.put("result",5))
                .build();
    }

    public static Rule sixRunRule()
    {
        return new RuleBuilder()
                .name("6 runs rule")
                .description("checks if random number is greater than 88 and less than equal to 90")
                .when( facts -> (int)facts.get("random")>88 && (int)facts.get("random")<=90)
                .then(facts -> facts.put("result",6))
                .build();
    }

    public static Rule wicketRule()
    {
        return new RuleBuilder()
                .name("Wicket rule")
                .description("checks if random number is greater than 90")
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

        rules.register(zeroRule());
        rules.register(oneRunRule());
        rules.register(twoRunRule());
        rules.register(threeRule());
        rules.register(fourRule());
        rules.register(fiveRule());
        rules.register(sixRunRule());
        rules.register(wicketRule());

        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);

        return facts.get("result");
    }

}
