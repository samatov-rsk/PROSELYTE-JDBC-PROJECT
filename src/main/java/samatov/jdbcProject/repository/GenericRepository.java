package samatov.jdbcProject.repository;

import java.util.List;

public interface GenericRepository<T, ID> {

    List<T> findAll();

    T findById(Integer id);

    void removeById(Integer id);

    T save(T object);

    T update(T object);

}
