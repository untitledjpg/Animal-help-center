package com.example.Animalhelpcenter.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;

@Data
@AllArgsConstructor
public class Cat {


    private Integer id;
    private String name;
    private Integer age;
    private String color;
    private String sex;
    private String nutured;
    private String description;
    private String picturePath;


    @SneakyThrows
    public static Cat create(ResultSet rs) {

        Cat cat = new Cat(rs.getInt("cat_id"),
                rs.getString("cat_name"),
                rs.getInt("cat_age"),
                rs.getString("cat_color"),
                rs.getString("cat_sex"),
                rs.getString("nutured"),
                rs.getString("description"),
                rs.getString("picture_path"));

        return cat;
    }
}
