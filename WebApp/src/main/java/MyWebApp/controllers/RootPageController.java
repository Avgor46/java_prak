package MyWebApp.controllers;

import MyWebApp.dao.Impl.stations_on_tripDao;
import MyWebApp.dao.Impl.tripsDao;
import MyWebApp.entity.stations_on_trip;
import MyWebApp.entity.trips;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class RootPageController {

    @Autowired
    private final tripsDao dtrips = new tripsDao();

    @Autowired
    private final stations_on_tripDao dstations_on_trip = new stations_on_tripDao();

    @GetMapping("/")
    public String root(Model model) {
        model.addAttribute("title", "Root Page");
        return "RootPage";
    }

    @PostMapping("/")
    public String rootsearch(@RequestParam String from,
                             @RequestParam String to,
                             @RequestParam String date,
                             Model model) {
        Timestamp ldate = Timestamp.valueOf(date.replace("T"," ") + ":00");
        System.out.println(ldate.toString());
        List<trips> alltrips = dtrips.getAll();
        List<List<String>> result = new ArrayList();
        for (var el: alltrips) {
            Long lid = el.getId();
            List<stations_on_trip> ltrips = dstations_on_trip.getAllByTripId(lid);
            long start_num = 0;
            long end_num = 0;
            Timestamp sdate = Timestamp.valueOf("2030-01-01 00:00:00");
            Timestamp edate = Timestamp.valueOf("2001-01-01 00:00:00");
            Long la_seats = 0L;
            for (var ltrip: ltrips) {
                //System.out.printf("%s %s %d\n", ltrip.getStation_id().getName(), from, ltrip.getStation_id().getName() == from);
                if (ltrip.getStation_id().getName().equals(from)) {
                    la_seats = ltrip.getA_seats();
                    start_num = ltrip.getId();
                    sdate = ltrip.getDate();
                } else if (ltrip.getStation_id().getName().equals(to)) {
                    end_num = ltrip.getId();
                    edate = ltrip.getDate();
                }
            }
            System.out.printf("%s %s\n", sdate.toString(), edate.toString());
            if ((sdate.equals(edate) || sdate.before(edate)) && (sdate.equals(ldate) || sdate.before(ldate))) {
                List<String> temp = Arrays.asList(from, to, sdate.toString(), la_seats.toString(),
                        String.valueOf(start_num), String.valueOf(end_num));
                result.add(temp);
            }
        }

        if (result.size() == 0) {
            model.addAttribute("err", "Trips not found");
            return "ErrPage";
        } else {
            model.addAttribute("variants", result);
            return "AvVars";
        }
    }

}
