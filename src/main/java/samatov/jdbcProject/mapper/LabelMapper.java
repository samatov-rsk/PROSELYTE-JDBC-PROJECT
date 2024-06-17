package samatov.jdbcProject.mapper;

import samatov.jdbcProject.model.Label;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LabelMapper {

    public static Label mapperToLabel(ResultSet resultSet) throws SQLException {
        return Label.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .build();
    }
}
