package br.com.boardproject;


import br.com.boardproject.persistence.migration.MigrationStrategy;
import br.com.boardproject.ui.MainMenu;

import java.sql.SQLException;

import static br.com.boardproject.persistence.config.ConnectionConfig.getConnection;

public class Main {
    public static void main(String[] args) throws SQLException {
        try(var connection = getConnection()){
            new MigrationStrategy(connection).executeMigration();
        }
        new MainMenu().execute();
    }
}