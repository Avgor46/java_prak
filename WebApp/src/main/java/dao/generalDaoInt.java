package dao;

import java.util.List;
import java.util.Map;

public interface generalDaoInt<T, K> {
    public T getByID(long id);
    public List<T> getAll();
    public <V> List<T> search(Map<String, V> params);
    public boolean insert(T entity);
    public boolean update(T entity);
    public boolean delete(T entity);
}
