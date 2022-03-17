package dao;

import dao.Impl.clientsDao;
import entity.clients;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ClientsDaoTest {
    @Test
    public void getClientByIdTest() {
        long id = 1;
        clientsDao dao = new clientsDao();
        clients client = dao.getByID(id);
        Assert.assertEquals(client.getName(), "Max Payne");
    }

    @Test
    public void getAllTest() {
        clientsDao dao = new clientsDao();
        List<clients> all = dao.getAll();

        Assert.assertTrue(all.size() != 0);
    }
}
