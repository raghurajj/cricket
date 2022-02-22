package com.tekion.cricket.helpers;

import com.tekion.cricket.models.Player;
import com.tekion.cricket.enums.PlayerType;

import java.util.Random;

public class BallServiceHelper {
    public static int getRandom(int min, int max)
    {
        return new Random().nextInt(max - min + 1) + min;
    }

    public static int getBallResultForBatsman(int random)
    {
        if(random>=1 && random <=20)
        {
            return 0;
        }
        else if(random>20 && random <=45)
        {
            return 1;
        }
        else if(random>45 && random<=69)
        {
            return 2;
        }
        else if(random>69 && random<=79)
        {
            return 3;
        }
        else if(random>79 && random<=89)
        {
            return 4;
        }
        else if(random==90)
        {
            return 5;
        }
        else if(random>90 && random<=95)
        {
            return 6;
        }
        else{
            return 7;
        }
    }


    //    bowler 0-35%, 1-25, 2-17, 3-5, 4-5%, 5-1%, 6-2%, 7-10%
    public static int getBallResultForBowler(int random)
    {
        if(random>=1 && random <=35)
        {
            return 0;
        }
        else if(random>35 && random <=60)
        {
            return 1;
        }
        else if(random>60 && random<=77)
        {
            return 2;
        }
        else if(random>77 && random<=82)
        {
            return 3;
        }
        else if(random>82 && random<=87)
        {
            return 4;
        }
        else if(random==88)
        {
            return 5;
        }
        else if(random>88 && random<=90)
        {
            return 6;
        }
        else{
            return 7;
        }
    }

    public static int getBallResult(Player batsmen)
    {
        int runs;
        int random = getRandom(1,100);
        if((batsmen.getPlayerType() == PlayerType.BATSMAN) || (batsmen.getPlayerType() == PlayerType.ALLROUNDER))
        {
            runs = getBallResultForBatsman(random);
        }
        else{
            runs = getBallResultForBowler(random);
        }
        return runs;
    }
}
