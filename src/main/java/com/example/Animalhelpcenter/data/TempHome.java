package com.example.Animalhelpcenter.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import javax.persistence.*;
import java.sql.ResultSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "temp_home")
public class TempHome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "temp_home_id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "phone")
    private Integer phone;
    @Column(name = "email")
    private String email;
    @Column(name = "address")
    private String address;

    @SneakyThrows
    public static TempHome create(ResultSet rs) {

        TempHome tempHome = new TempHome(rs.getInt("temp_home_id"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getInt("phone"),
                rs.getString("email"),
                rs.getString("address"));

        return tempHome;
    }
}
