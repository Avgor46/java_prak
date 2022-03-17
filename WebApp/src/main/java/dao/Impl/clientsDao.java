package dao.Impl;

import dao.clientsDaoInt;
import entity.clients;

public class clientsDao extends generalDao<clients, Long> implements clientsDaoInt<clients, Long> {
    public clientsDao() {
        super.obj = clients.class;
    }
}
