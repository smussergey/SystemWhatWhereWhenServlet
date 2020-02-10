package ua.training.game.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> extends AutoCloseable{
    void create(T entity);

    Optional<T> findById(int id);
    List<T> findAll();

    void update(T entity);

    void delete(int id);
}
