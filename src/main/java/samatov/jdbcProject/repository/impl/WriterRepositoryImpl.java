package samatov.jdbcProject.repository.impl;

import samatov.jdbcProject.exception.NotFoundException;
import samatov.jdbcProject.exception.WriterException;
import samatov.jdbcProject.mapper.WriterMapper;
import samatov.jdbcProject.model.Writer;
import samatov.jdbcProject.repository.WriterRepository;
import samatov.jdbcProject.utils.DbConfig;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static samatov.jdbcProject.constanst.WriterConstants.*;

public class WriterRepositoryImpl implements WriterRepository {

    @Override
    public List<Writer> findAll() {
        List<Writer> writers;
        try (PreparedStatement statement = DbConfig.getPreparedStatement(GET_ALL_WRITER)) {
            ResultSet resultSet = statement.executeQuery();
            writers = WriterMapper.mapWritersFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new WriterException("Ошибка запроса, писатели не найдены");
        }
        return writers;
    }

    @Override
    public Writer findById(Integer id) {
        Writer writer;
        try (PreparedStatement statement = DbConfig.getPreparedStatement(GET_BY_ID_WRITER)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            writer = WriterMapper.mapWritersFromResultSet(resultSet).stream().findFirst().orElseThrow(() -> new NotFoundException("Писатель с указанным id не найден"));
        } catch (SQLException e) {
            throw new WriterException("Ошибка запроса, проблема с синтаксисом");
        }
        return writer;
    }

    @Override
    public void removeById(Integer id) {
        try (PreparedStatement statement = DbConfig.getPreparedStatement(DELETE_BY_ID_WRITER)) {
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new NotFoundException("Ошибка запроса, писатель с указанным id:=" + id + " не найден");
            }
        } catch (SQLException e) {
            throw new WriterException("Ошибка запроса, писатель с указанным id:=" + id + " не удален");
        }
    }

    @Override
    public Writer save(Writer writer) {
        try (PreparedStatement statement = DbConfig.getPreparedStatementWithGeneratedKeys(INSERT_WRITER)) {
            statement.setString(1, writer.getFirstName());
            statement.setString(2, writer.getLastName());
            int countRows = statement.executeUpdate();
            if (countRows == 0) {
                throw new WriterException("Ошибка запроса, писателя не удалось сохранить");
            }
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    writer.setId(resultSet.getInt(1));
                } else {
                    throw new NotFoundException("Ошибка запроса, писателя не удалось сохранить, ID не найден");
                }
            }
        } catch (SQLException e) {
            throw new WriterException("Ошибка запроса, писателя не удалось сохранить");
        }
        return writer;
    }

    @Override
    public Writer update(Writer writer) {
        try (PreparedStatement statement = DbConfig.getPreparedStatement(UPDATE_WRITER)) {
            statement.setString(1, writer.getFirstName());
            statement.setString(2, writer.getLastName());
            statement.setInt(3, writer.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new NotFoundException("Ошибка запроса, писатель с указанным id:=" + writer.getId() + " не найден");
            }
            return writer;
        } catch (SQLException e) {
            throw new WriterException("Ошибка запроса, писателя не удалось изменить");
        }
    }

    @Override
    public void removePostsByWriterId(Integer writerId) {
        try (PreparedStatement statement = DbConfig.getPreparedStatement(DELETE_POSTS_BY_WRITER_ID)) {
            statement.setInt(1, writerId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new WriterException("Ошибка при удалении постов писателя");
        }
    }
}
