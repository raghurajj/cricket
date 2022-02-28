package com.tekion.cricket.services;

import com.tekion.cricket.interfaces.Observer;
import com.tekion.cricket.models.Player;
import com.tekion.cricket.interfaces.Ball;
import com.tekion.cricket.models.Team;
import com.tekion.cricket.helpers.BallServiceHelper;

import java.util.ArrayList;
import java.util.List;

/*
handle result of every ball
notify all it's observers regarding result of each ball
 */
public class BallService implements Ball {
    private Team battingTeam;
    private Team bowlingTeam;
    private List<Observer> observers;
    private int runs;

    public BallService(Team battingTeam, Team bowlingTeam){
        this.battingTeam = battingTeam;
        this.bowlingTeam = bowlingTeam;
        observers = new ArrayList<>();
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public void playBall()
    {
        Player bowler = bowlingTeam.getPlayerByIndex(bowlingTeam.getCurrentBowler());
        Player batsmen = battingTeam.getPlayerByIndex(battingTeam.getStrikerPlayer());
        int result = BallServiceHelper.getBallResult(batsmen);
        this.setRuns(result);
        System.out.println(bowler.getName()+" to "+batsmen.getName()+" "+(result==7?"Out":result));
        this.notifyObservers();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        int i = observers.indexOf(observer);
        if (i >= 0) {
            observers.remove(i);
        }

    }

    @Override
    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            Observer observer = observers.get(i);
            observer.update(this.getRuns(),bowlingTeam);
        }
    }
}
