package com.example.Animalhelpcenter;

import com.example.Animalhelpcenter.data.Cat;
import com.example.Animalhelpcenter.data.Volunteer;
import com.example.Animalhelpcenter.repositories.DatabaseManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AnimalHelpCenterApplicationTests {
	DatabaseManager mng = new DatabaseManager();

	@Test
	void contextLoads() {
	}

	@Test
	public void getCatById() {
		int id = 5;
		Cat cat = (Cat) mng.getObject(Cat.class, id);
		System.out.println(cat.getName());
	}

	@Test
	public void getApplicationById() {
		int id = 1;
		var appl = mng.getApplications();
		System.out.println(appl.get(0).getChildren());
		System.out.println(appl.get(3).getCat().getName());
	}

	@Test
	public void updateCat() {
		int id = 6;
		Cat cat = (Cat) mng.getObject(Cat.class, id);
		System.out.println(cat.getName() + " " + cat.getCatStatus());
		cat.setCatStatus("adopted");
		mng.updateObject(cat);
		System.out.println(cat.getName() + " " + cat.getCatStatus());

	}

	@Test
	public void getObject(){ // WORKS!!!
		Cat cat = (Cat) mng.getObject(Cat.class, 2);
		System.out.println("cat: " + cat.getName());

		Volunteer vol = (Volunteer) mng.getObject(Volunteer.class, 1);
		System.out.println("volunteer: " + vol.getName());
	}
}
