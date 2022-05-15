package MyWebApp.controllers;

import MyWebApp.dao.Impl.clientsDao;
import MyWebApp.dao.Impl.stations_on_tripDao;
import MyWebApp.dao.Impl.tripsDao;
import MyWebApp.entity.clients;
import MyWebApp.entity.stations_on_trip;
import MyWebApp.entity.trips;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class AdminController {

    @Autowired
    private final clientsDao dclients = new clientsDao();

    @Autowired
    private final tripsDao dtrips = new tripsDao();

    @Autowired
    private final stations_on_tripDao dstations_on_trip = new stations_on_tripDao();

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
}
