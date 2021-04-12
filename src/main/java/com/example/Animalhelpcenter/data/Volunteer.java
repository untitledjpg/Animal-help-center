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
@Table(name = "volunteer")
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "volunteer_id")
    private int id;
    @Column(name = "volunteer_name")
    private String name;
    @Column(name = "volunteer_surname")
    private String surname;
    @Column(name = "volunteer_phone")
    private Integer volunteerPhone;
    @Column(name = "volunteer_email")
    private String volunteerEmail;

    @SneakyThrows
    public static Volunteer create(ResultSet rs) {

        Volunteer volunteer = new Volunteer(rs.getInt("volunteer_id"),
                rs.getString("volunteer_name"),
                rs.getString("volunteer_surname"),
                rs.getInt("volunteer_phone"),
                rs.getString("volunteer_email"));

        return volunteer;
    }
}
