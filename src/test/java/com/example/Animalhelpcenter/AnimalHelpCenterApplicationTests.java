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

//	@Test
//	public void getApplications() {
//		var apps = dhm.getApplications();
//		for (var app : apps
//		) {
//			System.out.println(app.getName() + " " + app.getCatId() + " " + app.getCatName());
//		}
//	}

	@Test
	public void getCatById() {
		int id = 5;
		var cat = dhm.getCatById(id);
		System.out.println(cat.getName());
	}

	@Test
	public void getApplicationById() {
		int id = 1;
		var appl = dhm.getApplicationsWithCat();
		System.out.println(appl.get(0).getChildren());
		System.out.println(appl.get(3).getCat().getName());
	}

	@Test
	public void updateCat() {
		int id = 6;
		var cat = dhm.getCatById(6);
		System.out.println(cat.getName() + " " + cat.getCatStatus());
		cat.setCatStatus("adopted");
		dhm.updateCat(cat);
		System.out.println(cat.getName() + " " + cat.getCatStatus());

	}

}
