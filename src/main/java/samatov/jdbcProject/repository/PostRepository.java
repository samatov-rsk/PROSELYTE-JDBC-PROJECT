package samatov.jdbcProject.repository;


import samatov.jdbcProject.model.Label;
import samatov.jdbcProject.model.Post;

public interface PostRepository extends GenericRepository<Post, Integer> {

    void addLabelToPost(Integer postId, Label label);
    void removeLabelFromPost(Integer postId, Integer labelId);

    }
