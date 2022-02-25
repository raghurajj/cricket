package com.tekion.cricket;
import com.tekion.cricket.constants.StringUtils;
import com.tekion.cricket.dbconnector.MySqlConnector;
import com.tekion.cricket.services.MatchService;
import com.tekion.cricket.enums.MatchType;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        MySqlConnector.initializeConnection();

        MatchService matchService = new MatchService();
        matchService.initialiseGame();
    }
}
