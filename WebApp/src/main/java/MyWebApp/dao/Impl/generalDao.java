package MyWebApp.dao.Impl;

import MyWebApp.dao.generalDaoInt;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import MyWebApp.utils.HibernateUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
abstract class generalDao<T, K> implements generalDaoInt<T, K> {
    Class<T> obj = null;

    @Override
    public T getByID(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        T entity = (T) session.get(obj, id);
        session.close();
        return entity;
    }

    @Override
    public List<T> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<T> query = session.createQuery("FROM  " + obj.getName());
        List<T> res = query.getResultList();
        session.close();
        return res;
    }

    @Override
    public <V> List<T> search(Map<String, V> params) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        //Query<T> all = session.createQuery(("FROM clients WHERE " + params.toString().substring(1, params.toString().length() - 1)).replaceAll(",", " and "));
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<T> cr = cb.createQuery(obj);
        Root<T> root = cr.from(obj);
        Predicate[] predicates = new Predicate[params.size()];
        int i = 0;
        for(var el: params.entrySet()) {
            predicates[i++] = cb.equal(root.get(el.getKey()), el.getValue());
        }
        cr.select(root).where(predicates);

        Query<T> all = session.createQuery(cr);
        List<T> res = all.getResultList();
        session.close();
        return res;
    }
    @Override
    public boolean insert(T entity) {
        if (Objects.isNull(entity)) {
            return false;
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return true;
    }
    @Override
    public boolean update(T entity) {
        if (Objects.isNull(entity)) {
            return false;
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(entity);
        transaction.commit();
        session.close();
        return true;
    }
    @Override
    public boolean delete(T entity) {
        if (Objects.isNull(entity)) {
            return false;
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(entity);
        transaction.commit();
        session.close();
        return true;
    }
}
