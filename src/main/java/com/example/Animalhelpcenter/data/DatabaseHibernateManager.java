package com.example.Animalhelpcenter.data;

import com.example.Animalhelpcenter.mvc.AdoptionApplicationDto;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

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
                    .addAnnotatedClass(Volunteer.class)
                    .addAnnotatedClass(TempHome.class)
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

    public Cat getCatById(int id){ // dont need transaction, because dont store data
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
        return null;
    }

    public void deleteCat(int id) {
        var session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            var cat = session.get(Cat.class, id);
            if(cat != null) {
                session.delete(cat);
            }
            tx.commit();
        } catch (HibernateException exception) {
            if(tx != null) {
                tx.rollback();
            }
            System.err.println(exception);
        } finally {
            session.close();
        }
    }

    public void updateCat(Cat catToUpdate){ // check id, so that we don't use wrapper methods
        if (catToUpdate.getId() == 0){ // if don't have specific item
            return;
        }
        update(catToUpdate);
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

    public void deleteApp(int id) {
        var session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            var apl = session.get(AdoptionApplication.class, id);
            if(apl != null) {
                session.delete(apl);
            }
            tx.commit();
        } catch (HibernateException exception) {
            if(tx != null) {
                tx.rollback();
            }
            System.err.println(exception);
        } finally {
            session.close();
        }
    }

    public List<Volunteer> getVolunteers() {
        var session = factory.openSession(); // opening channel to communicate with database

        try {
            return session.createQuery("FROM Volunteer").list();
        } catch (HibernateException ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    public Volunteer getVolunteerById(int id){ // dont need transaction, because dont store data
        // it's not generic, it can only return Country
        // can use generic Class<T>?, because code is always the same for different classes
        var session = factory.openSession(); //

        try {
            return session.get(Volunteer.class, id);
        } catch (HibernateException ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
        return null; // was not able to find item by that id. could throw exception instead of null
    }

    public List<TempHome> getTempHomes() {
        var session = factory.openSession(); // opening channel to communicate with database

        try {
            return session.createQuery("FROM TempHome ").list();
        } catch (HibernateException ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    public TempHome getTempHomeById(int id){ // dont need transaction, because dont store data
        // it's not generic, it can only return Country
        // can use generic Class<T>?, because code is always the same for different classes
        var session = factory.openSession(); //

        try {
            return session.get(TempHome.class, id);
        } catch (HibernateException ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
        return null; // was not able to find item by that id. could throw exception instead of null
    }
}
