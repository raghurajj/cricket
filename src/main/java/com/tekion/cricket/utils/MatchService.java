package com.tekion.cricket.utils;

import com.tekion.cricket.constants.StringUtils;
import com.tekion.cricket.helpers.MatchHelper;
import com.tekion.cricket.models.Match;
import com.tekion.cricket.models.Team;
import com.tekion.cricket.helpers.MatchServiceHelper;
import com.tekion.cricket.repository.TeamRepository;

import java.sql.SQLException;

public class MatchService {
    private Team firstTeam;
    private Team secondTeam;
    private  int totalAvailableBalls;

    public MatchService()
    {

    }

    public void setTeams(String firstTeamName, String secondTeamName)
    {
        firstTeam = new Team(firstTeamName, totalAvailableBalls,StringUtils.FIRST_TEAM_PLAYERS);
        secondTeam = new Team(secondTeamName,totalAvailableBalls, StringUtils.SECOND_TEAM_PLAYERS);

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

    public int initialiseGame(String matchType, int overs, int totalGames,String firstTeamName, String secondTeamName)
    {
        this.setTeams(firstTeamName,secondTeamName);
        this.totalAvailableBalls = overs*6;
        return MatchServiceHelper.getMatchData(this,matchType,overs,totalGames);
    }

    public int playSingleMatch(int overs)
    {
        Match match = MatchServiceHelper.playSingleMatch(firstTeam, secondTeam,totalAvailableBalls);
        MatchHelper.insertMatchIntoDb(match);
        return match.getId();
    }

    public int playSeries(int overs, int totalGames)
    {
        return (MatchServiceHelper.playSeries(firstTeam,secondTeam,totalGames,overs*6)).getId();
    }



}
