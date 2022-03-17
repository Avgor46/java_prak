package dao;

import dao.Impl.stations_on_tripDao;
import entity.stations_on_trip;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.List;

public class StationsontripDaoTest {
    @Test
    public void getOrderByIdTest() {
        long id = 1;
        stations_on_tripDao dao = new stations_on_tripDao();
        stations_on_trip order = dao.getByID(id);
        Assert.assertEquals(order.getDate(), java.sql.Timestamp.valueOf("2022-01-01 00:00:00"));
    }

    @Test
    public void getAllTest() {
        stations_on_tripDao dao = new stations_on_tripDao();
        List<stations_on_trip> all = dao.getAll();

        Assert.assertTrue(all.size() != 0);
    }

    @Test
    public void searchByDateTest() {
        stations_on_tripDao dao = new stations_on_tripDao();
        List<stations_on_trip> all = dao.searchByDate(Timestamp.valueOf("2022-01-01 00:00:00"), Timestamp.valueOf("2022-01-01 00:00:00"));
        Assert.assertTrue(all.size() != 0);
        all = dao.searchByDate(Timestamp.valueOf("2021-01-01 00:00:00"), Timestamp.valueOf("2021-12-31 00:00:00"));
        Assert.assertFalse(all.size() != 0);
    }
}
