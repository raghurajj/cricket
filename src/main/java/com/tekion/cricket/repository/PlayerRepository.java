package com.tekion.cricket.repository;

import com.tekion.cricket.dbconnector.MySqlConnector;
import com.tekion.cricket.dataTypes.PlayerData;
import com.tekion.cricket.dataTypes.PlayerDb;
import com.tekion.cricket.dataTypes.PlayerInfo;
import com.tekion.cricket.dataTypes.WicketData;
import com.tekion.cricket.models.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerRepository {


    /*
    insert players of a team into players table in db
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
            playerInfo.setTeam_id(rs.getInt("team_id"));
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
        get list of all the wickets a player has helped in
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
            wicketHelper.setHelper_id(rs.getInt("wicket_helper"));
            wicketHelper.setBatsman_id(rs.getInt("player_id"));
            wicketHelper.setBowler_id(rs.getInt("bowled_by"));
            wicketHelper.setWicket_type(rs.getString("wicket_type"));
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
        player.setTeam_id(rs.getInt("team_id"));
        player.setName(fetchPlayerNameById(player.getId()));
        player.setType(fetchPlayerTypeById(player.getId()));
        player.setState(rs.getString("player_state"));
        player.setRuns_scored(rs.getInt("runs_scored"));
        player.setBalls_played(rs.getInt("balls_played"));
        player.setFour_count(rs.getInt("four_count"));
        player.setSix_count(rs.getInt("six_count"));
        player.setBowled_by(rs.getInt("bowled_by"));
        player.setWicket_type(rs.getString("wicket_type"));
        player.setWicket_helper(rs.getInt("wicket_helper"));
        player.setOvers_bowled(rs.getFloat("overs_bowled"));
        player.setRuns_given(rs.getInt("runs_given"));
        player.setWickets_taken(rs.getInt("wickets_taken"));
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
            playerDb.setPlayer_data(getPlayerData(rs));
        }

        playerDb.setWickets_list(getWicketsData(id,matchId));
        return playerDb;
    }

}
