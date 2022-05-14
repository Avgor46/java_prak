package MyWebApp.dao;

import java.sql.Timestamp;
import java.util.List;

public interface stations_on_tripDaoInt<T, K> extends generalDaoInt<T, K> {
    public List<T> searchByDate(Timestamp d1, Timestamp d2);
    public List<T> getAllByTripId(Long id);
}