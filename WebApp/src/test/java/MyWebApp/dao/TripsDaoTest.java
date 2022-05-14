package MyWebApp.dao;

import MyWebApp.dao.Impl.tripsDao;
import MyWebApp.entity.trips;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TripsDaoTest {

    @Test
    public void getTripByIdTest() {
        long id = 1;
        tripsDao dao = new tripsDao();
        trips trip = dao.getByID(id);
        Assert.assertEquals(trip.getCompany(), "Umbrella");
    }

    @Test
    public void getAllTest() {
        long id = 1;
        tripsDao dao = new tripsDao();
        List<trips> all = dao.getAll();

        Assert.assertTrue(all.size() != 0);
    }
}
