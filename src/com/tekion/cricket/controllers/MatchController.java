package com.tekion.cricket.controllers;

import com.tekion.cricket.Match;
import com.tekion.cricket.Team;

import java.util.Scanner;

public class MatchController {
    Team firstTeam;
    Team secondTeam;
    private final int totalAvailableBalls;
    String[] firstTeamPlayers = {"Shikhar", "Rohit", "Virat", "SKY", "Rishabh", "Hardik", "Chahal", "Shami", "Bumrah", "Siraj", "Kuldeep"};
    String[] secondTeamPlayers = {"Warner", "Finch", "Steve", "Alex", "Wade", "Maxwell", "Marsh", "Starc", "Mitch", "Josh", "kane"};

    public MatchController()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Number of Overs in the Match");
        this.totalAvailableBalls = sc.nextInt() * 6;
        this.firstTeam = getTeam("first", firstTeamPlayers, totalAvailableBalls);
        this.secondTeam = getTeam("second", secondTeamPlayers, totalAvailableBalls);
    }

    public Team getTeam(String teamNumber,String[] teamPlayers, int totalAvailableBalls)
    {
        Scanner sc =  new Scanner(System.in);
        String teamName;
        System.out.println("Enter Name of the "+ teamNumber+" Team");
        teamName = sc.nextLine();
        return new Team(teamName, totalAvailableBalls, teamPlayers);
    }

    public void playSingleMatch()
    {
        Match match = new Match(this.firstTeam, this.secondTeam,this.totalAvailableBalls);
        match.startMatch();
        match.printScoreCard();
    }

    public void playSeries()
    {
        Scanner sc =  new Scanner(System.in);
        int totalGames;
        System.out.println("Enter total Number of Matches to be played: ");
        totalGames = sc.nextInt();
        int matchesWonByFirstTeam=0, matchesWonBySecondTeam=0, countTies=0, countDraws=0;
        Match match;
        for(int game=0;game<totalGames;game++)
        {
            System.out.println("\n\n----------- Match number: "+(game+1)+"-------------\n");
            match = new Match(this.firstTeam,this.secondTeam,this.totalAvailableBalls);
            match.startMatch();
            match.printScoreCard();
            if(match.getWinner() == this.firstTeam.getTeamName())matchesWonByFirstTeam++;
            else if(match.getWinner() == this.secondTeam.getTeamName())matchesWonBySecondTeam++;
            else if(match.getWinner() == "TIE")countTies++;
            else countDraws++;
            match.reset();
        }

        System.out.println("\n\n ||   Series Result: "+firstTeam.getTeamName()+" "+matchesWonByFirstTeam+" - "+matchesWonBySecondTeam+" "+secondTeam.getTeamName()+"  || \n\n\n");
    }

}
