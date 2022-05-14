package MyWebApp.dao.Impl;

import MyWebApp.dao.ordersDaoInt;
import MyWebApp.entity.orders;
import org.springframework.stereotype.Repository;

@Repository
public class ordersDao extends generalDao<orders, Long> implements ordersDaoInt<orders, Long> {
    public ordersDao() {
        super.obj = orders.class;
    }
}
