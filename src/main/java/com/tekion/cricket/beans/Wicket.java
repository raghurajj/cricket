package com.tekion.cricket.beans;

import com.tekion.cricket.enums.WicketType;
import org.springframework.stereotype.Component;


/*
holds all the info regarding a wicket
 */
public class Wicket {
    private final Player bowler;
    private final WicketType wicketType;
    private final Player helper;

    public static class Builder {

        private final Player bowler;
        private WicketType wicketType;
        private Player helper;

        public Builder(Player bowler) {
            this.bowler = bowler;
        }

        public Builder helper(Player wicketHelper) {
            helper = wicketHelper;
            return this;
        }

        public Builder type(WicketType type) {
            wicketType = type;
            return this;
        }

        public Wicket build() {
            return new Wicket(this);
        }
    }

    private Wicket(Builder builder){
        bowler = builder.bowler;
        wicketType = builder.wicketType;
        helper = builder.helper;
    }

    public Player getBowler() {
        return bowler;
    }

    public WicketType getWicketType() {
        return wicketType;
    }

    public Player getHelper() {
        return helper;
    }
}
