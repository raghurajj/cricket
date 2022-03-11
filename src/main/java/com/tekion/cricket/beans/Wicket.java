package com.tekion.cricket.beans;

import com.tekion.cricket.enums.WicketType;
import org.springframework.stereotype.Component;


/*
holds all the info regarding a wicket
 */
@Component
public class Wicket {
    private Player bowler;
    private WicketType wicketType;
    private Player helper;

    public Wicket(){}

    public Player getBowler() {
        return bowler;
    }

    public void setBowler(Player bowler) {
        this.bowler = bowler;
    }

    public WicketType getWicketType() {
        return wicketType;
    }

    public void setWicketType(WicketType wicketType) {
        this.wicketType = wicketType;
    }

    public Player getHelper() {
        return helper;
    }

    public void setHelper(Player helper) {
        this.helper = helper;
    }
}
