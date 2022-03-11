package com.tekion.cricket.helpers;

import com.tekion.cricket.models.Player;
import com.tekion.cricket.enums.PlayerType;

import java.util.Random;


/*
Provides helper methods to BallUtil class.
 */
public class BallUtilHelper {

    /*
    Returns a random number b/w min and max
    both inclusive.
     */
    public static int getRandom(int min, int max)
    {
        return new Random().nextInt(max - min + 1) + min;
    }


    /*
    returns ball result for batsman according to
    this probability distribution:
    0-10,1-25,2-24,3-10,4-10,5-1,6-5,out-5
     */
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

    /*
        returns ball result for bowler according to
        this probability distribution:
        0-35, 1-25, 2-17, 3-5, 4-5, 5-1, 6-2, out-10
         */
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

    /*
    returns ball result for a particular ball
    according to the batsman type
     */
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
