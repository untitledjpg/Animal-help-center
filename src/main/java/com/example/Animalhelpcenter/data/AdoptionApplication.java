package com.example.Animalhelpcenter.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "adoption_app")
public class AdoptionApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "phone_number")
    private Integer phoneNumber;
    @Column(name = "email")
    private String email;
    @Column(name = "cat_id")
    private Integer catId;
    @Column(name = "other_pets")
    private String otherPets;
    @Column(name = "children")
    private String children;
}
