package ru.aston.hw4.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.aston.hw4.entity.Genre;
import ru.aston.hw4.utils.HibernateSessionFactoryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GenreDao implements Dao<Genre> {

    @Override
    public Optional<Genre> get(long id) {
        Transaction transaction = null;
        Genre genre = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            genre = session.get(Genre.class, id);
            transaction.commit();
            return Optional.of(genre);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return Optional.ofNullable(genre);
    }

    @Override
    public List<Genre> getAll() {
        Transaction transaction = null;
        List<Genre> genres = new ArrayList<>();
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Genre> criteria = cb.createQuery(Genre.class);
            criteria.from(Genre.class);
            genres = session.createQuery(criteria).getResultList();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return genres;
    }

    @Override
    public void save(Genre genre) {
        Transaction transaction = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(genre);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void update(Genre genre) {
        Transaction transaction = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(genre);
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
            Genre genre = session.find(Genre.class, id);
            session.remove(genre);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
