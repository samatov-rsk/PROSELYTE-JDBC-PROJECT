package samatov.jdbcProject.constanst;

public class LabelConstants {
    public static final String GET_ALL_LABEL = "SELECT * FROM label";
    public static final String GET_BY_ID_LABEL = "SELECT * FROM label WHERE id = ?";
    public static final String INSERT_LABEL = "INSERT INTO label (name) VALUES (?)";
    public static final String UPDATE_LABEL = "UPDATE label SET name = ? WHERE id = ?";
    public static final String DELETE_BY_ID_LABEL = "DELETE FROM label WHERE id = ?;";
}
