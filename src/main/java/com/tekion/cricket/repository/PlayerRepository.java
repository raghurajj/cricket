package com.tekion.cricket.repository;

import com.tekion.cricket.dbconnector.MySqlConnector;
import com.tekion.cricket.models.Player;

import java.sql.*;
import java.util.List;

public class PlayerRepository {


    public static void insertTeamPlayers(List<Player> players, int teamId) throws SQLException, ClassNotFoundException {
        PreparedStatement statement;
        for(Player player:players){
            statement = MySqlConnector.getConnection().prepareStatement("insert into players(name, type, team_id) values (?,?,?)",Statement.RETURN_GENERATED_KEYS);
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

    public static String fetchPlayerNameById(int playerId) throws SQLException, ClassNotFoundException {
        String playerName="";
        Connection connection = MySqlConnector.getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM players where id="+playerId;
        ResultSet rs = statement.executeQuery(query);
        if(rs.next())
        {
            playerName = rs.getString("name");
        }
        return  playerName;
    }

}
