package samatov.jdbcProject.repository.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import samatov.jdbcProject.exception.WriterException;
import samatov.jdbcProject.model.Label;
import samatov.jdbcProject.model.Post;
import samatov.jdbcProject.model.Writer;
import samatov.jdbcProject.repository.WriterRepository;
import samatov.jdbcProject.utils.HibernateUtil;

import java.util.List;
import java.util.Set;

public class WriterRepositoryImpl implements WriterRepository {

    @Override
    public List<Writer> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT DISTINCT w FROM Writer w LEFT JOIN FETCH w.posts p LEFT JOIN FETCH p.labels";
            Query<Writer> query = session.createQuery(hql, Writer.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new WriterException("Ошибка запроса, писатели не найдены");
        }
    }

    @Override
    public Writer findById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT w FROM Writer w LEFT JOIN FETCH w.posts p LEFT JOIN FETCH p.labels WHERE w.id = :id";
            Query<Writer> query = session.createQuery(hql, Writer.class);
            query.setParameter("id", id);
            Writer writer = query.uniqueResult();
            if (writer == null) {
                throw new WriterException("Писатель с указанным id не найден");
            }
            return writer;
        } catch (Exception e) {
            e.printStackTrace();
            throw new WriterException("Ошибка запроса, проблема с синтаксисом");
        }
    }

    @Override
    public void removeById(Integer id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Writer writer = session.get(Writer.class, id);
            if (writer == null) {
                throw new WriterException("Ошибка запроса, писатель с указанным id:=" + id + " не найден");
            }
            session.delete(writer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new WriterException("Ошибка запроса, писатель с указанным id:=" + id + " не удален");
        }
    }

    @Override
    public Writer save(Writer writer) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(writer);
            transaction.commit();
            return writer;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new WriterException("Ошибка запроса, писателя не удалось сохранить");
        }
    }

    @Override
    public Writer update(Writer writer) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(writer);
            transaction.commit();
            return writer;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new WriterException("Ошибка запроса, писателя не удалось изменить");
        }
    }
    @Override
    public void addPostWithLabelsToWriter(Integer writerId, Post post, Set<Label> labels) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Writer writer = session.get(Writer.class, writerId);
            if (writer == null) {
                throw new WriterException("Писатель с указанным id:=" + writerId + " не найден");
            }

            post.setLabels(labels);

            post.setWriter(writer);

            writer.getPosts().add(post);

            session.saveOrUpdate(writer);
            session.saveOrUpdate(post);

            for (Label label : labels) {
                session.saveOrUpdate(label);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new WriterException("Ошибка при добавлении поста с метками к писателю");
        }
    }


    @Override
    public void removePostsByWriterId(Integer writerId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query<?> query = session.createQuery("DELETE FROM Post WHERE writer.id = :writerId");
            query.setParameter("writerId", writerId);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new WriterException("Ошибка при удалении постов писателя");
        }
    }
}
