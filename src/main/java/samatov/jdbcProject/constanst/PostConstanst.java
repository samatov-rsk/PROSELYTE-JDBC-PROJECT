package samatov.jdbcProject.constanst;

public class PostConstanst {
    public static final String GET_ALL_POST = "SELECT p.id as post_id, p.content, p.created, p.updated, p.status, " +
            "l.id as label_id, l.name " +
            "FROM post p " +
            "LEFT JOIN post_labels pl ON p.id = pl.post_id " +
            "LEFT JOIN label l ON l.id = pl.label_id";
    public static final String GET_BY_ID_POST = "SELECT p.id as post_id, p.content, p.created, p.updated, p.status, " +
            "l.id as label_id, l.name " +
            "FROM post p " +
            "LEFT JOIN post_labels pl ON p.id = pl.post_id " +
            "LEFT JOIN label l ON l.id = pl.label_id " +
            "WHERE p.id = ?";
    public static final String INSERT_POST = "INSERT INTO post (writer_id, content, created, updated, status) VALUES (?, ?, ?, ?, ?)";
    public static final String UPDATE_POST = "UPDATE post SET content = ?, created = ?, updated = ?, status = ? WHERE id = ?";
    public static final String DELETE_BY_ID_POST = "DELETE FROM post WHERE id = ?";
    public static final String ADD_LABEL_TO_POST = "INSERT INTO post_labels (post_id, label_id) VALUES (?, ?)";
    public static final String REMOVE_LABEL_FROM_POST = "DELETE FROM post_labels WHERE post_id = ? AND label_id = ?";
}
