package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.*;
import org.hibernate.query.Query;

import javax.persistence.EntityManagerFactory;
import javax.swing.*;

import static jm.task.core.jdbc.util.Util.getSessionFactory;
import static jm.task.core.jdbc.util.Util.sessionFactory;


public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    private Session session = getSessionFactory().openSession();

    @Override
    public void createUsersTable() {
        try {
            session.getEntityManagerFactory();
            Transaction transaction = session.getTransaction();
            transaction.begin();

            String sql = """
                    CREATE TABLE IF NOT EXISTS users (
                      id INT NOT NULL AUTO_INCREMENT,
                      name VARCHAR(45) NOT NULL,
                      lastName VARCHAR(45) NOT NULL,
                      age INT NOT NULL,
                      PRIMARY KEY (id))
                    ENGINE = InnoDB
                    DEFAULT CHARACTER SET = utf8;""";

            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            transaction.commit();

        } catch (HibernateException e) {
            throw new RuntimeException(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }

    @Override //OK
    public void dropUsersTable() {
        try {
            Session session = getSessionFactory().openSession();
            session.getEntityManagerFactory();
            Transaction transaction = session.beginTransaction();

            String sql = "DROP TABLE IF EXISTS users";

            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();

        } catch (HibernateException e) {
            throw new RuntimeException(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        Transaction transaction = null;

        try {
            session = getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("User с именем — " + name + " " + lastName + " добавлен в базу данных");
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {

        Transaction transaction = null;
        try {
            session = getSessionFactory().openSession();
            transaction = session.beginTransaction();

            String hql = "DELETE User WHERE id = :id";

            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<User> getAllUsers() {

        try {
            session = getSessionFactory().openSession();

        } catch (Exception e) {
            System.out.println("GETTING ALL USERS... FAIL!");
        }

        return session.createQuery("from User ", User.class).list();

    }

    @Override
    public void cleanUsersTable() {

        try {
            session = getSessionFactory().openSession();
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }
}
