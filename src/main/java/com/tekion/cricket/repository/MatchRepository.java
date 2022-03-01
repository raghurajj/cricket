package com.tekion.cricket.repository;

import com.tekion.cricket.constants.StringUtils;
import com.tekion.cricket.dbconnector.MySqlConnector;
import com.tekion.cricket.dbmodels.MatchData;
import com.tekion.cricket.dbmodels.MatchDb;
import com.tekion.cricket.dbmodels.PlayerData;
import com.tekion.cricket.models.Match;
import com.tekion.cricket.models.Player;
import com.tekion.cricket.models.Wicket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatchRepository {

    public static void insertMatch(Match match) throws SQLException, ClassNotFoundException{
        int firstTeamId = match.getBowlingTeam().getId();
        int secondTeamId = match.getBattingTeam().getId();
        int tossWinner = match.getTossWinner()==match.getBattingTeam().getTeamName()?secondTeamId:firstTeamId;
        int battingFirst = firstTeamId;
        int winner;
        String wnr = match.getWinner();
        if(wnr == match.getBowlingTeam().getTeamName())winner = firstTeamId;
        else if(wnr == match.getBattingTeam().getTeamName())winner=secondTeamId;
        else if(wnr=="TIE")winner=-1;
        else winner=-2;

        float overs = (float)match.getTotalAvailableBalls()/6;
        int seriesId = match.getSeriesId();
        Connection connection = MySqlConnector.getConnection();
        connection.setAutoCommit(false);
        String query="insert into `matches`(first_team_id,second_team_id,winner,toss_winner,batting_first,overs,series_id) values (?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1,firstTeamId);
        statement.setInt(2,secondTeamId);
        statement.setInt(3,winner);
        statement.setInt(4,tossWinner);
        statement.setInt(5,battingFirst);
        statement.setFloat(6,overs);
        statement.setInt(7,seriesId);
        try {
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            int matchId = 0;
            if (rs.next())
                matchId = rs.getInt(1);

            match.setId(matchId);
            connection.commit();
        }
        catch (SQLException sqle){
            connection.rollback();
            throw sqle;
        }
    }

    public static void insertIntoMatchData(int matchId, int teamId, int teamScore,  int totalBallsPlayed,int numberOfWicketsFell) throws SQLException, ClassNotFoundException {
        float overs = (float)(totalBallsPlayed/6) + (float)(totalBallsPlayed%6)/10;
        Connection connection = MySqlConnector.getConnection();
        connection.setAutoCommit(false);
        String query="insert into match_data(match_id,team_id,team_score,overs,number_of_wickets_fell) values (?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1,matchId);
        statement.setInt(2,teamId);
        statement.setInt(3,teamScore);
        statement.setFloat(4,overs);
        statement.setInt(5,numberOfWicketsFell);
        try {
            statement.execute();
            connection.commit();
        }
        catch (SQLException sqle){
            connection.rollback();
            throw sqle;
        }
    }

    public static void insertIntoScorecard(int matchId, int teamId, int batsmanId , String playerState, int runsScored, int ballsPlayed, int fourCount, int sixCount, Wicket wicket, float overs, int runsGiven, int wicketsTaken) throws SQLException, ClassNotFoundException {
        Connection connection = MySqlConnector.getConnection();
        connection.setAutoCommit(false);
        int bowler = wicket==null?-1:wicket.getBowler().getId();
        String wicketType = wicket==null?"":wicket.getWicketType().toString();
        int wicketHelper = wicket==null || wicket.getHelper()==null?-1:wicket.getHelper().getId();
        String query="insert into scorecards(match_id,team_id,player_id,player_state,runs_scored,balls_played,four_count,six_count,bowled_by,wicket_type,wicket_helper,overs_bowled,runs_given,wickets_taken) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1,matchId);
        statement.setInt(2,teamId);
        statement.setInt(3,batsmanId);
        statement.setString(4,playerState);
        statement.setInt(5,runsScored);
        statement.setInt(6,ballsPlayed);
        statement.setInt(7,fourCount);
        statement.setInt(8,sixCount);
        statement.setInt(9,bowler);
        statement.setString(10,wicketType);
        statement.setInt(11,wicketHelper);
        statement.setFloat(12,overs);
        statement.setInt(13,runsGiven);
        statement.setInt(14,wicketsTaken);
        try {
            statement.execute();
            connection.commit();
        }
        catch (SQLException sqle){
            connection.rollback();
            throw sqle;
        }
    }

    public static void printTossResult(String tossWinner, String battingFirst)
    {
        if(tossWinner.equals(battingFirst))
        {
            System.out.println(tossWinner+" won the toss and decided to bat first");
        }
        else{
            System.out.println(tossWinner+ " won the toss and decided to bowl first");
        }
    }

    public static void printmatchResult(int winnerId) throws SQLException, ClassNotFoundException {
        if(winnerId==-1)
        {
            System.out.println("\nMatch Result: TIE");
        }
        else if(winnerId==-2)
        {
            System.out.println("\nMatch Result: DRAW");
        }
        else{
            System.out.println("\nTeam "+TeamRepository.fetchTeamNameById(winnerId)+" won the match");
        }
    }

    public static void printScoreCard(int matchId, int firstTeamId, int secondTeamId) throws SQLException, ClassNotFoundException {
        TeamRepository.printTeamScore(matchId,firstTeamId);
        TeamRepository.printBattingScorecard(matchId,firstTeamId);
        TeamRepository.printBowlingScorecard(matchId,secondTeamId);
        TeamRepository.printTeamScore(matchId,secondTeamId);
        TeamRepository.printBattingScorecard(matchId,secondTeamId);
        TeamRepository.printBowlingScorecard(matchId,firstTeamId);
    }


    public static void fetchScorecard(int matchId) throws SQLException, ClassNotFoundException {
        Connection connection = MySqlConnector.getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM `matches` where id="+matchId;
        ResultSet rs = statement.executeQuery(query);
        int firstTeamId,secondTeamId,winnerId,tossWinnerId,battingFirstId,overs;
        if(rs.next())
        {
            firstTeamId = rs.getInt("first_team_id");
            secondTeamId = rs.getInt("second_team_id");
            winnerId = rs.getInt("winner");
            tossWinnerId = rs.getInt("toss_winner");
            battingFirstId = rs.getInt("batting_first");

            String tossWinner = TeamRepository.fetchTeamNameById(tossWinnerId);
            String battingFirst = TeamRepository.fetchTeamNameById(battingFirstId);

            printTossResult(tossWinner,battingFirst);
            printmatchResult(winnerId);

            if(battingFirstId!=firstTeamId)
            {
                secondTeamId=firstTeamId;
                firstTeamId=battingFirstId;
            }
            printScoreCard(matchId,firstTeamId,secondTeamId);
        }
    }


    public static MatchData getMatchData(MatchDb matchDb,int teamId) throws SQLException, ClassNotFoundException {
        Connection connection = MySqlConnector.getConnection();
        Statement statement = connection.createStatement();
        MatchData matchData = new MatchData();
        String query = "SELECT * FROM match_data where match_id="+matchDb.getId()+" and team_id="+teamId;
        ResultSet rs = statement.executeQuery(query);
        if(rs.next())
        {

            matchData.setTeamId(teamId);
            matchData.setScore(rs.getInt("team_score"));
            matchData.setWickets(rs.getInt("number_of_wickets_fell"));
            matchData.setOvers(rs.getFloat("overs"));
        }
        return matchData;

    }

    public static List<PlayerData> getPlayersData(MatchDb matchDb) throws SQLException, ClassNotFoundException {
        List<PlayerData> players = new ArrayList<PlayerData>();
        Connection connection = MySqlConnector.getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM scorecards where match_id="+matchDb.getId()+" and team_id in ("+matchDb.getFirstTeamId()+","+(matchDb.getSecondTeamId())+")";
        ResultSet rs = statement.executeQuery(query);
        while(rs.next())
        {
            PlayerData player = new PlayerData();
            player.setId(rs.getInt("player_id"));
            player.setTeamId(rs.getInt("team_id"));
            player.setState(rs.getString("player_state"));
            player.setRunsScored(rs.getInt("runs_scored"));
            player.setBallsPlayed(rs.getInt("balls_played"));
            player.setFourCount(rs.getInt("four_count"));
            player.setSixCount(rs.getInt("six_count"));
            player.setBowledBy(rs.getInt("bowled_by"));
            player.setWicketType(rs.getString("wicket_type"));
            player.setWicketHelper(rs.getInt("wicket_helper"));
            player.setOversBowled(rs.getFloat("overs_bowled"));
            player.setRunsGiven(rs.getInt("runs_given"));
            player.setWicketsTaken(rs.getInt("wickets_taken"));
            players.add(player);
        }
        return players;
    }

    public static MatchDb getMatchById(int id) throws SQLException, ClassNotFoundException {
        Connection connection = MySqlConnector.getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM `matches` where id="+id;
        ResultSet rs = statement.executeQuery(query);

        MatchDb matchDb = new MatchDb();
        if(rs.next())
        {
            matchDb.setId(id);
            matchDb.setFirstTeamId(rs.getInt("first_team_id"));
            matchDb.setSecondTeamId(rs.getInt("second_team_id"));
            matchDb.setWinner(rs.getInt("winner"));
            matchDb.setTossWinner(rs.getInt("toss_winner"));
            matchDb.setBattingFirst(rs.getInt("batting_first"));
            matchDb.setOvers(rs.getFloat("overs"));
            matchDb.setSeriesId(rs.getInt("series_id"));

            matchDb.setFirstTeamMatchData(getMatchData(matchDb, matchDb.getFirstTeamId()));
            matchDb.setSecondTeamMatchData(getMatchData(matchDb,matchDb.getSecondTeamId()));

            matchDb.setPlayerData(getPlayersData(matchDb));
        }
        return matchDb;
    }
}
