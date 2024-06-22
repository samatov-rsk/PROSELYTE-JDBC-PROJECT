package samatov.jdbcProject.repository;

import samatov.jdbcProject.model.Label;
import samatov.jdbcProject.model.Post;
import samatov.jdbcProject.model.Writer;

import java.util.Set;

public interface WriterRepository extends GenericRepository<Writer, Integer> {

   void addPostWithLabelsToWriter(Integer writerId, Post post, Set<Label> labels);

    void removePostsByWriterId(Integer writerId);
}
