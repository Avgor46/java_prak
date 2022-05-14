package MyWebApp.controllers;

import MyWebApp.dao.Impl.ordersDao;
import MyWebApp.entity.orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class OrdersController {

    @Autowired
    private ordersDao lorders = new ordersDao();

    @GetMapping("/request")
    public String request(Model model) {
        List<orders> myord = lorders.getAll();
        model.addAttribute("orders", myord);
        return "Request";
    }
}
