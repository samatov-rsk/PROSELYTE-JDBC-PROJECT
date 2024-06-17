package samatov.jdbcProject.repository.impl;

import samatov.jdbcProject.exception.PostException;
import samatov.jdbcProject.mapper.PostMapper;
import samatov.jdbcProject.model.Label;
import samatov.jdbcProject.model.Post;
import samatov.jdbcProject.repository.PostRepository;
import samatov.jdbcProject.utils.DbConfig;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static samatov.jdbcProject.constanst.PostConstanst.*;

public class PostRepositoryImpl implements PostRepository {

    @Override
    public List<Post> findAll() {
        List<Post> posts;
        try (PreparedStatement statement = DbConfig.getPreparedStatement(GET_ALL_POST)) {
            ResultSet resultSet = statement.executeQuery();
            posts = PostMapper.mapPostsFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new PostException("Ошибка запроса, посты не найдены...");
        }
        return posts;
    }

    @Override
    public Post findById(Integer id) {
        Post post = null;
        try (PreparedStatement statement = DbConfig.getPreparedStatement(GET_BY_ID_POST)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            post = PostMapper.mapPostFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new PostException("Ошибка запроса, пост с указанным id:=" + id + " не найден...");
        }
        return post;
    }


    @Override
    public void removeById(Integer id) {
        try (PreparedStatement statement = DbConfig.getPreparedStatement(DELETE_BY_ID_POST)) {
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new PostException("Ошибка запроса, пост с указаным id:=" + id + " не найден...");
            }
        } catch (SQLException e) {
            throw new PostException("Ошибка запроса, пост с указаным id:=" + id + " не удален...");
        }
    }

    @Override
    public Post save(Post post) {
        try (PreparedStatement statement = DbConfig.getPreparedStatementWithGeneratedKeys(INSERT_POST)) {
            statement.setInt(1, post.getWriter().getId());
            statement.setString(2, post.getContent());
            statement.setTimestamp(3, post.getCreated());
            statement.setTimestamp(4, post.getUpdated());
            statement.setString(5, post.getStatus().name());
            int countRows = statement.executeUpdate();
            if (countRows == 0) {
                throw new PostException("Ошибка запроса, пост не удалось сохранить...");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    post.setId(generatedKeys.getInt(1));
                } else {
                    throw new PostException("Ошибка запроса, пост не удалось сохранить, ID не найден");
                }
            }
        } catch (SQLException e) {
            throw new PostException("Ошибка запроса, пост не удалось сохранить...");
        }
        return post;
    }

    @Override
    public Post update(Post post) {
        try (PreparedStatement statement = DbConfig.getPreparedStatement(UPDATE_POST)) {
            statement.setString(1, post.getContent());
            statement.setTimestamp(2, post.getCreated());
            statement.setTimestamp(3, post.getUpdated());
            statement.setString(4, post.getStatus().name());
            statement.setInt(5, post.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new PostException("Ошибка запроса, пост с указаным id:=" + post.getId() + " не найден...");
            }
            return post;
        } catch (SQLException e) {
            throw new PostException("Ошибка запроса, пост не удалось изменить...");
        }
    }

    @Override
    public void addLabelToPost(Integer postId, Label label) {
        try (PreparedStatement statement = DbConfig.getPreparedStatement(ADD_LABEL_TO_POST)) {
            statement.setInt(1, postId);
            statement.setInt(2, label.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PostException("Ошибка добавления метки к посту...");
        }
    }

    @Override
    public void removeLabelFromPost(Integer postId, Integer labelId) {
        try (PreparedStatement statement = DbConfig.getPreparedStatement(REMOVE_LABEL_FROM_POST)) {
            statement.setInt(1, postId);
            statement.setInt(2, labelId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PostException("Ошибка удаления метки с поста...");
        }
    }
}
