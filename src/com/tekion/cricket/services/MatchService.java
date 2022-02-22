package com.tekion.cricket.services;

import com.tekion.cricket.constants.StringUtils;
import com.tekion.cricket.models.Team;
import com.tekion.cricket.helpers.MatchServiceHelper;

import java.util.Scanner;

public class MatchService {
    private Team firstTeam;
    private Team secondTeam;
    private final int totalAvailableBalls;

    public MatchService()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println(StringUtils.NUMBER_OF_MATCHES);
        totalAvailableBalls = sc.nextInt() * 6;
        setTeams();
    }

    public void setTeams()
    {
        firstTeam = MatchServiceHelper.getTeam("first", StringUtils.FIRST_TEAM_PLAYERS, totalAvailableBalls);
        secondTeam = MatchServiceHelper.getTeam("second", StringUtils.SECOND_TEAM_PLAYERS,totalAvailableBalls);
    }

    public void playSingleMatch()
    {
        MatchServiceHelper.playSingleMatch(firstTeam, secondTeam,totalAvailableBalls);
    }

    public void playSeries()
    {
        MatchServiceHelper.playSeries(firstTeam,secondTeam,totalAvailableBalls);
    }



}
