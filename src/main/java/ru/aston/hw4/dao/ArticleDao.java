package ru.aston.hw4.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.aston.hw4.entity.Article;
import ru.aston.hw4.entity.Author;
import ru.aston.hw4.utils.HibernateSessionFactoryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ArticleDao implements Dao<Article> {

    @Override
    public Optional<Article> get(long articleId) {
        Transaction transaction = null;
        Article article = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            article = session.createQuery("SELECT a " +
                    "FROM Article a " +
                    "JOIN fetch Author at " +
                    "WHERE a.id = ?" ,Article.class).setParameter(1, articleId).getSingleResult();
            List<Author> authors = article.getAuthors();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return Optional.ofNullable(article);
    }

    public List<Article> getArticlesByAuthor(Author author) {
        Transaction transaction = null;
        List<Article> articles = new ArrayList<>();
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Article> criteriaQuery = criteriaBuilder.createQuery(Article.class);
            Root<Article> bookRoot = criteriaQuery.from(Article.class);
            Join<Article, Author> authorJoin = bookRoot.join("authors");
            criteriaQuery.where(criteriaBuilder.equal(authorJoin.get("id"), author.getId()));
            articles = session.createQuery(criteriaQuery).getResultList();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    public List<Article> getAll() {
        Transaction transaction = null;
        List<Article> articles = new ArrayList<>();
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            articles = session.createQuery("SELECT a " +
                            "FROM Article a " +
                            "JOIN FETCH a.authors ", Article.class)
                    .getResultList();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    public void save(Article article) {
        Transaction transaction = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Queue<Author> authors = new ConcurrentLinkedQueue<>(article.getAuthors());
            for (Author author : authors) {
                session.refresh(author);
                author.addWork(article);
            }
            session.persist(article);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void update(Article article) {
        Transaction transaction = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Article currentArticle = session.get(Article.class, article.getId());
            Queue<Author> dbAuthors = new ConcurrentLinkedQueue<>(currentArticle.getAuthors());
            Queue<Author> authors = new ConcurrentLinkedQueue<>(article.getAuthors());
            for (Author author : dbAuthors) {
                if (!article.getAuthors().contains(author)) {
                    author.removeWork(article);
                }
            }
            for (Author author : authors) {
                session.refresh(author);
                author.addWork(article);
            }
            session.merge(article);
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
            Article article = session.find(Article.class, id);
            if (article != null) {
                Queue<Author> authors = new ConcurrentLinkedQueue<>(article.getAuthors());
                for (Author author : authors) {
                    session.refresh(author);
                    author.removeWork(article);
                }
                session.remove(article);
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
