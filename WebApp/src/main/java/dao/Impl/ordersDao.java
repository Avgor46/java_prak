package dao.Impl;

import dao.ordersDaoInt;
import entity.orders;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.HibernateUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.List;

public class ordersDao extends generalDao<orders, Long> implements ordersDaoInt<orders, Long> {
    public ordersDao() {
        super.obj = orders.class;
    }
}
