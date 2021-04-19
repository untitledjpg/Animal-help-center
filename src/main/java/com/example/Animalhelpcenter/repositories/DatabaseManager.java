package com.example.Animalhelpcenter.repositories;

import com.example.Animalhelpcenter.data.AdoptionApplication;
import com.example.Animalhelpcenter.data.Cat;
import com.example.Animalhelpcenter.data.TempHome;
import com.example.Animalhelpcenter.data.Volunteer;
import com.example.Animalhelpcenter.dto.AdoptionApplicationDto;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static SessionFactory factory;

    public DatabaseManager() {
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

    public void saveObject(Object item){
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

    public void updateObject(Object item){ // important that item has id
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

    public void deleteObject(Object item){
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

    public Object getObject(Class c, int id) {
        var session = factory.openSession();

        try {
            return session.get(c, id);
        } catch (HibernateException ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
        return null;
    }



    public List<Cat> getCats() {
        var session = factory.openSession();

        try {
            return session.createQuery("FROM Cat").list();
        } catch (HibernateException ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    public List<AdoptionApplication> getApplications() {
        var session = factory.openSession(); // opening channel to communicate with database

        try {
            return session.createQuery("FROM AdoptionApplication ").list(); // from employee ENTITY!
        } catch (HibernateException ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    public List<Volunteer> getVolunteers() {
        var session = factory.openSession();

        try {
            return session.createQuery("FROM Volunteer").list();
        } catch (HibernateException ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    public List<TempHome> getTempHomes() {
        var session = factory.openSession();

        try {
            return session.createQuery("FROM TempHome ").list();
        } catch (HibernateException ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }
}
