package com.tekion.cricket;
import com.tekion.cricket.dbconnector.MySqlConnector;
import com.tekion.cricket.services.MatchService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.sql.SQLException;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ApplicationContext context = SpringApplication.run(Main.class,args);

        MySqlConnector.initializeConnection();

//        MatchService matchService = new MatchService();
//        matchService.initialiseGame();
    }
}
