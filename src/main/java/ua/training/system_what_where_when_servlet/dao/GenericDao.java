package ua.training.system_what_where_when_servlet.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> extends AutoCloseable {
    void create(T entity);

    Optional<T> findById(int id);
    List<T> findAll();

    void update(T entity);

    void delete(int id);
}
