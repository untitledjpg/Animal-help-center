package com.example.Animalhelpcenter.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import javax.persistence.*;
import java.sql.ResultSet;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="cat")
public class Cat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cat_id")
    private Integer id;
    @Column(name = "cat_name")
    private String name;
    @Column(name = "cat_age")
    private Integer age;
    @Column(name = "cat_color")
    private String color;
    @Column(name = "cat_sex")
    private String sex;
    @Column(name = "neutered")
    private String neutered;
    @Column(name = "description")
    private String description;
    @Column(name = "picture_path")
    private String picturePath;
    @Column(name = "status")
    private String catStatus;
    @Column(name = "cat_arrival")
    private String catArrival;
    @Column(name = "temp_home_id")
    private Integer tempHomeId;


    @SneakyThrows
    public static Cat create(ResultSet rs) {

        Cat cat = new Cat(rs.getInt("cat_id"),
                rs.getString("cat_name"),
                rs.getInt("cat_age"),
                rs.getString("cat_color"),
                rs.getString("cat_sex"),
                rs.getString("neutered"),
                rs.getString("description"),
                rs.getString("picture_path"),
                rs.getString("status"),
                rs.getString("cat_arrival"),
                rs.getInt("temp_home_id"));

        return cat;
    }
}
