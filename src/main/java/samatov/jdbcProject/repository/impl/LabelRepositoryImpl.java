package samatov.jdbcProject.repository.impl;

import samatov.jdbcProject.exception.LabelException;
import samatov.jdbcProject.exception.NotFoundException;
import samatov.jdbcProject.mapper.LabelMapper;
import samatov.jdbcProject.model.Label;
import samatov.jdbcProject.repository.LabelRepository;
import samatov.jdbcProject.utils.DbConfig;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static samatov.jdbcProject.constanst.LabelConstants.*;

public class LabelRepositoryImpl implements LabelRepository {

    @Override
    public List<Label> findAll() {
        List<Label> labels = new ArrayList<>();
        try (PreparedStatement statement = DbConfig.getPreparedStatement(GET_ALL_LABEL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Label label = LabelMapper.mapperToLabel(resultSet);
                labels.add(label);
            }
        } catch (SQLException e) {
            throw new LabelException("Ошибка запроса, метки не найдены...");
        }
        return labels;
    }

    @Override
    public Label findById(Integer id) {
        Label label = null;
        try (PreparedStatement statement = DbConfig.getPreparedStatement(GET_BY_ID_LABEL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                label = LabelMapper.mapperToLabel(resultSet);
            } else {
                throw new NotFoundException("Метка с указаным id:=" + id + " не найдена...");
            }
        } catch (SQLException e) {
            throw new LabelException("Ошибка запроса, метка с указаным id:=" + id + " не найдена...");
        }
        return label;
    }

    @Override
    public void removeById(Integer id) {
        try (PreparedStatement statement = DbConfig.getPreparedStatement(DELETE_BY_ID_LABEL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new LabelException("Ошибка запроса, метка с указаным id:=" + id + " не удалена...");
        }
    }

    @Override
    public Label save(Label label) {
        try (PreparedStatement statement = DbConfig.getPreparedStatementWithGeneratedKeys(INSERT_LABEL)) {
            statement.setString(1, label.getName());
            int countRows = statement.executeUpdate();
            if (countRows == 0) {
                throw new LabelException("Ошибка запроса, метку не удалось сохранить...");
            }
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    label.setId(resultSet.getInt(1));
                } else {
                    throw new NotFoundException("Ошибка запроса, метку не удалось сохранить, ID не найден");
                }

            }
        } catch (SQLException e) {
            throw new LabelException("Ошибка запроса, метку не удалось сохранить...");
        }
        return label;
    }

    @Override
    public Label update(Label label) {
        try (PreparedStatement statement = DbConfig.getPreparedStatement(UPDATE_LABEL)) {
            statement.setString(1, label.getName());
            statement.setInt(2, label.getId());
            statement.executeUpdate();
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new NotFoundException("Ошибка запроса, метка с указаным id:=" + label.getId() + " не найдена...");
            }
            return label;
        } catch (SQLException e) {
            throw new LabelException("Ошибка запроса, метку не удалось изменить...");
        }
    }
}
