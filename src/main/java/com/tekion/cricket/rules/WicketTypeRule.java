package com.tekion.cricket.rules;

import com.tekion.cricket.constants.StringUtils;
import com.tekion.cricket.enums.WicketType;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RuleBuilder;

import static com.tekion.cricket.helpers.TeamHelper.getRandom;

public class WicketTypeRule {

    public static Rule catchRule()
    {
        return new RuleBuilder()
                .name(StringUtils.CATCHRULE)
                .description(StringUtils.CATCHDESC)
                .when( facts -> (int)facts.get("random")>=1 && (int)facts.get("random")<35)
                .then(facts -> facts.put("wicketType",WicketType.CAUGHT))
                .build();
    }

    public static Rule boldRule()
    {
        return new RuleBuilder()
                .name(StringUtils.BOLDRULE)
                .description(StringUtils.BOLDDESC)
                .when( facts -> (int)facts.get("random")>=35 && (int)facts.get("random")<55)
                .then(facts -> facts.put("wicketType",WicketType.BOLD))
                .build();
    }

    public static Rule lbwRule()
    {
        return new RuleBuilder()
                .name(StringUtils.LBWRULE)
                .description(StringUtils.LBWDESC)
                .when( facts -> (int)facts.get("random")>=55 && (int)facts.get("random")<85)
                .then(facts -> facts.put("wicketType",WicketType.LBW))
                .build();
    }


    public static Rule runOutRule()
    {
        return new RuleBuilder()
                .name(StringUtils.RUNOUTRULE)
                .description(StringUtils.RUNOUTDESC)
                .when( facts -> (int)facts.get("random")>=85 && (int)facts.get("random")<93)
                .then(facts -> facts.put("wicketType",WicketType.RUN_OUT))
                .build();
    }


    public static Rule hitWicketRule()
    {
        return new RuleBuilder()
                .name(StringUtils.HITWICKETRULE)
                .description(StringUtils.HITWICKETDESC)
                .when( facts -> (int)facts.get("random")>=93 && (int)facts.get("random")<95)
                .then(facts -> facts.put("wicketType",WicketType.HIT_WICKET))
                .build();
    }

    public static Rule stumpingRule()
    {
        return new RuleBuilder()
                .name(StringUtils.STUMPINGRULE)
                .description(StringUtils.STUMPINGDESC)
                .when( facts -> (int)facts.get("random")>=95)
                .then(facts -> facts.put("wicketType",WicketType.STUMPED))
                .build();
    }


    /*
    return a type of wicket according to following
    probability distribution
    caught:34,bold:20,lbw:30,runOut:8,hitWicket:2,Stumped:6
     */
    public static WicketType getWicketType()
    {
        int random = getRandom(1,100);
        Facts facts = new Facts();
        facts.put("random",random);
        Rules rules = new Rules();

        rules.register(catchRule());
        rules.register(lbwRule());
        rules.register(boldRule());
        rules.register(runOutRule());
        rules.register(hitWicketRule());
        rules.register(stumpingRule());

        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);

        return facts.get("wicketType");
    }
}
