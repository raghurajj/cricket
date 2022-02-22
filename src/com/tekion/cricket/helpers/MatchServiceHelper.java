package com.tekion.cricket.helpers;

import com.tekion.cricket.models.Match;
import com.tekion.cricket.models.Team;
import com.tekion.cricket.constants.StringUtils;

import java.util.Scanner;

public class MatchServiceHelper {
    public static Team getTeam(String teamNumber, String[] teamPlayers, int totalAvailableBalls)
    {
        Scanner sc =  new Scanner(System.in);
        String teamName;
        System.out.println("Enter Name of the "+ teamNumber+" Team");
        teamName = sc.nextLine();
        return new Team(teamName, totalAvailableBalls, teamPlayers);
    }

    public static Match playSingleMatch(Team firstTeam, Team secondTeam, int totalAvailableBalls)
    {
        Match match = new Match(firstTeam, secondTeam,totalAvailableBalls);
        match.startMatch();
        match.printScoreCard();

        return match;
    }

    public static void playSeries(Team firstTeam, Team secondTeam, int totalAvailableBalls)
    {
        Scanner sc =  new Scanner(System.in);
        int totalGames;
        System.out.println(StringUtils.MATCHES_TO_BE_PLAYED);
        totalGames = sc.nextInt();
        int matchesWonByFirstTeam=0, matchesWonBySecondTeam=0, countTies=0, countDraws=0;
        Match match;
        for(int game=1;game<=totalGames;game++)
        {
            System.out.println("\n\n----------- Match number: "+game+"-------------\n");
            match = playSingleMatch(firstTeam,secondTeam,totalAvailableBalls);
            if(match.getWinner().equals(firstTeam.getTeamName()))matchesWonByFirstTeam++;
            else if(match.getWinner().equals(secondTeam.getTeamName()))matchesWonBySecondTeam++;
            else if(match.getWinner().equals("TIE"))countTies++;
            else countDraws++;
            match.reset();
        }

        System.out.println("\n\n ||   Series Result: "+firstTeam.getTeamName()+" "+matchesWonByFirstTeam+" - "+matchesWonBySecondTeam+" "+secondTeam.getTeamName()+"  || \n\n\n");
    }
}
