package MyWebApp.controllers;

import MyWebApp.dao.Impl.*;
import MyWebApp.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    private final clientsDao dclients = new clientsDao();

    @Autowired
    private final tripsDao dtrips = new tripsDao();

    @Autowired
    private final stations_on_tripDao dstations_on_trip = new stations_on_tripDao();

    @Autowired
    private final stationsDao dstations = new stationsDao();

    @Autowired
    private ordersDao dorders = new ordersDao();

    @GetMapping("/admin")
    public String adminroot(Model model) {
        return "AdminPage";
    }

    @GetMapping("/admin/clients")
    public String adminclients(Model model) {
        List<clients> cls = dclients.getAll();
        model.addAttribute("clients", cls);
        return "Clients";
    }

    @GetMapping("/admin/clients/{id}")
    public String adminclient(@PathVariable(value = "id") long id, Model model) {
        clients cl_id = dclients.getByID(id);
        if (cl_id == null) {
            model.addAttribute("err", "User not found!");
            return "ErrPage";
        }
        model.addAttribute("client", cl_id);
        return "ClientInfo";
    }

    @GetMapping("/admin/orders")
    public String adminorders(Model model) {
        return "AdminPage";
    }

    @PostMapping("/admin/clients/{id}")
    public String updateclient(@RequestParam String name,
                               @RequestParam String phone,
                               @RequestParam String email,
                               @RequestParam String address,
                               @RequestParam String login,
                               @PathVariable(value = "id") long id,
                               Model model) {
        clients cl_id = dclients.getByID(id);
        if (cl_id == null) {
            model.addAttribute("err", "User not found!");
            return "ErrPage";
        }
        clients cl_up = new clients(id, name, address, phone, email, cl_id.isAdmin(), login, cl_id.getPass());
        dclients.update(cl_up);
        return "redirect:/admin/clients";
    }

    @GetMapping("/admin/clients/{id}/trips")
    public String clienttrips(@PathVariable(value = "id") long id, Model model) {
        clients cl_id = dclients.getByID(id);
        if (cl_id == null) {
            model.addAttribute("err", "User not found!");
            return "ErrPage";
        }
        List<List<String>> result = new SupportController().getOrdersByClientId(id);
        model.addAttribute("orders", result);
        return "Request";
    }

    @PostMapping("/admin/clients/{id}/remove")
    public String deleteclient(@PathVariable(value = "id") long id, Model model) {
        clients cl_id = dclients.getByID(id);
        if (cl_id == null) {
            model.addAttribute("err", "User not found!");
            return "ErrPage";
        }
        dclients.delete(cl_id);
        model.addAttribute("text", "Пользователь успешно удален!");
        return "Success";
    }
    //--------------------------------------------------------------------------------------------------------
    @GetMapping("/admin/trips")
    public String adminctrips(Model model) {
        List<trips> alltrips = dtrips.getAll();
        List<List<stations_on_trip>> result = new ArrayList();
        for (var trip: alltrips) {
            Map<String, trips> findmap = new HashMap();
            findmap.put("trip_id", trip);
            List<stations_on_trip> lstont = dstations_on_trip.search(findmap);
            if (lstont.size() == 0) {
                continue;
            }
            stations_on_trip start_st = lstont.get(0);
            stations_on_trip end_st = lstont.get(0);
            for (var st: lstont) {
                if (st.getSt_num() < start_st.getSt_num()) {
                    start_st = st;
                } else if (st.getSt_num() > end_st.getSt_num()) {
                    end_st = st;
                }
            }
            List<stations_on_trip> temp = Arrays.asList(start_st, end_st);
            result.add(temp);
        }
        model.addAttribute("trips", result);
        return "Trips";
    }

    @GetMapping("/trips/{id}")
    public String tripinfo(@PathVariable(value = "id") long id, Model model) {
        Map<String, trips> findmap = new HashMap();
        findmap.put("trip_id", dtrips.getByID(id));
        List<stations_on_trip> stont = dstations_on_trip.search(findmap);
        model.addAttribute("stont", stont);
        return "TripInfo";
    }

    @GetMapping("/admin/trips/addtrip")
    public String addtrip(Model model) {
        return "AddTrip";
    }

    @PostMapping("/admin/trips/addtrip")
    public String addtripsubmit(@RequestParam String company,
                                @RequestParam String a_seats,
                                @RequestParam String hstations,
                                @RequestParam String dates,
                                Model model) {
        List<stations> lstations;
        try {
            lstations = Arrays.stream(hstations.split(",")).map(el -> {
                Map<String, String> findst = new HashMap();
                findst.put("name", el.trim());
                List<stations> lst = dstations.search(findst);
                return lst.get(0);}).collect(Collectors.toList());
        } catch (Exception e) {
            model.addAttribute("err", "Cannot find station");
            return "ErrPage";
        }
        List<Timestamp> ldates;
        try {
            ldates = Arrays.stream(dates.split(",")).map(el -> {
                System.out.println(el + "#");
                return Timestamp.valueOf(el + ":00");
            }).collect(Collectors.toList());
        } catch (Exception e) {
            model.addAttribute("err", "Cannot parse dates");
            return "ErrPage";
        }
        if (lstations.size() != ldates.size()) {
            model.addAttribute("err", "Sizes of dates and stations are not equal");
            return "ErrPage";
        }
        Map<String, String> find = new HashMap();
        find.put("company", company);
        List<trips> ltrips= dtrips.search(find);
        if (ltrips.size() == 0) {
            model.addAttribute("err", "Company not found");
            return "ErrPage";
        }
        trips ltrip = ltrips.get(0);
        ltrip.setId((long)dtrips.getAll().size() + 1);
        dtrips.insert(ltrip);
        int lsize = lstations.size();
        Long stont_size = Long.valueOf(dstations_on_trip.getAll().size() + 1);
        for (int i = 1; i <= lsize; ++i) {
            stations_on_trip lstont = new stations_on_trip(stont_size++, ltrip,
                    lstations.get(i - 1), i, Long.valueOf(a_seats), ldates.get(i - 1));
            dstations_on_trip.insert(lstont);
        }
        return "redirect:/admin/trips";
    }

    @PostMapping("/admin/trips/{id}/remove")
    public String deletetrip(@PathVariable(value = "id") long id, Model model) {
        trips trip_id = dtrips.getByID(id);
        if (trip_id == null) {
            model.addAttribute("err", "Trip not found!");
            return "ErrPage";
        }
        Map<String, Long> find = new HashMap();
        find.put("trip_id", trip_id.getId());
        List<orders> lorders= dorders.search(find);
        for (var el: lorders) {
            dorders.delete(el);
        }
        dtrips.delete(trip_id);
        model.addAttribute("text", "Рейс успешно удален!");
        return "Success";
    }

}
