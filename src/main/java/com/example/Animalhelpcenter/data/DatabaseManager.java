package com.example.Animalhelpcenter.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static final String connectionUrl = "jdbc:mysql://localhost:3306/animalhelpcenter?serverTimezone=UTC";

    public List<Cat> getCats() {
        List<Cat> cats = new ArrayList<>();

        try {
            var con = getConnection();
            var stmt = con.createStatement();
            var rs = stmt.executeQuery("SELECT * FROM cats;");

            while (rs.next()) { //while there are still more records

                cats.add(Cat.create(rs));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return cats;
    }


    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionUrl, "test", "test123");
    }
}
