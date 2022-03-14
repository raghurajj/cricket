package com.tekion.cricket.repository;

import com.tekion.cricket.dbconnector.MySqlConnector;
import com.tekion.cricket.models.PlayerData;
import com.tekion.cricket.models.PlayerDb;
import com.tekion.cricket.models.PlayerBasicInfo;
import com.tekion.cricket.models.WicketData;
import com.tekion.cricket.beans.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/*
handles db related operations on players table
 */
public class PlayerRepository {


    /*
    insert all the players of a team into
    the players table in db. and store players'
    id of all the player after inserting them
    into db.
     */
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


    /*
    get Players basic info by playerId
     */
    public static PlayerBasicInfo getPlayerInfoById(int id) throws SQLException, ClassNotFoundException {
        PlayerBasicInfo playerInfo = new PlayerBasicInfo();
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


    /*
    get players name by playerId
     */
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


    /*
    get Players' Type by playerId
     */
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


    /*
        get list of all the wickets a player has
        participated in
     */
    public static List<WicketData> getWicketsData(int playerId, int matchId) throws SQLException, ClassNotFoundException {
        List<WicketData>wicketHelperList = new ArrayList<WicketData>();
        Connection connection = MySqlConnector.getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM scorecards where match_id="+matchId+" and (wicket_helper="+playerId+" or bowled_by = "+playerId+")";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next())
        {
            WicketData wicketHelper = new WicketData();
            wicketHelper.setHelperId(rs.getInt("wicket_helper"));
            wicketHelper.setBatsmanId(rs.getInt("player_id"));
            wicketHelper.setBowlerId(rs.getInt("bowled_by"));
            wicketHelper.setWicketType(rs.getString("wicket_type"));
            wicketHelperList.add(wicketHelper);
        }
        return wicketHelperList;
    }


    /*
    extract a players data from the resultset
     */
    public static PlayerData getPlayerData(ResultSet rs) throws SQLException, ClassNotFoundException {
        PlayerData player = new PlayerData();
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
        return player;
    }


    /*
    get all the stats of a player by his id
     */
    public static PlayerDb getPlayerStats(int matchId, int id) throws SQLException, ClassNotFoundException {
        PlayerDb playerDb = new PlayerDb();
        Connection connection = MySqlConnector.getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM scorecards where match_id="+matchId+" and player_id="+id;
        ResultSet rs = statement.executeQuery(query);
        while(rs.next())
        {
            playerDb.setPlayerData(getPlayerData(rs));
        }

        playerDb.setWicketsList(getWicketsData(id,matchId));
        return playerDb;
    }

}
