package MyWebApp.controllers;

import MyWebApp.dao.Impl.clientsDao;
import MyWebApp.dao.Impl.ordersDao;
import MyWebApp.dao.Impl.stations_on_tripDao;
import MyWebApp.dao.Impl.tripsDao;
import MyWebApp.entity.clients;
import MyWebApp.entity.orders;
import MyWebApp.entity.stations_on_trip;
import MyWebApp.entity.trips;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class SupportController {

    @Autowired
    private final tripsDao dtrips = new tripsDao();

    @Autowired
    private final stations_on_tripDao dstations_on_trip = new stations_on_tripDao();

    @Autowired
    private final ordersDao dorders = new ordersDao();

    @Autowired
    private final clientsDao dclients = new clientsDao();

    public List<List<String>> getOrdersByClientId(Long id) {
        Map<String, clients> findmap = new HashMap<>();
        findmap.put("client_id", dclients.getByID(id));
        List<orders> res = dorders.search(findmap);
        List<List<String>> result = new ArrayList();
        for (var el: res) {
            trips trip_id = dtrips.getByID(el.getTrip_id());
            Long start_num = el.getStart_num();
            Long end_num = el.getEnd_num();
            Map<String, trips> localmap = new HashMap<>();
            localmap.put("trip_id", trip_id);
            List<stations_on_trip> stont = dstations_on_trip.search(localmap);
            stations_on_trip start_st = null;
            stations_on_trip end_st = null;
            for (var st: stont) {
                if (st.getSt_num() == start_num) {
                    start_st = st;
                } else if (st.getSt_num() == end_num) {
                    end_st = st;
                }
            }
            if (start_st == null || end_st == null) {
                return null;
            }
            Double price = Math.abs(50 * trip_id.getPr_c() * (start_num - end_num));
            String company = trip_id.getCompany();
            List<String> temp = Arrays.asList(
                    start_st.getStation_id().getName(),
                    end_st.getStation_id().getName(),
                    start_st.getDate().toString(),
                    end_st.getDate().toString(),
                    String.valueOf(price),
                    company,
                    String.valueOf(el.getId()));
            result.add(temp);
        }
        return result;
    }
}
