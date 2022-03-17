package dao;

import dao.Impl.stationsDao;
import entity.stations;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class StationsDaoTest {
    @Test
    public void getStationByIdTest() {
        long id = 1;
        stationsDao dao = new stationsDao();
        stations station = dao.getByID(id);
        Assert.assertEquals(station.getName(), "Helheim");
    }

    @Test
    public void getAllTest() {
        long id = 1;
        stationsDao dao = new stationsDao();
        List<stations> all = dao.getAll();

        Assert.assertTrue(all.size() != 0);
    }
}
