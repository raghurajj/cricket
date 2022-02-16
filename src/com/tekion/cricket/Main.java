package com.tekion.cricket;
import com.tekion.cricket.enums.MatchType;
import com.tekion.cricket.enums.Winner;
import java.util.Scanner;

public class Main {

    public static void playSeries(Team firstTeam, Team secondTeam,int totalGames, int totalAvailableBalls)
    {
        int matchesWonByFirstTeam=0, matchesWonBySecondTeam=0, countTies=0, countDraws=0;
        Match match;
        for(int game=0;game<totalGames;game++)
        {
            System.out.println("\n\n----------- Match number: "+(game+1)+"-------------\n");
            match = new Match(firstTeam,secondTeam,totalAvailableBalls);
            match.startMatch();
            match.printScoreCard();
            if(match.getWinner() == Winner.FIRSTTEAM)matchesWonByFirstTeam++;
            else if(match.getWinner() == Winner.SECONDTEAM)matchesWonBySecondTeam++;
            else if(match.getWinner() == Winner.TIE)countTies++;
            else countDraws++;
            match.reset();
        }

        System.out.println("\n\n ||   Series Result: "+firstTeam.getTeamName()+" "+matchesWonByFirstTeam+" - "+matchesWonBySecondTeam+" "+secondTeam.getTeamName()+"  || \n\n\n");
    }

    public static Team getTeam(String teamNumber,String[] teamPlayers, int totalAvailableBalls)
    {
        Scanner sc =  new Scanner(System.in);
        String teamName;
        System.out.println("Enter Name of the "+ teamNumber+" Team");
        teamName = sc.nextLine();
        return new Team(teamName, totalAvailableBalls, teamPlayers);
    }

    public static void main(String[] args) {
        String[] firstTeamPlayers = {"Shikhar", "Rohit", "Virat", "SKY", "Rishabh", "Hardik", "Chahal", "Shami", "Bumrah", "Siraj", "Kuldeep"};
        String[] secondTeamPlayers = {"Warner", "Finch", "Steve", "Alex", "Wade", "Maxwell", "Marsh", "Starc", "Mitch", "Josh", "kane"};
        int choice;
        System.out.println("Press 1 to start a Match , Press 2 to start a series");
        Scanner sc = new Scanner(System.in);
        choice = sc.nextInt();
        MatchType matchType = (choice == 1 ? MatchType.SINGLE : MatchType.SERIES);
        System.out.println("Enter Number of Overs in the Match");
        int totalAvailableBalls = sc.nextInt() * 6;
        Team firstTeam = getTeam("first", firstTeamPlayers, totalAvailableBalls);
        Team secondTeam = getTeam("second", secondTeamPlayers, totalAvailableBalls);


        Match match;
        switch (matchType) {
            case SINGLE:
                match = new Match(firstTeam, secondTeam,totalAvailableBalls);
                match.startMatch();
                match.printScoreCard();
                break;
            case SERIES:
                int totalGames;
                System.out.println("Enter total number of Matches to be Played: ");
                totalGames = sc.nextInt();
                playSeries(firstTeam, secondTeam, totalGames, totalAvailableBalls);
                break;
            default:
                System.out.println("Please enter either 1 or 2 !!");

        }


    }
}
