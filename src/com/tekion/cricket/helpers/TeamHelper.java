package com.tekion.cricket.helpers;

import com.tekion.cricket.constants.StringUtils;
import com.tekion.cricket.models.Player;
import com.tekion.cricket.models.Team;
import com.tekion.cricket.enums.PlayerState;
import com.tekion.cricket.enums.PlayerType;

public class TeamHelper {
    public static void printBattingStats(Team team)
    {
        System.out.println("\nteam "+team.getTeamName()+" : " + team.getTeamScore() +"/"+(team.getNextPlayer()-2) +" in "+(team.getTotalPlayedBalls()/6) +"."+(team.getTotalPlayedBalls()%6) +" overs");
        System.out.println(StringUtils.BATTING_STATS);

        System.out.printf(StringUtils.SIX_STRING_INPUT, StringUtils.PLAYERNAME,StringUtils.PLAYERSTATE,StringUtils.RUNSCORED,StringUtils.BALLS_PLAYED,StringUtils.FOUR_COUNT,StringUtils.SIX_COUNT);
        System.out.println(StringUtils.BIG_DOT_LINE);
        for(int i=0;i<11;i++)
        {
            Player player = team.getPlayerByIndex(i);
            String gotOutTo=player.getPlayerState()== PlayerState.OUT?"got out to "+player.getGotOutTo().getName():"";
            System.out.printf(StringUtils.SEVEN_STRING_INPUT,player.getName(),player.getPlayerState(),player.getRunScored(),player.getNumberOfBallPlayed(),player.getFourCount(),player.getSixCount(),gotOutTo);
        }
    }

    public static void printBowlingStats(Team team)
    {
        System.out.println(StringUtils.BOWLING_STATS);

        int maxNumberOfBalls =((int) Math.ceil(((team.getTotalAvailableBalls()/6))/5))*6;
        System.out.printf(StringUtils.FOUR_STRING_INPUT, StringUtils.BOWLERNAME,StringUtils.OVERBOWLED,StringUtils.RUNSGIVEN,StringUtils.WICKETSTAKEN);
        System.out.println(StringUtils.DOT_LINE);
        for(int i=5;i<11;i++)
        {
            Player bowler = team.getPlayerByIndex(i);
            if((bowler.getPlayerType()== PlayerType.BOWLER) || (bowler.getPlayerType()== PlayerType.ALLROUNDER)) {
                if (bowler.getNumberOfBallsLeftToBowl() != maxNumberOfBalls) {
                    int totalBallsBowled = maxNumberOfBalls - bowler.getNumberOfBallsLeftToBowl();
                    System.out.printf( StringUtils.FOUR_STRING_INPUT,bowler.getName(),(totalBallsBowled / 6) + "." + (totalBallsBowled % 6),bowler.getRunsGiven(),bowler.getNumberOfWicketsTaken());
                }
            }
        }

        System.out.println(StringUtils.DOT_LINE);
    }

    public static void reset(Team team,int totalAvailableBalls)
    {
        team.setTeamScore(0);
        team.setNextPlayer(0);
        team.setStrikerPlayer(0);
        team.setNonStrikerPlayer(0);
        team.setCurrentBowler(0);
        team.setTotalPlayedBalls(0);
        int overs = totalAvailableBalls/6;
        int oversPerBowler = (int) Math.ceil(overs/5);
        for(int i=0;i<11;i++)
        {
            team.getPlayerByIndex(i).reset(oversPerBowler*6);
        }
    }

    public static void handleWicket(Team team,Team opposition)
    {
        team.getPlayerByIndex(team.getStrikerPlayer()).setPlayerState(PlayerState.OUT);
        team.getPlayerByIndex(team.getStrikerPlayer()).setGotOutTo(opposition.getPlayerByIndex(opposition.getCurrentBowler()));
        if(team.getNextPlayer()<11){
            team.setStrikerPlayer(team.getNextPlayer());
        }
        team.setNextPlayer(team.getNextPlayer()+1);
    }

    public static void addPlayers(Team team, String[]teamPlayers, int totalAvailableBalls)
    {
        int overs = totalAvailableBalls/6;
        int oversPerBowler = (int) Math.ceil(overs/5);
        String playerName;
        Player player;
        for(int i=0;i<11;i++)
        {
            playerName = teamPlayers[i];
            player = new Player(playerName);
            if(i<5){
                player.setPlayerType(PlayerType.BATSMAN);
            }
            else if(i==5)
            {
                player.setPlayerType(PlayerType.ALLROUNDER);
                player.setNumberOfBallsLeftToBowl(oversPerBowler*6);
            }
            else{
                player.setPlayerType(PlayerType.BOWLER);
                player.setNumberOfBallsLeftToBowl(oversPerBowler*6);
            }
            team.addPlayer(player);
        }
    }
}
