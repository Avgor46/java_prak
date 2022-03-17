import dao.Impl.clientsDao;
import org.hibernate.Session;
import utils.HibernateUtil;

import java.util.HashMap;
import java.util.Map;

public class Start {
    public static void main(String argv[]) {
        Map<String, String> params = new HashMap<>();
        params.put("client_id" , "1");
        params.put("admin" , "True");
        Session session = HibernateUtil.getSessionFactory().openSession();
        /*Query<clients> all = session.createQuery(("FROM clients WHERE " + params.toString().substring(1, params.toString().length() - 1)).replaceAll(",", " and "));
        List<clients> list = all.getResultList();
        for (clients cl: list
             ) {
            System.out.println("Our goal is " + cl.toString());
        }*/
        //clients Simple = new clients();
        //clients entity = session.get(Simple.getClass(), 1L);
        //System.out.println(entity);
        clientsDao test = new clientsDao();
        System.out.println(test.getByID(2));
        session.close();
    }

}
