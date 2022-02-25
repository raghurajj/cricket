package com.tekion.cricket.services;

import com.tekion.cricket.constants.StringUtils;
import com.tekion.cricket.dbconnector.MySqlConnector;
import com.tekion.cricket.enums.MatchType;
import com.tekion.cricket.helpers.MatchHelper;
import com.tekion.cricket.helpers.SeriesHelper;
import com.tekion.cricket.models.Match;
import com.tekion.cricket.models.Team;
import com.tekion.cricket.helpers.MatchServiceHelper;

import java.sql.SQLException;
import java.util.Scanner;

public class MatchService {
    private Team firstTeam;
    private Team secondTeam;
    private  int totalAvailableBalls;

    public MatchService()
    {

    }

    public void setTeams()
    {
        firstTeam = MatchServiceHelper.getTeam("first", StringUtils.FIRST_TEAM_PLAYERS, totalAvailableBalls);
        secondTeam = MatchServiceHelper.getTeam("second", StringUtils.SECOND_TEAM_PLAYERS,totalAvailableBalls);

        try {
            MySqlConnector.insertTeam(firstTeam);
            MySqlConnector.insertTeam(secondTeam);
            System.out.println("Teams inserted into db!!");
        } catch(SQLException sqle){
            System.out.println(sqle);
        } catch(Exception e){
            System.out.println("DB Error");
        }

    }

    public void databaseOps(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Match id to fetch scorecard");
        int matchId = sc.nextInt();

        try {
            MySqlConnector.fetchScorecard(matchId);
        } catch(SQLException sqle){
            System.out.println(sqle);
        } catch(Exception e){
            System.out.println("DB Error");
        }
    }

    public void getMatchData()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println(StringUtils.NUMBER_OF_OVERS);
        totalAvailableBalls = sc.nextInt() * 6;
        setTeams();
        System.out.println(StringUtils.MATCH_TYPE);
        int choice = sc.nextInt();
        MatchType matchType = (choice == 1 ? MatchType.SINGLE : MatchType.SERIES);
        switch (matchType) {
            case SINGLE:
                playSingleMatch();
                break;
            case SERIES:
                playSeries();
                break;
            default:
                System.out.println(StringUtils.MAIN_SWITCH_DEFAULT_MESSAGE);

        }
    }

    public void initialiseGame()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter 1 to play matches , Enter 2 to fetch data from database");
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                getMatchData();
                break;
            case 2:
                databaseOps();
                break;
            default:
                System.out.println(StringUtils.MAIN_SWITCH_DEFAULT_MESSAGE);

        }
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
