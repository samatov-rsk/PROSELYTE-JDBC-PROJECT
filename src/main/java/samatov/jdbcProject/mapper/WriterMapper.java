package samatov.jdbcProject.mapper;

import samatov.jdbcProject.enums.PostStatus;
import samatov.jdbcProject.model.Label;
import samatov.jdbcProject.model.Writer;
import samatov.jdbcProject.model.Post;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WriterMapper {

    public static List<Writer> mapWritersFromResultSet(ResultSet resultSet) throws SQLException {
        List<Writer> writers = new ArrayList<>();
        Writer lastWriter = null;
        Post lastPost = null;

        while (resultSet.next()) {
            int currentWriterId = resultSet.getInt("writer_id");
            if (lastWriter == null || lastWriter.getId() != currentWriterId) {
                lastWriter = new Writer();
                lastWriter.setId(currentWriterId);
                lastWriter.setFirstName(resultSet.getString("firstName"));
                lastWriter.setLastName(resultSet.getString("lastName"));
                lastWriter.setPosts(new ArrayList<>());
                writers.add(lastWriter);
                lastPost = null;
            }

            int postId = resultSet.getInt("post_id");
            if (!resultSet.wasNull()) {
                if (lastPost == null || lastPost.getId() != postId) {
                    lastPost = new Post();
                    lastPost.setId(postId);
                    lastPost.setContent(resultSet.getString("content"));
                    lastPost.setCreated(resultSet.getTimestamp("created"));
                    lastPost.setUpdated(resultSet.getTimestamp("updated"));
                    lastPost.setStatus(PostStatus.valueOf(resultSet.getString("status")));
                    lastPost.setLabels(new ArrayList<>());
                    lastWriter.getPosts().add(lastPost);
                }

                int labelId = resultSet.getInt("label_id");
                if (!resultSet.wasNull()) {
                    Label label = new Label();
                    label.setId(labelId);
                    label.setName(resultSet.getString("name"));
                    lastPost.getLabels().add(label);
                }
            }
        }
        return writers;
    }
}
