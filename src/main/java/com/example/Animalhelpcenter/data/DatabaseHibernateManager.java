package com.example.Animalhelpcenter.data;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHibernateManager {

    private static SessionFactory factory;

    public DatabaseHibernateManager() {
        try {
            factory = new Configuration()
                    .configure()
                    .addAnnotatedClass(Cat.class) //reads configuration of class
                    .addAnnotatedClass(AdoptionApplication.class)
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


    public void updateCat(Cat cat){ // check id, so that we don't use wrapper methods
        if (cat.getId() == 0){ // if don't have specific item
            return;
        }
        update(cat);
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

}
