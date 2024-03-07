package ru.aston.hw4.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.aston.hw4.entity.Author;
import ru.aston.hw4.entity.Book;
import ru.aston.hw4.entity.Genre;
import ru.aston.hw4.utils.HibernateSessionFactoryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BookDao implements Dao<Book> {

    @Override
    public Optional<Book> get(long bookId) {
        Transaction transaction = null;
        Book book = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            book = session.get(Book.class, bookId);
            List<Author> authors = book.getAuthors();
            Genre genre = book.getGenre();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return Optional.ofNullable(book);
    }

    public List<Book> getBooksByAuthor(Author author) {
        Transaction transaction = null;
        List<Book> books = new ArrayList<>();
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
            Root<Book> bookRoot = criteriaQuery.from(Book.class);
            Join<Book, Author> authorJoin = bookRoot.join("authors");
            criteriaQuery.where(criteriaBuilder.equal(authorJoin.get("id"), author.getId()));
            books = session.createQuery(criteriaQuery).getResultList();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> getAll() {
        Transaction transaction = null;
        List<Book> books = new ArrayList<>();
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            books = session.createQuery("SELECT b " +
                            "FROM Book b " +
                            "JOIN FETCH b.genre " +
                            "JOIN FETCH b.authors ", Book.class)
                    .getResultList();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public void save(Book book) {
        Transaction transaction = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Queue<Author> authors = new ConcurrentLinkedQueue<>(book.getAuthors());
            for (Author author : authors) {
                session.refresh(author);
                author.addWork(book);
            }
            session.persist(book);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void update(Book book) {
        Transaction transaction = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Book currentBook = session.get(Book.class, book.getId());
            Queue<Author> dbAuthors = new ConcurrentLinkedQueue<>(currentBook.getAuthors());
            Queue<Author> authors = new ConcurrentLinkedQueue<>(book.getAuthors());
            for (Author author : dbAuthors) {
                if (!book.getAuthors().contains(author)) {
                    author.removeWork(book);
                }
            }
            for (Author author : authors) {
                session.refresh(author);
                author.addWork(book);
            }
            session.merge(book);
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
            Book book = session.find(Book.class, id);
            if (book != null) {
                Queue<Author> authors = new ConcurrentLinkedQueue<>(book.getAuthors());
                for (Author author : authors) {
                    session.refresh(author);
                    author.removeWork(book);
                }
                session.remove(book);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
