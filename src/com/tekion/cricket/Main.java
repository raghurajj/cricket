package com.tekion.cricket;
import com.tekion.cricket.enums.MatchType;
import com.tekion.cricket.enums.Winner;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {
        String[] firstTeamPlayers = {"Shikhar","Rohit","Virat","SKY","Rishabh","Hardik","Chahal","Shami","Bumrah","Siraj","Kuldeep"};
        String[] secondTeamPlayers = {"Warner","Finch","Steve","Alex","Wade","Maxwell","Marsh","Starc","Mitch","Josh","kane"};
        int choice;
        System.out.println("Press 1 to start a Match , Press 2 to start a series");
        Scanner sc =  new Scanner(System.in);
        choice = sc.nextInt();

        MatchType matchType = (choice==1?MatchType.SINGLE:MatchType.SERIES);


        Match match;
        switch(matchType){
            case SINGLE:
                match = new Match(firstTeamPlayers,secondTeamPlayers);
                match.startMatch();
                match.printScoreCard();
                break;
            case SERIES:
                int totalAvailableBalls,totalGames;
                System.out.println("Enter total number of Matches to be Played: ");
                totalGames = sc.nextInt();
                System.out.println("Enter Number of Overs in the Match");
                totalAvailableBalls = sc.nextInt()*6;
                sc.nextLine();
                String teamName;
                System.out.println("Enter Name of the first Team");
                teamName = sc.nextLine();
                Team team1 = new Team(teamName, totalAvailableBalls, firstTeamPlayers);
                System.out.println("Enter Name of the Second Team");
                teamName = sc.nextLine();
                Team team2 = new Team(teamName,totalAvailableBalls, secondTeamPlayers);

                int matchesWonByTeam1=0, matchesWonByTeam2=0, countTies=0, countDraws=0;

                for(int game=0;game<totalGames;game++)
                {
                    System.out.println("\n\n----------- Match number: "+(game+1)+"-------------\n");
                    match = new Match(team1,team2,totalAvailableBalls);
                    match.startMatch();
                    match.printScoreCard();
                    if(match.getWinner() == Winner.FIRSTTEAM)matchesWonByTeam1++;
                    else if(match.getWinner() == Winner.SECONDTEAM)matchesWonByTeam2++;
                    else if(match.getWinner() == Winner.TIE)countTies++;
                    else countDraws++;
                    match.reset();
                }

                System.out.println("\n\n ||   Series Result: "+team1.getTeamName()+" "+matchesWonByTeam1+" - "+matchesWonByTeam2+" "+team2.getTeamName()+"  || \n\n\n");


                break;
            default:
                System.out.println("Please enter either 1 or 2 !!");

        }


    }
}
