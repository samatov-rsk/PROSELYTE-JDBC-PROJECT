package samatov.jdbcProject.constanst;
public class WriterConstants {

    public static final String GET_ALL_WRITER = "SELECT w.id as writer_id, w.firstName, w.lastName, " +
            "p.id as post_id, p.content, p.created, p.updated, p.status, " +
            "l.id as label_id, l.name " +
            "FROM writer w " +
            "LEFT JOIN post p ON w.id = p.writer_id " +
            "LEFT JOIN post_labels pl ON p.id = pl.post_id " +
            "LEFT JOIN label l ON l.id = pl.label_id";

    public static final String GET_BY_ID_WRITER = "SELECT w.id as writer_id, w.firstName, w.lastName, " +
            "p.id as post_id, p.content, p.created, p.updated, p.status, " +
            "l.id as label_id, l.name " +
            "FROM writer w " +
            "LEFT JOIN post p ON w.id = p.writer_id " +
            "LEFT JOIN post_labels pl ON p.id = pl.post_id " +
            "LEFT JOIN label l ON l.id = pl.label_id " +
            "WHERE w.id = ?";

    public static final String INSERT_WRITER = "INSERT INTO writer (firstName, lastName) VALUES (?,?)";
    public static final String UPDATE_WRITER = "UPDATE writer SET firstName = ?, lastName = ? WHERE id = ?";
    public static final String DELETE_BY_ID_WRITER = "DELETE FROM writer WHERE id = ?";
    public static final String DELETE_POSTS_BY_WRITER_ID = "DELETE FROM post WHERE writer_id = ?";
}
