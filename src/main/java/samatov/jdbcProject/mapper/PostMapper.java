package samatov.jdbcProject.mapper;

import samatov.jdbcProject.enums.PostStatus;
import samatov.jdbcProject.model.Label;
import samatov.jdbcProject.model.Post;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostMapper {

    public static List<Post> mapPostsFromResultSet(ResultSet resultSet) throws SQLException {
        List<Post> posts = new ArrayList<>();
        Post lastPost = null;

        while (resultSet.next()) {
            int currentPostId = resultSet.getInt("post_id");
            if (lastPost == null || lastPost.getId() != currentPostId) {
                lastPost = new Post();
                lastPost.setId(currentPostId);
                lastPost.setContent(resultSet.getString("content"));
                lastPost.setCreated(resultSet.getTimestamp("created"));
                lastPost.setUpdated(resultSet.getTimestamp("updated"));
                lastPost.setStatus(PostStatus.valueOf(resultSet.getString("status")));
                lastPost.setLabels(new ArrayList<>());

                posts.add(lastPost);
            }

            int labelId = resultSet.getInt("label_id");
            if (!resultSet.wasNull()) {
                Label label = new Label();
                label.setId(labelId);
                label.setName(resultSet.getString("name"));
                lastPost.getLabels().add(label);
            }
        }
        return posts;
    }

    public static Post mapPostFromResultSet(ResultSet resultSet) throws SQLException {
        List<Post> posts = mapPostsFromResultSet(resultSet);
        return posts.isEmpty() ? null : posts.get(0);
    }
}
