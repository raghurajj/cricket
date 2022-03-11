package com.tekion.cricket.repository;

import com.tekion.cricket.dbconnector.MySqlConnector;
import com.tekion.cricket.models.TeamDb;
import com.tekion.cricket.beans.Team;

import java.sql.*;

/*
handles db related operations on teams table
 */
public class TeamRepository {

    /*
    insert a team into teams table in db
    and store the teams' id in team object
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
    get teams' info  by teamId.
     */
    public static TeamDb getTeamById(int teamId) throws SQLException, ClassNotFoundException {
        TeamDb teamDb = new TeamDb();
        Connection connection = MySqlConnector.getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM teams where id="+teamId;
        ResultSet rs = statement.executeQuery(query);
        teamDb.setId(teamId);
        if(rs.next())
        {
            teamDb.setName(rs.getString("name"));
        }
        return  teamDb;
    }
    /*
    get teams' name  by teamId.
     */
    public static String getTeamNameById(int teamId) throws SQLException, ClassNotFoundException {
        String name="";
        Connection connection = MySqlConnector.getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT name FROM teams where id="+teamId;
        ResultSet rs = statement.executeQuery(query);
        if(rs.next())
        {
            name = rs.getString("name");
        }
        return  name;
    }

}
