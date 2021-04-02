package com.example.Animalhelpcenter.data;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


public class AdminRepository {
    private static SessionFactory factory;

    public AdminRepository() {
        try {
            factory = new Configuration()
                    .configure()
                    .addAnnotatedClass(Admin.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public Admin login(String login, String password) {
        var session = factory.openSession();

        try {
            String hql = "FROM Admin U WHERE U.login = :login and U.password = MD5(:pwd)";
            Query query = session.createQuery(hql);

            query.setParameter("login",login);
            query.setParameter("pwd",password);

            var results = query.list();

            if(results.size() > 0) {
                return (Admin) results.get(0);
            }
        } catch (HibernateException ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }

        return null;
    }

}
