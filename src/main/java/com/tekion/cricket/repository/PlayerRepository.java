package com.tekion.cricket.repository;

import com.tekion.cricket.dbconnector.MySqlConnector;
import com.tekion.cricket.dbmodels.PlayerData;
import com.tekion.cricket.dbmodels.PlayerDb;
import com.tekion.cricket.dbmodels.WicketHelper;
import com.tekion.cricket.models.Player;

import java.sql.*;
import java.util.ArrayList;
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

    public static void addWicketHelperData(PlayerDb playerDb, int matchId) throws SQLException, ClassNotFoundException {
        List<WicketHelper>wicketHelperList = new ArrayList<WicketHelper>();
        Connection connection = MySqlConnector.getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM scorecards where match_id="+matchId+" and wicket_helper="+playerDb.getPlayerData().getId();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next())
        {
            WicketHelper wicketHelper = new WicketHelper();
            wicketHelper.setHelperId(rs.getInt("wicket_helper"));
            wicketHelper.setBatsmanId(rs.getInt("player_id"));
            wicketHelper.setBowlerId(rs.getInt("bowled_by"));
            wicketHelper.setWicketType(rs.getString("wicket_type"));
            wicketHelperList.add(wicketHelper);
        }
        playerDb.setWicketHelperList(wicketHelperList);
    }

    public static PlayerDb getPlayerStats(int matchId, int id) throws SQLException, ClassNotFoundException {
        PlayerDb playerDb = new PlayerDb();
        Connection connection = MySqlConnector.getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM scorecards where match_id="+matchId+" and player_id="+id;
        ResultSet rs = statement.executeQuery(query);
        PlayerData player = new PlayerData();
        while(rs.next())
        {
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
        }
        playerDb.setPlayerData(player);

        addWicketHelperData(playerDb,matchId);
        return playerDb;
    }

}
