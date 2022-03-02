package com.tekion.cricket.helpers;

import com.tekion.cricket.constants.StringUtils;
import com.tekion.cricket.enums.WicketType;
import com.tekion.cricket.models.Match;
import com.tekion.cricket.models.Player;
import com.tekion.cricket.models.Team;
import com.tekion.cricket.enums.PlayerState;
import com.tekion.cricket.enums.PlayerType;
import com.tekion.cricket.models.Wicket;
import com.tekion.cricket.repository.MatchRepository;

import java.sql.SQLException;
import java.util.Random;

public class TeamHelper {

    /*
    reset data of a team
     */
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

    /*
    return a random number b/w min and max both inclusive
     */
    public static int getRandom(int min, int max)
    {
        return new Random().nextInt(max - min + 1) + min;
    }

    /*
    return a type of wicket according to following
    probability distribution
    caught:34,bold:20,lbw:30,runOut:8,hitWicket:2,Stumped:6
     */
    public static WicketType getWicketType()
    {
        int random = getRandom(1,100);
        if(random>=1 && random<35)
        {
            return WicketType.CAUGHT;
        }
        else if(random>=35 && random<55)
        {
            return WicketType.BOLD;
        }
        else if(random>=55 && random<85)
        {
            return WicketType.LBW;
        }
        else if(random>=85 && random<93)
        {
            return  WicketType.RUN_OUT;
        }
        else if(random>=93 && random<95)
            return  WicketType.HIT_WICKET;
        else{
            return WicketType.STUMPED;
        }
    }

    /*
    returns the player who caught or run-out- a batsman
    (if applicable) else returns null
     */
    public static Player getWicketHelper(Team opposition, WicketType wicketType)
    {
        if((wicketType==WicketType.CAUGHT) || (wicketType==WicketType.RUN_OUT) )
        {
            return opposition.getPlayerByIndex(getRandom(0,10));
        }
        else if(wicketType==WicketType.HIT_WICKET || wicketType==WicketType.LBW || wicketType==WicketType.BOLD)
        {
            return  null;
        }
        else if(wicketType==WicketType.STUMPED)
            return opposition.getWicketKeeper();

        return null;
    }

    /*
    handle teams players on wicket and updates
     necessary info
     */
    public static void handleWicket(Team team,Team opposition)
    {
        team.getPlayerByIndex(team.getStrikerPlayer()).setPlayerState(PlayerState.OUT);
        Wicket wicket  = new Wicket();
        WicketType wicketType = getWicketType();
        wicket.setWicketType(wicketType);
        wicket.setHelper(getWicketHelper(opposition,wicketType));
        wicket.setBowler(opposition.getPlayerByIndex(opposition.getCurrentBowler()));
        team.getPlayerByIndex(team.getStrikerPlayer()).setWicket(wicket);
        if(team.getNextPlayer()<11){
            team.setStrikerPlayer(team.getNextPlayer());
        }
        team.setNextPlayer(team.getNextPlayer()+1);
    }

    /*
    add players into the team
     */
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

    /*
    insert player data into database
     */
    public static void insertIntoScorecard(Match match, Team team)
    {
        int teamId = team.getId();
        int matchId = match.getId();
        int maxNumberOfBalls =((int) Math.ceil(((team.getTotalAvailableBalls()/6))/5))*6;
        for(int i=0;i<11;i++)
        {
            Player player = team.getPlayerByIndex(i);
            Wicket wicket = player.getPlayerState()== PlayerState.OUT?player.getWicket():null;
            int totalBallsBowled = maxNumberOfBalls - player.getNumberOfBallsLeftToBowl();
            float overs = ((float) (totalBallsBowled / 6)) + ((float) (totalBallsBowled % 6)/10);
            if(player.getPlayerType() == PlayerType.BATSMAN)overs=0;
            try {
                MatchRepository.insertIntoScorecard(matchId,teamId,player.getId(),player.getPlayerState().toString(),player.getRunScored(),player.getNumberOfBallPlayed(),player.getFourCount(),player.getSixCount(),wicket,overs,player.getRunsGiven(),player.getNumberOfWicketsTaken());
            } catch(SQLException sqle){
                System.out.println(sqle);
            } catch(Exception e){
                System.out.println("DB Error");
            }

        }
    }


    /*
    insert match data in database
     */
    public static void insertTeamMatchData(Match match, Team team)
    {
        try {
            MatchRepository.insertIntoMatchData(match.getId(),team.getId(),team.getTeamScore(),team.getTotalPlayedBalls(), team.getNextPlayer()-2);
        } catch(SQLException sqle){
            System.out.println(sqle);
        } catch(Exception e){
            System.out.println("DB Error");
        }
    }


}
