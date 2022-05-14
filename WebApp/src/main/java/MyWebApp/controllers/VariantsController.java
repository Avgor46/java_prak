package MyWebApp.controllers;

import MyWebApp.dao.Impl.clientsDao;
import MyWebApp.dao.Impl.ordersDao;
import MyWebApp.dao.Impl.stations_on_tripDao;
import MyWebApp.entity.orders;
import MyWebApp.entity.stations_on_trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class VariantsController {

    @Autowired
    private final stations_on_tripDao dstations_on_trip = new stations_on_tripDao();

    @Autowired
    private final ordersDao dorders = new ordersDao();

    @Autowired
    private final clientsDao dclients = new clientsDao();

    @GetMapping("/AvVars/{start}/{end}")
    public String TicketPurchase(@PathVariable(value = "start") String start,
                                 @PathVariable(value = "end") String end,
                                 Model model) {
        stations_on_trip st = dstations_on_trip.getByID(Long.parseLong(start));
        stations_on_trip en = dstations_on_trip.getByID(Long.parseLong(end));
        if (st == null || en == null) {
            return "RootPage";
        }
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("start_st", st.getStation_id().getName());
        model.addAttribute("end_st", en.getStation_id().getName());
        model.addAttribute("start_date", st.getDate());
        model.addAttribute("end_date", en.getDate());
        model.addAttribute("company", st.getTrip_id().getCompany());
        model.addAttribute("price", 50 * Math.abs(st.getTrip_id().getPr_c() * (en.getSt_num() - st.getSt_num())));

        return "AcceptOrder";
    }

    @GetMapping("/Success/{start}/{end}")
    public String success(@PathVariable(value = "start") String start,
                          @PathVariable(value = "end") String end,
                          Model model) {
        stations_on_trip st = dstations_on_trip.getByID(Long.parseLong(start));
        stations_on_trip en = dstations_on_trip.getByID(Long.parseLong(end));
        if (st == null || en == null) {
            return "RootPage";
        }
        Long trip_id = st.getTrip_id().getId();
        //Long client_id = dclients.getAll().get(0).getId();
        Long order_id = (long) dorders.getAll().size() + 1;
        Long start_st_num = st.getSt_num();
        Long end_st_num = en.getSt_num();
        Double price = 50 * Math.abs(st.getTrip_id().getPr_c() * (en.getSt_num() - st.getSt_num()));
        dorders.insert(new orders(order_id, trip_id, dclients.getAll().get(0), start_st_num, end_st_num, price));
        return "SuccessOrder";
    }
}
