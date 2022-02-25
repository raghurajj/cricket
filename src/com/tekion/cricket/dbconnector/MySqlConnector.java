package com.tekion.cricket.dbconnector;

import com.tekion.cricket.PrivateData;
import com.tekion.cricket.constants.StringUtils;
import com.tekion.cricket.models.Match;
import com.tekion.cricket.models.Player;
import com.tekion.cricket.models.Series;
import com.tekion.cricket.models.Team;

import java.sql.*;
import java.util.List;

public class MySqlConnector {
    private static Connection connection = null;
    public static void initializeConnection() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/cricket?autoReconnect=true&useSSL=false";
        String user = "root";
        String password = PrivateData.mysqlPassword;
        connection = DriverManager.getConnection(url,user,password);

    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        if(connection == null || connection.isClosed()) {
            initializeConnection();
        }
        return connection;
    }

    public static void insertTeam(Team team) throws SQLException, ClassNotFoundException{
        String teamName = team.getTeamName();
        Connection connection = getConnection();
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.prepareStatement("insert into team(name) values(?)", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1,teamName);
        try {
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            int teamId = 0;
            if (rs.next())
                teamId = rs.getInt(1);

            team.setId(teamId);

            insertTeamPlayers(team.getPlayers(), teamId);
            connection.commit();
        }
        catch (SQLException sqle){
            connection.rollback();
            throw sqle;
        }
    }

    private static void insertTeamPlayers(List<Player> players, int teamId) throws SQLException {
        PreparedStatement statement;
        for(Player player:players){
            statement = connection.prepareStatement("insert into player(name, type, team_id) values (?,?,?)",Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,player.getName());
            statement.setString(2, player.getPlayerType().toString());
            statement.setInt(3, teamId);
            statement.execute();
            int playerId=0;
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next())
                playerId = rs.getInt(1);

            player.setId(playerId);

        }
    }

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
        Connection connection = getConnection();
        connection.setAutoCommit(false);
        String query="insert into `match`(first_team_id,second_team_id,winner,toss_winner,batting_first,overs,series_id) values (?,?,?,?,?,?,?)";
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

    public static void insertSeries(Series series) throws SQLException, ClassNotFoundException{
        Connection connection = getConnection();
        connection.setAutoCommit(false);
        String query="insert into series(first_team_id,second_team_id,total_matches,overs) values (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1,series.getFirstTeam().getId());
        statement.setInt(2,series.getSecondTeam().getId());
        statement.setInt(3,series.getNumberOfGames());
        statement.setFloat(4,(float)(series.getTotalAvailableBalls()/6));
//        statement.setString(5,series.getWinner());
        try {
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            int seriesId = 0;
            if (rs.next())
                seriesId = rs.getInt(1);

            series.setId(seriesId);
            connection.commit();
        }
        catch (SQLException sqle){
            connection.rollback();
            throw sqle;
        }
    }

    public static void insertIntoBattingScorecard(int matchId, int teamId,int batsmanId ,String playerName, String playerState,int runs, int balls, int fourCount, int sixCount, String gotOutTo) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        connection.setAutoCommit(false);
        String query="insert into batting_scorecard(match_id,team_id,batsman_id,batsman_name,batsman_state,runs,balls,four_count,six_count,got_out_to) values (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1,matchId);
        statement.setInt(2,teamId);
        statement.setInt(3,batsmanId);
        statement.setString(4,playerName);
        statement.setString(5,playerState);
        statement.setInt(6,runs);
        statement.setInt(7,balls);
        statement.setInt(8,fourCount);
        statement.setInt(9,sixCount);
        statement.setString(10,gotOutTo);
        try {
            statement.execute();
            connection.commit();
        }
        catch (SQLException sqle){
            connection.rollback();
            throw sqle;
        }
    }

    public static void insertIntoBowlingScorecard(int matchId, int teamId, int bowlerId,String bowlerName, float oversBowled,int runsGiven, int wicketsTaken) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        connection.setAutoCommit(false);
        String query="insert into bowling_scorecard(match_id,team_id,bowler_id,bowler_name,overs_bowled,runs_given,wickets_taken) values (?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1,matchId);
        statement.setInt(2,teamId);
        statement.setInt(3,bowlerId);
        statement.setString(4,bowlerName);
        statement.setFloat(5,oversBowled);
        statement.setInt(6,runsGiven);
        statement.setInt(7,wicketsTaken);
        try {
            statement.execute();
            connection.commit();
        }
        catch (SQLException sqle){
            connection.rollback();
            throw sqle;
        }
    }


    public static void insertIntoPlayerMatchData(int matchId, int playerId, int runsScored, String playerState, int numberOfBallsPlayed,int numberOfBallsLeftToBowl,int numberOfWicketsTaken,int fourCount, int sixCount ,int runsGiven, int gotOutTo) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        connection.setAutoCommit(false);
        String query="insert into player_match_data(match_id,player_id,runs_scored,player_state,number_of_balls_played,number_of_balls_left_to_bowl,number_of_wickets_taken,four_count, six_count,runs_given,got_out_to) values (?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1,matchId);
        statement.setInt(2,playerId);
        statement.setInt(3,runsScored);
        statement.setString(4,playerState);
        statement.setInt(5,numberOfBallsPlayed);
        statement.setInt(6,numberOfBallsLeftToBowl);
        statement.setInt(7,numberOfWicketsTaken);
        statement.setInt(8,fourCount);
        statement.setInt(9,sixCount);
        statement.setInt(10,runsGiven);
        statement.setInt(11,gotOutTo);
        try {
            statement.execute();
            connection.commit();
        }
        catch (SQLException sqle){
            connection.rollback();
            throw sqle;
        }
    }

    public static void insertIntoTeamMatchData(int matchId, int teamId, int teamScore,  int totalBallsPlayed,int numberOfWicketsFell) throws SQLException, ClassNotFoundException {
        float overs = (float)(totalBallsPlayed/6) + (float)(totalBallsPlayed%6)/10;
        Connection connection = getConnection();
        connection.setAutoCommit(false);
        String query="insert into team_match_data(match_id,team_id,team_score,overs,number_of_wickets_fell) values (?,?,?,?,?)";
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

    public static String fetchTeamNameById(int teamId) throws SQLException, ClassNotFoundException {
        String teamName="";
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM team where id="+teamId;
        ResultSet rs = statement.executeQuery(query);
        if(rs.next())
        {
            teamName = rs.getString("name");
        }
        return  teamName;
    }

    public static void printTeamScore(int matchId, int teamId) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM team_match_data where match_id="+matchId+" and team_id="+teamId;
        ResultSet rs = statement.executeQuery(query);
        if(rs.next())
        {
            System.out.println("\n\n"+fetchTeamNameById(teamId)+": "+rs.getInt("team_score")+"/"+rs.getInt("number_of_wickets_fell")+" in "+rs.getFloat("overs"));
        }
    }

    public static void printBattingScorecard(int matchId, int teamId) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM batting_scorecard where match_id="+matchId+" and team_id="+teamId;
        ResultSet rs = statement.executeQuery(query);

        System.out.printf(StringUtils.SEVEN_STRING_INPUT, StringUtils.PLAYERNAME,StringUtils.PLAYERSTATE,StringUtils.RUNSCORED,StringUtils.BALLS_PLAYED,StringUtils.FOUR_COUNT,StringUtils.SIX_COUNT,StringUtils.GOT_OUT_TO);
        System.out.println(StringUtils.BIG_DOT_LINE);
        while(rs.next())
        {
            System.out.printf(StringUtils.SEVEN_STRING_INPUT,rs.getString("batsman_name"),rs.getString("batsman_state")
                                ,rs.getInt("runs"),rs.getInt("balls"),rs.getInt("four_count")
                                ,rs.getInt("six_count"),rs.getString("got_out_to"));
        }
    }

    public static void printBowlingScorecard(int matchId, int teamId) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM bowling_scorecard where match_id="+matchId+" and team_id="+teamId;
        ResultSet rs = statement.executeQuery(query);
        System.out.println("\n");
        System.out.printf(StringUtils.FOUR_STRING_INPUT, StringUtils.BOWLERNAME,StringUtils.OVERBOWLED,StringUtils.RUNSGIVEN,StringUtils.WICKETSTAKEN);
        System.out.println(StringUtils.BIG_DOT_LINE);
        while(rs.next())
        {
            System.out.printf(StringUtils.FOUR_STRING_INPUT,rs.getString("bowler_name"),rs.getInt("overs_bowled")
                    ,rs.getInt("runs_given"),rs.getInt("wickets_taken"));
        }
    }




    public static void fetchScorecard(int matchId) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM `match` where id="+matchId;
        ResultSet rs = statement.executeQuery(query);
        int firstTeamId,secondTeamId,winnerId,tossWinnerId,battingFirstId,overs;
        if(rs.next())
        {
            firstTeamId = rs.getInt("first_team_id");
            secondTeamId = rs.getInt("second_team_id");
            winnerId = rs.getInt("winner");
            tossWinnerId = rs.getInt("toss_winner");
            battingFirstId = rs.getInt("batting_first");
            overs = rs.getInt("overs");
//            System.out.println(firstTeamId+" "+secondTeamId+" "+winnerId+" "+tossWinnerId+" "+battingFirstId+" "+overs);

            String firstTeam = fetchTeamNameById(firstTeamId);
            String secondTeam = fetchTeamNameById(secondTeamId);
            String tossWinner = fetchTeamNameById(tossWinnerId);
            String battingFirst = fetchTeamNameById(battingFirstId);

            if(tossWinner.equals(battingFirst))
            {
                System.out.println(tossWinner+" won the toss and decided to bat first");
            }
            else{
                System.out.println(tossWinner+ " won the toss and decided to bowl first");
            }

            if(winnerId==-1)
            {
                System.out.println("\nMatch Result: TIE");
            }
            else if(winnerId==-2)
            {
                System.out.println("\nMatch Result: DRAW");
            }
            else{
                System.out.println("\nTeam "+fetchTeamNameById(winnerId)+" won the match");
            }

            if(battingFirstId!=firstTeamId)
            {
                secondTeamId=firstTeamId;
                firstTeamId=battingFirstId;
            }

            printTeamScore(matchId,firstTeamId);
            printBattingScorecard(matchId,firstTeamId);
            printBowlingScorecard(matchId,secondTeamId);
            printTeamScore(matchId,secondTeamId);
            printBattingScorecard(matchId,secondTeamId);
            printBowlingScorecard(matchId,firstTeamId);

        }
    }


}
