package com.bank.bank_api.infraestructure.health;

import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Component("H2-Database")
@AllArgsConstructor
public class DatabaseHealthIndicator implements HealthIndicator {

    private final DataSource dataSource;

    @Override
    public Health health() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT 1")) {

            if (resultSet.next()) {
                return Health.up()
                        .withDetail("database", connection.getMetaData().getDatabaseProductName())
                        .withDetail("validationQuery", "SELECT 1")
                        .build();
            } else {
                return Health.down()
                        .withDetail("error", "La consulta de validación no devolvió resultados")
                        .build();
            }
        } catch (Exception e) {
            return Health.down()
                    .withDetail("error", e.getMessage())
                    .build();
        }
    }
}