package com.tekion.cricket.repository;

import com.tekion.cricket.dbconnector.MySqlConnector;
import com.tekion.cricket.dbmodels.SeriesDb;
import com.tekion.cricket.models.Series;
import com.tekion.cricket.dbmodels.SeriesDb;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;

public class SeriesRepository {
<<<<<<< Updated upstream:src/com/tekion/cricket/repository/SeriesRepository.java
=======

    private JdbcTemplate jdbcTemplate;

    /*
    insert series data into series table in db
     */
>>>>>>> Stashed changes:src/main/java/com/tekion/cricket/repository/SeriesRepository.java
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

    public static SeriesDb getSeriesById(int id) throws SQLException, ClassNotFoundException {
        Connection connection = MySqlConnector.getConnection();
        String query="select * from series where id="+id;
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        SeriesDb series = new SeriesDb();
        if(rs.next())
        {
            series.setId(rs.getInt("id"));
            series.setFirstTeamId(rs.getInt("first_team_id"));
            series.setSecondTeamId(rs.getInt("second_team_id"));
            series.setOvers(rs.getInt("overs"));
            series.setTotalMatches(rs.getInt("total_matches"));
        }
        return series;
    }
}
