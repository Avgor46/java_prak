package dao;

import dao.Impl.stationsDao;
import dao.Impl.stations_on_tripDao;
import dao.Impl.tripsDao;
import entity.stations;
import entity.stations_on_trip;
import entity.trips;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneralDaoTest {
    @Test
    public void searchTest() {
        Map<String, Timestamp> testtime = new HashMap<>();
        testtime.put("date", Timestamp.valueOf("2022-01-01 00:00:00"));
        stations_on_tripDao stations = new stations_on_tripDao();
        List<stations_on_trip> res = stations.<Timestamp>search(testtime);
        Assert.assertEquals(4, res.size());

        Map<String, String> testval = new HashMap<>();
        testval.put("a_seats", "18");
        List<stations_on_trip> res2 = stations.<String>search(testval);
        Assert.assertEquals(2, res2.size());
    }

    @Test
    public void insertTest() {

        stationsDao stdao = new stationsDao();
        List<stations> list = stdao.getAll();
        stations st = new stations(list.size() + 1, "Muspelheim");
        stdao.insert(st);
        stations musp = stdao.getByID(list.size() + 1);
        stdao.delete(st);
        Assert.assertEquals(musp, st);

        // test empty
        Assert.assertFalse(stdao.insert(null));
    }

    @Test
    public void updateTest() {

        tripsDao tripsdao = new tripsDao();
        List<trips> list = tripsdao.getAll();
        trips st = new trips(list.size() + 1, "Dobraandpositiva", 1.25);
        tripsdao.insert(st);
        st.setPr_c(1.3);
        tripsdao.update(st);
        trips musp = tripsdao.getByID(list.size() + 1);
        tripsdao.delete(st);
        Assert.assertEquals(1.3, musp.getPr_c(), 0.0);

        // test empty
        Assert.assertFalse(tripsdao.update(null));
    }

    @Test
    public void deleteTest() {

        tripsDao tripsdao = new tripsDao();
        List<trips> list1 = tripsdao.getAll();
        trips st = new trips(list1.size() + 1, "Dobraandpositiva", 1.25);
        tripsdao.insert(st);
        tripsdao.delete(st);
        List<trips> list2 = tripsdao.getAll();
        Assert.assertEquals(list1.size(), list2.size());

        // test empty
        Assert.assertFalse(tripsdao.delete(null));
    }
}
