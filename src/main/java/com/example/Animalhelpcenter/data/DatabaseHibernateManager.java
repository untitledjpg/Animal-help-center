package com.example.Animalhelpcenter.data;

import com.example.Animalhelpcenter.mvc.AdoptionApplicationDto;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseHibernateManager {

    private static SessionFactory factory;

    public DatabaseHibernateManager() {
        try {
            factory = new Configuration()
                    .configure()
                    .addAnnotatedClass(Cat.class) //reads configuration of class
                    .addAnnotatedClass(AdoptionApplication.class)
                    .addAnnotatedClass(AdoptionApplicationDto.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public List<Cat> getCats() {
        var session = factory.openSession(); // opening channel to communicate with database

        try {
            return session.createQuery("FROM Cat").list();
        } catch (HibernateException ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    public void save(Object item){
        var session = factory.openSession();

        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(item);
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null){
                tx.rollback();
            }
            System.err.println(ex);
        } finally {
            session.close();
        }
    }

    public void update(Object item){ // important that item has id
        var session = factory.openSession();

        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(item);
            tx.commit();   // what is commit
        } catch (HibernateException ex) {
            if (tx != null){
                tx.rollback();
            }
            System.err.println(ex);
        } finally {
            session.close();
        }
    }



/*    public void update(Cat catToUpdate){ // important that item has id
        var session = factory.openSession();

        var name = newCat.getName();
        var id = newCat.getId();
        try {
            String hql = "UPDATE Cat set name = :name "  +
                    "WHERE id = :cat_id";
            Query query = session.createQuery(hql);
            query.setParameter("name", name);
            query.setParameter("cat_id", id);
            int result = query.executeUpdate();

        } catch (HibernateException ex) {

            System.err.println(ex);
        } finally {
            session.close();
        }
    }*/


    public void updateCat(Cat catToUpdate){ // check id, so that we don't use wrapper methods
        if (catToUpdate.getId() == 0){ // if don't have specific item
            return;
        }
        update(catToUpdate);
    }

    //delete deletes with instance of object, not just with id
    public void delete(Object item){
        var session = factory.openSession();

        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(item);
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null){
                tx.rollback();
            }
            System.err.println(ex);
        } finally {
            session.close();
        }
    }

    public Cat getById(int id){ // dont need transaction, because dont store data
        // it's not generic, it can only return Country
        // can use generic Class<T>?, because code is always the same for different classes
        var session = factory.openSession(); //

        try {
            return session.get(Cat.class, id);
        } catch (HibernateException ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
        return null; // was not able to find item by that id. could throw exception instead of null
    }


    public List<AdoptionApplicationDto> getApplications() {
        var session = factory.openSession(); // opening channel to communicate with database

        try {
            var query = session.createQuery("SELECT A.id, C.name, A.name, A.surname, A.phoneNumber, A.email," +
                    "A.catId, A.otherPets, A.children FROM AdoptionApplication A " +
                    "left join Cat C ON C.id = A.catId");
            var result = query.list().stream()
                    .map(item->mapToAdoptionApplicationDto(item))
                    .collect(Collectors.toList());

            int a = 1;
            return (ArrayList<AdoptionApplicationDto>)result;

//            var result = query.list();
//            return query.list();
        } catch (HibernateException ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    private AdoptionApplicationDto mapToAdoptionApplicationDto(Object obj){
        var row = (Object[]) obj;
        return new AdoptionApplicationDto((Integer)row[0], (String)row[1], (String)row[2], (String)row[3], (Integer)row[4],
        (String)row[5], (Integer)row[6], (String)row[7], (String)row[8]);
    }

    /*            return session.createQuery("FROM AdoptionApplication.id, Cat.name, AdoptionApplication.name," +
                    " AdoptionApplication .surname, AdoptionApplication.phoneNumber, " +
                    "AdoptionApplication.email, AdoptionApplication.catId, AdoptionApplication.otherPets," +
                    "AdoptionApplication.children ").list();*/

}
