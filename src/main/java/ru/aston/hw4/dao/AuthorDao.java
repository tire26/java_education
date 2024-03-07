package ru.aston.hw4.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.aston.hw4.entity.Article;
import ru.aston.hw4.entity.Author;
import ru.aston.hw4.entity.Book;
import ru.aston.hw4.entity.IntellectualWork;
import ru.aston.hw4.factory.DaoFactory;
import ru.aston.hw4.utils.HibernateSessionFactoryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AuthorDao implements Dao<Author> {

    Dao<Book> bookDao = DaoFactory.getInstance().getBookDao();

    @Override
    public Optional<Author> get(long id) {

        Transaction transaction = null;
        Author author = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            author = session.createQuery("SELECT a " +
                    "FROM Author a " +
                    "JOIN FETCH a.works " +
                    "WHERE a.id = ?", Author.class).setParameter(1, id).getSingleResult();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return Optional.ofNullable(author);
    }




    @Override
    public List<Author> getAll() {

        Transaction transaction = null;
        List<Author> authors = new ArrayList<>();
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            authors  = session.createQuery("SELECT a " +
                            "FROM Author a " +
                            "JOIN FETCH a.works ", Author.class)
                    .getResultList();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return authors;
    }

    @Override
    public void save(Author author) {
        Transaction transaction = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Queue<IntellectualWork> books = new ConcurrentLinkedQueue<>(author.getWorks());
            for (IntellectualWork intellectualWork : books) {
                session.refresh(intellectualWork);
                author.addWork(intellectualWork);
            }
            session.persist(author);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void update(Author author) {
        Transaction transaction = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(author);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        Transaction transaction = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Author author = session.find(Author.class, id);
            session.remove(author);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
