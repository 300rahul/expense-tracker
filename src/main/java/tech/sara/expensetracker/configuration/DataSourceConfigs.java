package tech.sara.expensetracker.configuration;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfigs {

    @Bean
    public DataSource getDataSource(){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url(createUrl());
        dataSourceBuilder.username("postgres");
        dataSourceBuilder.password("password");
        return dataSourceBuilder.build();
    }

    private String createUrl() {
        return "jdbc:postgresql://" + "127.0.0.1" + ":" + "5432" + "/" + "expense_tracker";
    }
}
