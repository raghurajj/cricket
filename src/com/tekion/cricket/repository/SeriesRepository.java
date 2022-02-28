package com.tekion.cricket.repository;

import com.tekion.cricket.dbconnector.MySqlConnector;
import com.tekion.cricket.models.Series;

import java.sql.*;

public class SeriesRepository {
    /*
    insert series data into series table in db
     */
    public static void insertSeries(Series series) throws SQLException, ClassNotFoundException{
        Connection connection = MySqlConnector.getConnection();
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
}
