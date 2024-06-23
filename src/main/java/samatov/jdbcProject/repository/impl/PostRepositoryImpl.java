package samatov.jdbcProject.repository.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import samatov.jdbcProject.exception.NotFoundException;
import samatov.jdbcProject.exception.PostException;
import samatov.jdbcProject.model.Label;
import samatov.jdbcProject.model.Post;
import samatov.jdbcProject.repository.PostRepository;
import samatov.jdbcProject.utils.HibernateUtil;

import java.util.List;

public class PostRepositoryImpl implements PostRepository {

    @Override
    public List<Post> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.labels";
            Query<Post> query = session.createQuery(hql, Post.class);
            return query.list();
        } catch (Exception e) {
            throw new PostException("Ошибка запроса, посты не найдены");
        }
    }

    @Override
    public Post findById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT p FROM Post p LEFT JOIN FETCH p.labels WHERE p.id = :id";
            Query<Post> query = session.createQuery(hql, Post.class);
            query.setParameter("id", id);
            Post post = query.uniqueResult();
            if (post == null) {
                throw new NotFoundException("Ошибка запроса, пост с указанным id:=" + id + " не найден...");
            }
            return post;
        } catch (Exception e) {
            throw new PostException("Ошибка запроса, пост с указанным id:=" + id + " не найден...");
        }
    }

    @Override
    public void removeById(Integer id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Post post = session.get(Post.class, id);
            if (post == null) {
                throw new NotFoundException("Ошибка запроса, пост с указаным id:=" + id + " не найден...");
            }
            session.delete(post);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new PostException("Ошибка запроса, пост с указаным id:=" + id + " не удален...");
        }
    }

    @Override
    public Post save(Post post) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(post);
            transaction.commit();
            return post;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new PostException("Ошибка запроса, пост не удалось сохранить...");
        }
    }

    @Override
    public Post update(Post post) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(post);
            transaction.commit();
            return post;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new PostException("Ошибка запроса, пост не удалось изменить...");
        }
    }

    @Override
    public void addLabelToPost(Integer postId, Label label) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Post post = session.get(Post.class, postId);
            if (post == null) {
                throw new NotFoundException("Ошибка запроса, пост с указанным id:=" + postId + " не найден...");
            }
            post.getLabels().add(label);
            session.update(post);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new PostException("Ошибка добавления метки к посту...");
        }
    }

    @Override
    public void removeLabelFromPost(Integer postId, Integer labelId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Post post = session.get(Post.class, postId);
            if (post == null) {
                throw new NotFoundException("Ошибка запроса, пост с указанным id:=" + postId + " не найден...");
            }
            Label label = session.get(Label.class, labelId);
            post.getLabels().remove(label);
            session.update(post);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new PostException("Ошибка удаления метки с поста...");
        }
    }
}
