package samatov.jdbcProject.repository.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import samatov.jdbcProject.exception.LabelException;
import samatov.jdbcProject.exception.NotFoundException;
import samatov.jdbcProject.model.Label;
import samatov.jdbcProject.repository.LabelRepository;
import samatov.jdbcProject.utils.HibernateUtil;

import java.util.List;

public class LabelRepositoryImpl implements LabelRepository {

    @Override
    public List<Label> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Label> query = session.createQuery("FROM Label", Label.class);
            return query.list();
        } catch (Exception e) {
            throw new LabelException("Ошибка запроса, метки не найдены...");
        }
    }

    @Override
    public Label findById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Label label = session.get(Label.class, id);
            if (label == null) {
                throw new NotFoundException("Метка с указаным id:=" + id + " не найдена...");
            }
            return label;
        } catch (Exception e) {
            throw new LabelException("Ошибка запроса, метка с указаным id:=" + id + " не найдена...");
        }
    }

    @Override
    public void removeById(Integer id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Label label = session.get(Label.class, id);
            if (label == null) {
                throw new NotFoundException("Метка с указаным id:=" + id + " не найдена...");
            }
            session.delete(label);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new LabelException("Ошибка запроса, метка с указаным id:=" + id + " не удалена...");
        }
    }

    @Override
    public Label save(Label label) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(label);
            transaction.commit();
            return label;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new LabelException("Ошибка запроса, метку не удалось сохранить...");
        }
    }

    @Override
    public Label update(Label label) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(label);
            transaction.commit();
            return label;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new LabelException("Ошибка запроса, метку не удалось изменить...");
        }
    }
}
