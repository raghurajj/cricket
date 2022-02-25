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
        PreparedStatement statement = connection.prepareStatement("insert into teams(name) values(?)", Statement.RETURN_GENERATED_KEYS);
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
            statement = connection.prepareStatement("insert into players(name, type, team_id) values (?,?,?)",Statement.RETURN_GENERATED_KEYS);
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

    public static void insertSeries(Series series) throws SQLException, ClassNotFoundException{
        Connection connection = getConnection();
        connection.setAutoCommit(false);
        String query="insert into series(first_team_id,second_team_id,total_matches,overs) values (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1,series.getFirstTeam().getId());
        statement.setInt(2,series.getSecondTeam().getId());
        statement.setInt(3,series.getNumberOfGames());
        statement.setFloat(4,(float)(series.getTotalAvailableBalls()/6));
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

    public static void insertIntoScorecard(int matchId, int teamId,int batsmanId , String playerState,int runsScored, int ballsPlayed, int fourCount, int sixCount, int gotOutTo, float overs, int runsGiven, int wicketsTaken) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        connection.setAutoCommit(false);
        String query="insert into scorecards(match_id,team_id,player_id,player_state,runs_scored,balls_played,four_count,six_count,got_out_to,overs_bowled,runs_given,wickets_taken) values (?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1,matchId);
        statement.setInt(2,teamId);
        statement.setInt(3,batsmanId);
        statement.setString(4,playerState);
        statement.setInt(5,runsScored);
        statement.setInt(6,ballsPlayed);
        statement.setInt(7,fourCount);
        statement.setInt(8,sixCount);
        statement.setInt(9,gotOutTo);
        statement.setFloat(10,overs);
        statement.setInt(11,runsGiven);
        statement.setInt(12,wicketsTaken);
        try {
            statement.execute();
            connection.commit();
        }
        catch (SQLException sqle){
            connection.rollback();
            throw sqle;
        }
    }



    public static void insertIntoMatchData(int matchId, int teamId, int teamScore,  int totalBallsPlayed,int numberOfWicketsFell) throws SQLException, ClassNotFoundException {
        float overs = (float)(totalBallsPlayed/6) + (float)(totalBallsPlayed%6)/10;
        Connection connection = getConnection();
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

    public static String fetchTeamNameById(int teamId) throws SQLException, ClassNotFoundException {
        String teamName="";
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM teams where id="+teamId;
        ResultSet rs = statement.executeQuery(query);
        if(rs.next())
        {
            teamName = rs.getString("name");
        }
        return  teamName;
    }

    public static String fetchPlayerNameById(int playerId) throws SQLException, ClassNotFoundException {
        String playerName="";
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM players where id="+playerId;
        ResultSet rs = statement.executeQuery(query);
        if(rs.next())
        {
            playerName = rs.getString("name");
        }
        return  playerName;
    }

    public static void printTeamScore(int matchId, int teamId) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM match_data where match_id="+matchId+" and team_id="+teamId;
        ResultSet rs = statement.executeQuery(query);
        if(rs.next())
        {
            System.out.println("\n\n"+fetchTeamNameById(teamId)+": "+rs.getInt("team_score")+"/"+rs.getInt("number_of_wickets_fell")+" in "+rs.getFloat("overs"));
        }
    }

    public static void printBattingScorecard(int matchId, int teamId) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM scorecards where match_id="+matchId+" and team_id="+teamId;
        ResultSet rs = statement.executeQuery(query);

        System.out.printf(StringUtils.SEVEN_STRING_INPUT, StringUtils.PLAYERNAME,StringUtils.PLAYERSTATE,StringUtils.RUNSCORED,StringUtils.BALLS_PLAYED,StringUtils.FOUR_COUNT,StringUtils.SIX_COUNT,StringUtils.GOT_OUT_TO);
        System.out.println(StringUtils.BIG_DOT_LINE);
        while(rs.next())
        {
            System.out.printf(StringUtils.SEVEN_STRING_INPUT,fetchPlayerNameById(rs.getInt("player_id")),rs.getString("player_state")
                                ,rs.getInt("runs_scored"),rs.getInt("balls_played"),rs.getInt("four_count")
                                ,rs.getInt("six_count"),rs.getInt("got_out_to")==-1?"":fetchPlayerNameById(rs.getInt("got_out_to")));
        }
    }

    public static void printBowlingScorecard(int matchId, int teamId) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM scorecards where match_id="+matchId+" and team_id="+teamId;
        ResultSet rs = statement.executeQuery(query);
        System.out.println("\n");
        System.out.printf(StringUtils.FOUR_STRING_INPUT, StringUtils.BOWLERNAME,StringUtils.OVERBOWLED,StringUtils.RUNSGIVEN,StringUtils.WICKETSTAKEN);
        System.out.println(StringUtils.BIG_DOT_LINE);
        while(rs.next())
        {
            if(rs.getInt("overs_bowled")>0)
            System.out.printf(StringUtils.FOUR_STRING_INPUT,fetchPlayerNameById(rs.getInt("player_id")),rs.getInt("overs_bowled")
                    ,rs.getInt("runs_given"),rs.getInt("wickets_taken"));
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
            System.out.println("\nTeam "+fetchTeamNameById(winnerId)+" won the match");
        }
    }

    public static void printScoreCard(int matchId, int firstTeamId, int secondTeamId) throws SQLException, ClassNotFoundException {
        printTeamScore(matchId,firstTeamId);
        printBattingScorecard(matchId,firstTeamId);
        printBowlingScorecard(matchId,secondTeamId);
        printTeamScore(matchId,secondTeamId);
        printBattingScorecard(matchId,secondTeamId);
        printBowlingScorecard(matchId,firstTeamId);
    }

    public static void fetchScorecard(int matchId) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
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

            String tossWinner = fetchTeamNameById(tossWinnerId);
            String battingFirst = fetchTeamNameById(battingFirstId);

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


}
