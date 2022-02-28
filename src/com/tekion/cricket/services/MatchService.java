package com.tekion.cricket.services;

import com.tekion.cricket.constants.StringUtils;
import com.tekion.cricket.dbconnector.MySqlConnector;
import com.tekion.cricket.helpers.MatchHelper;
import com.tekion.cricket.models.Match;
import com.tekion.cricket.models.Team;
import com.tekion.cricket.helpers.MatchServiceHelper;
import com.tekion.cricket.repository.TeamRepository;

import java.sql.SQLException;

/*
handle whether to play a game or fetch data from db
get data about type of match
initialise teams,overs
 */
public class MatchService {
    private Team firstTeam;
    private Team secondTeam;
    private  int totalAvailableBalls;

    public MatchService()
    {

    }

    public void setTeams()
    {
        firstTeam = MatchServiceHelper.getTeam(StringUtils.FIRST_TEAM_NAME, StringUtils.FIRST_TEAM_PLAYERS, totalAvailableBalls);
        secondTeam = MatchServiceHelper.getTeam(StringUtils.SECOND_TEAM_NAME, StringUtils.SECOND_TEAM_PLAYERS,totalAvailableBalls);

        try {
            TeamRepository.insertTeam(firstTeam);
            TeamRepository.insertTeam(secondTeam);
            System.out.println("Teams inserted into db!!");
        } catch(SQLException sqle){
            System.out.println(sqle);
        } catch(Exception e){
            System.out.println("DB Error");
        }

    }

    public int getTotalAvailableBalls() {
        return totalAvailableBalls;
    }


    public void setTotalAvailableBalls(int totalAvailableBalls) {
        this.totalAvailableBalls = totalAvailableBalls;
    }

    public void initialiseGame()
    {
        MatchServiceHelper.initialiseGame(this);
    }

    public void playSingleMatch()
    {
        Match match = MatchServiceHelper.playSingleMatch(firstTeam, secondTeam,totalAvailableBalls);
        MatchHelper.insertMatchIntoDb(match);
    }

    public void playSeries()
    {
        MatchServiceHelper.playSeries(firstTeam,secondTeam,totalAvailableBalls);
    }



}
