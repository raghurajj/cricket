package com.tekion.cricket.helpers;

import com.tekion.cricket.beans.Player;
import com.tekion.cricket.enums.PlayerType;
import com.tekion.cricket.rules.BatsmanRunsRule;
import com.tekion.cricket.rules.BowlerRunsRule;

import java.util.Random;


/*
Provides helper methods to BallUtil class.
 */
public class BallUtilHelper {

    /*
    returns ball result for a particular ball
    according to the player type
     */
    public static int getBallResult(Player batsmen)
    {
        switch (batsmen.getPlayerType()){
            case BATSMAN:
            case ALLROUNDER:
                return  BatsmanRunsRule.getBallResult();
            case BOWLER:
                return BowlerRunsRule.getBallResult();
            default:
                return 0;
        }
    }
}
