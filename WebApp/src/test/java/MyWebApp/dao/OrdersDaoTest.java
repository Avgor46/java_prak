package MyWebApp.dao;

import MyWebApp.dao.Impl.ordersDao;
import MyWebApp.entity.orders;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class OrdersDaoTest {
    @Test
    public void getOrderByIdTest() {
        long id = 1;
        ordersDao dao = new ordersDao();
        orders order = dao.getByID(id);
        Assert.assertEquals(order.getPrice(), 100);
    }

    @Test
    public void getAllTest() {
        long id = 1;
        ordersDao dao = new ordersDao();
        List<orders> all = dao.getAll();

        Assert.assertTrue(all.size() != 0);
    }
}
