package com.tekion.cricket.repository;

import com.tekion.cricket.dbconnector.MySqlConnector;
import com.tekion.cricket.dataTypes.PlayerData;
import com.tekion.cricket.dataTypes.PlayerDb;
import com.tekion.cricket.dataTypes.PlayerInfo;
import com.tekion.cricket.dataTypes.WicketHelper;
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

    public static PlayerInfo getPlayerInfoById(int id) throws SQLException, ClassNotFoundException {
        PlayerInfo playerInfo = new PlayerInfo();
        Connection connection = MySqlConnector.getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM players where id="+id;
        ResultSet rs = statement.executeQuery(query);
        if(rs.next())
        {
            playerInfo.setId(id);
            playerInfo.setName(rs.getString("name"));
            playerInfo.setType(rs.getString("type"));
            playerInfo.setTeamId(rs.getInt("team_id"));
        }
        return playerInfo;
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

    public static String fetchPlayerTypeById(int playerId) throws SQLException, ClassNotFoundException {
        String type="";
        Connection connection = MySqlConnector.getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM players where id="+playerId;
        ResultSet rs = statement.executeQuery(query);
        if(rs.next())
        {
            type = rs.getString("type");
        }
        return  type;
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

    public static void fillPlayerData(PlayerData player, ResultSet rs) throws SQLException, ClassNotFoundException {
        player.setId(rs.getInt("player_id"));
        player.setTeamId(rs.getInt("team_id"));
        player.setName(fetchPlayerNameById(player.getId()));
        player.setType(fetchPlayerTypeById(player.getId()));
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

    public static PlayerDb getPlayerStats(int matchId, int id) throws SQLException, ClassNotFoundException {
        PlayerDb playerDb = new PlayerDb();
        Connection connection = MySqlConnector.getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM scorecards where match_id="+matchId+" and player_id="+id;
        ResultSet rs = statement.executeQuery(query);
        PlayerData player = new PlayerData();
        while(rs.next())
        {
            fillPlayerData(player,rs);
        }
        playerDb.setPlayerData(player);

        addWicketHelperData(playerDb,matchId);
        return playerDb;
    }

}
