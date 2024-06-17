package samatov.jdbcProject.repository;

import samatov.jdbcProject.model.Writer;

public interface WriterRepository extends GenericRepository<Writer, Integer> {

    void removePostsByWriterId(Integer writerId);
}
