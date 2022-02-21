package com.tekion.cricket.controllers;

import com.tekion.cricket.Observer;
import com.tekion.cricket.Player;
import com.tekion.cricket.Subject;
import com.tekion.cricket.Team;
import com.tekion.cricket.enums.PlayerType;

import java.util.Random;


public class BallController implements Subject {
    private Observer battingTeam; //observer1
    private Observer bowlingTeam; //observer2
    private int runs;


    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
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
        this.setRuns(runs);
        return runs;
    }

    public void playBall()
    {
        Player bowler = ((Team)bowlingTeam).getPlayerByIndex(((Team)bowlingTeam).getCurrentBowler());
        Player batsmen = ((Team)battingTeam).getPlayerByIndex(((Team)battingTeam).getStrikerPlayer());
        int result = this.getBallResult(batsmen);
        System.out.println(bowler.getName()+" to "+batsmen.getName()+" "+(result==7?"Out":result));
        this.notifyObservers();
    }

    @Override
    public void registerObserver(Observer observer, boolean isBattingTeam) {
        if(isBattingTeam)
        {
            this.battingTeam = observer;
        }
        else{
            this.bowlingTeam = observer;
        }
    }

    @Override
    public void removeObserver(Observer observer,boolean isBattingTeam) {
        if(isBattingTeam)this.battingTeam=null;
        else this.bowlingTeam=null;

    }

    @Override
    public void notifyObservers() {
        this.battingTeam.update(this.getRuns(),true,(Team)this.bowlingTeam);
        this.bowlingTeam.update(this.getRuns(),false,(Team)this.battingTeam);

    }
}
