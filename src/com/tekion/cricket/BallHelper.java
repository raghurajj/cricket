package com.tekion.cricket;

import com.tekion.cricket.enums.PlayerState;
import com.tekion.cricket.enums.PlayerType;

import java.util.Random;


public class BallHelper {
    private final Team battingTeam;
    private final Team bowlingTeam;


    public BallHelper(Team battingTeam, Team bowlingTeam)
    {
        this.battingTeam = battingTeam;
        this.bowlingTeam = bowlingTeam;
    }


    public void handleWicket()
    {
        Player bowler = this.bowlingTeam.getPlayerByIndex(this.bowlingTeam.getCurrentBowler());
        bowler.incrementNumberOfWicketsTaken();
        Player batsmen = this.battingTeam.getPlayerByIndex(this.battingTeam.getStrikerPlayer());
        batsmen.setGotOutTo(bowler);
        batsmen.setPlayerState(PlayerState.OUT);

        this.battingTeam.handleWicket();

    }
    public static int getRandom(int min, int max)
    {
        return new Random().nextInt(max - min + 1) + min;
    }

    public int getBallResultForBatsman(int random)
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
    public int getBallResultForBowler(int random)
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

    public int getBallResult(Player batsmen)
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

    public void playBall()
    {
        Player bowler = bowlingTeam.getPlayerByIndex(bowlingTeam.getCurrentBowler());
        Player batsmen = battingTeam.getPlayerByIndex(battingTeam.getStrikerPlayer());
        int result = getBallResult(batsmen);
        switch(result){
            case 0:
                System.out.println(bowler.getName()+" to "+batsmen.getName()+" "+result);
                battingTeam.incrementTeamScore(0);
                bowler.incrementRunsGiven(result);
                break;
            case 1:
                System.out.println(bowler.getName()+" to "+batsmen.getName()+" "+result);
                battingTeam.incrementTeamScore(1);
                bowler.incrementRunsGiven(result);
                battingTeam.changeStrike();
                break;
            case 2:
                System.out.println(bowler.getName()+" to "+batsmen.getName()+" "+result);
                battingTeam.incrementTeamScore(2);
                bowler.incrementRunsGiven(result);
                break;
            case 3:
                System.out.println(bowler.getName()+" to "+batsmen.getName()+" "+result);
                battingTeam.incrementTeamScore(3);
                bowler.incrementRunsGiven(result);
                battingTeam.changeStrike();
                break;
            case 4:
                System.out.println(bowler.getName()+" to "+batsmen.getName()+" "+result);
                battingTeam.incrementTeamScore(4);
                bowler.incrementRunsGiven(result);
                break;
            case 5:
                System.out.println(bowler.getName()+" to "+batsmen.getName()+" "+result);
                battingTeam.incrementTeamScore(5);
                bowler.incrementRunsGiven(result);
                battingTeam.changeStrike();
                break;
            case 6:
                System.out.println(bowler.getName()+" to "+batsmen.getName()+" "+result);
                battingTeam.incrementTeamScore(6);
                bowler.incrementRunsGiven(result);
                break;
            case 7:
                System.out.println(bowler.getName()+" to "+batsmen.getName()+" Out");
                handleWicket();
                break;

        }

    }

}
