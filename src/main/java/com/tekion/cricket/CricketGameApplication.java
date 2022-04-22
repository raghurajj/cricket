package com.tekion.cricket;
import com.tekion.cricket.dbconnector.MySqlConnector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.SQLException;

@SpringBootApplication
@EnableSwagger2
@EnableCaching
public class CricketGameApplication {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        SpringApplication.run(CricketGameApplication.class,args);

        MySqlConnector.initializeConnection();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tekion.cricket"))
                .paths(PathSelectors.any())
                .build();
    }

}
