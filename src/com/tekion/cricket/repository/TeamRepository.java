package com.tekion.cricket.repository;

import com.tekion.cricket.constants.StringUtils;
import com.tekion.cricket.dbconnector.MySqlConnector;
import com.tekion.cricket.models.Player;
import com.tekion.cricket.models.Team;

import java.sql.*;
import java.util.List;

public class TeamRepository {


    /*
    insert team's info in teams table in db
     */
    public static void insertTeam(Team team) throws SQLException, ClassNotFoundException{
        String teamName = team.getTeamName();
        Connection connection = MySqlConnector.getConnection();
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

            PlayerRepository.insertTeamPlayers(team.getPlayers(), teamId);
            connection.commit();
        }
        catch (SQLException sqle){
            connection.rollback();
            throw sqle;
        }
    }

    /*
    returns name of the team given it's id
     */
    public static String fetchTeamNameById(int teamId) throws SQLException, ClassNotFoundException {
        String teamName="";
        Connection connection = MySqlConnector.getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM teams where id="+teamId;
        ResultSet rs = statement.executeQuery(query);
        if(rs.next())
        {
            teamName = rs.getString("name");
        }
        return  teamName;
    }
    public static void printTeamScore(int matchId, int teamId) throws SQLException, ClassNotFoundException {
        Connection connection = MySqlConnector.getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM match_data where match_id="+matchId+" and team_id="+teamId;
        ResultSet rs = statement.executeQuery(query);
        if(rs.next())
        {
            System.out.println("\n\n"+fetchTeamNameById(teamId)+": "+rs.getInt("team_score")+"/"+rs.getInt("number_of_wickets_fell")+" in "+rs.getFloat("overs"));
        }
    }

    /*
    print stats of a batsman of a particular match and team
     */
    public static void printBattingScorecard(int matchId, int teamId) throws SQLException, ClassNotFoundException {
        Connection connection = MySqlConnector.getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM scorecards where match_id="+matchId+" and team_id="+teamId;
        ResultSet rs = statement.executeQuery(query);

        System.out.printf(StringUtils.NINE_STRING_INPUT, StringUtils.PLAYERNAME,StringUtils.PLAYERSTATE,StringUtils.RUNSCORED,StringUtils.BALLS_PLAYED,StringUtils.FOUR_COUNT,StringUtils.SIX_COUNT,StringUtils.BOWLED_BY, StringUtils.WICKET_TYPE, StringUtils.WICKET_HELPER);
        System.out.println(StringUtils.BIG_DOT_LINE);
        while(rs.next())
        {
            String bowler = rs.getInt("bowled_by")==-1?"":PlayerRepository.fetchPlayerNameById(rs.getInt("bowled_by"));
            String wicketType = rs.getString("wicket_type");
            String wicketHelper=rs.getInt("wicket_helper")==-1?"":PlayerRepository.fetchPlayerNameById(rs.getInt("wicket_helper"));
            System.out.printf(StringUtils.NINE_STRING_INPUT,PlayerRepository.fetchPlayerNameById(rs.getInt("player_id")),rs.getString("player_state")
                    ,rs.getInt("runs_scored"),rs.getInt("balls_played"),rs.getInt("four_count")
                    ,rs.getInt("six_count"),bowler,wicketType,wicketHelper);
        }
    }

    /*
    fetch data from scorecard table and
    print following info:
    bowlerName,runsGiven,oversBowled,wicketsTaken
     */
    public static void printBowlingScorecard(int matchId, int teamId) throws SQLException, ClassNotFoundException {
        Connection connection = MySqlConnector.getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM scorecards where match_id="+matchId+" and team_id="+teamId;
        ResultSet rs = statement.executeQuery(query);
        System.out.println("\n");
        System.out.printf(StringUtils.FOUR_STRING_INPUT, StringUtils.BOWLERNAME,StringUtils.OVERBOWLED,StringUtils.RUNSGIVEN,StringUtils.WICKETSTAKEN);
        System.out.println(StringUtils.BIG_DOT_LINE);
        while(rs.next())
        {
            if(rs.getInt("overs_bowled")>0)
                System.out.printf(StringUtils.FOUR_STRING_INPUT,PlayerRepository.fetchPlayerNameById(rs.getInt("player_id")),rs.getFloat("overs_bowled")
                        ,rs.getInt("runs_given"),rs.getInt("wickets_taken"));
        }
    }

}
