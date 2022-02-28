package com.tekion.cricket;
import com.tekion.cricket.dbconnector.MySqlConnector;
import com.tekion.cricket.services.MatchService;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        MySqlConnector.initializeConnection();

        MatchService matchService = new MatchService();
        matchService.initialiseGame();
    }
}
