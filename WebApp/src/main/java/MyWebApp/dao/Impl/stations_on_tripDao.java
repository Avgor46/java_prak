package MyWebApp.dao.Impl;

import MyWebApp.dao.stations_on_tripDaoInt;
import MyWebApp.entity.stations_on_trip;
import org.hibernate.Session;
import org.hibernate.query.Query;
import MyWebApp.utils.HibernateUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class stations_on_tripDao extends generalDao<stations_on_trip, Long> implements stations_on_tripDaoInt<stations_on_trip, Long> {
    public stations_on_tripDao() {
        super.obj = stations_on_trip.class;
    }
    public List<stations_on_trip> searchByDate(Timestamp d1, Timestamp d2) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<stations_on_trip> cr = cb.createQuery(obj);
        Root<stations_on_trip> root = cr.from(obj);
        Predicate greater = cb.greaterThanOrEqualTo(root.get("date"), d1);
        Predicate less = cb.lessThanOrEqualTo(root.get("date"), d2);

        cr.select(root).where(cb.and(greater, less));

        Query<stations_on_trip> all = session.createQuery(cr);
        List<stations_on_trip> res= all.getResultList();
        session.close();
        return res;
    }
    public List<stations_on_trip> getAllByTripId(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<stations_on_trip> cr = cb.createQuery(obj);
        Root<stations_on_trip> root = cr.from(obj);

        Predicate eq = cb.equal(root.get("trip_id"), id);

        cr.select(root).where(cb.and(eq));
        Query<stations_on_trip> all = session.createQuery(cr);
        List<stations_on_trip> res= all.getResultList();
        session.close();
        return res;
    }
}
