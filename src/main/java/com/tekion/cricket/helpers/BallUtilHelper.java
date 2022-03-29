package com.tekion.cricket.helpers;

import com.tekion.cricket.beans.Player;
import com.tekion.cricket.enums.PlayerType;
import com.tekion.cricket.rules.BatsmanRunsRule;

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
        if((batsmen.getPlayerType() == PlayerType.BATSMAN) || (batsmen.getPlayerType() == PlayerType.ALLROUNDER))
        {
                return BatsmanRunsRule.getBallResult();
        }
        else{
                return BatsmanRunsRule.getBallResult();
        }
    }
}
