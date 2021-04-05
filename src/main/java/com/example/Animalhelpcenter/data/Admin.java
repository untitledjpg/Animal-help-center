package com.example.Animalhelpcenter.data;
<<<<<<< HEAD

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

=======

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private int id;

    @Column(name = "admin_login")
    private String login;

    @Column(name = "admin_password")
    private String password;
}
>>>>>>> 84a808a (Added many templates, changed design)
