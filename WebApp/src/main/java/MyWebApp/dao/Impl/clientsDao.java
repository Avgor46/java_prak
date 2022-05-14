package MyWebApp.dao.Impl;

import MyWebApp.dao.clientsDaoInt;
import MyWebApp.entity.clients;
import org.springframework.stereotype.Repository;

@Repository
public class clientsDao extends MyWebApp.dao.Impl.generalDao<clients, Long> implements clientsDaoInt<clients, Long> {
    public clientsDao() {
        super.obj = clients.class;
    }
}
