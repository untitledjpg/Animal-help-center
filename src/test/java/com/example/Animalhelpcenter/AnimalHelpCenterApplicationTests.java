package com.example.Animalhelpcenter;

import com.example.Animalhelpcenter.repositories.DatabaseHibernateManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AnimalHelpCenterApplicationTests {
	DatabaseHibernateManager dhm = new DatabaseHibernateManager();

	@Test
	void contextLoads() {
	}

	@Test
	public void getApplications() {
		var apps = dhm.getApplications();
		for (var app : apps
		) {
			System.out.println(app.getName() + " " + app.getCatId() + " " + app.getCatName());
		}
	}

	@Test
	public void getCatById() {
		int id = 5;
		var cat = dhm.getCatById(id);
		System.out.println(cat.getName());
	}

}
