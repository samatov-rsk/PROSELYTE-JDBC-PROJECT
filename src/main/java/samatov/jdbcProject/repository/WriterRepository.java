package samatov.jdbcProject.repository;

import samatov.jdbcProject.model.Post;
import samatov.jdbcProject.model.Writer;

import java.util.List;

public interface WriterRepository extends GenericRepository<Writer, Integer> {

    void removePostsByWriterId(Integer writerId);
}
