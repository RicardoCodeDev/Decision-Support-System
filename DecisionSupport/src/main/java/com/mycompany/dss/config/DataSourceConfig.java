package com.mycompany.dss.config;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

@Singleton(name = "DataSourceInitializer")
@Startup
@DataSourceDefinition(
    name = "java:app/jdbc/BatteryPassportDS",
    className = "com.microsoft.sqlserver.jdbc.SQLServerDataSource",
    url = "jdbc:sqlserver://localhost:1433;databaseName=BatteryPassportDS;encrypt=false",
    user = "sa",
    password = "",
    minPoolSize = 5,
    maxPoolSize = 20
)
public class DataSourceConfig {
}

