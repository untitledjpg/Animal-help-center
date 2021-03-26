package com.example.Animalhelpcenter.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdoptionApplication {

    private Integer id;
    private String name;
    private String surname;
    private Integer phoneNumber;
    private String email;
    private Integer catId;
    private String otherPets;
    private String children;

}
