package com.example.Animalhelpcenter.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
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


    public Integer addAdoptApplication(AdoptionApplication app) { // stored procedure
        Connection con = null;
        try {
            con = getConnection();

            var stmt = con.prepareCall("{CALL addAdoptApplication(?,?,?,?,?,?,?,?,?)}");

            stmt.setInt("id", app.getId());
            stmt.setString("name", app.getName());
            stmt.setString("surname", app.getSurname());
            stmt.setInt("phone_number", app.getPhoneNumber());
            stmt.setString("email", app.getEmail());
            stmt.setInt("catId", app.getCatId());
            stmt.setString("other_pets", app.getOtherPets());
            stmt.setString("children", app.getChildren());

            stmt.registerOutParameter("new_id", Types.INTEGER); // out

            stmt.executeUpdate(); // should return id

            Integer id = stmt.getInt("new_id");

            app.setId(id);

            con.close();

            return id;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionUrl, "test", "test123");
    }
}
