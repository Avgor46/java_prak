package MyWebApp.controllers;

import MyWebApp.dao.Impl.clientsDao;
import MyWebApp.dao.Impl.ordersDao;
import MyWebApp.entity.clients;
import MyWebApp.entity.orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class OrdersController {

    @Autowired
    private ordersDao dorders = new ordersDao();

    @Autowired
    private final clientsDao dclients = new clientsDao();

    @GetMapping("/request")
    public String request(Model model) {
        clients cl = dclients.getAll().get(0);
        List<List<String>> result = new SupportController().getOrdersByClientId(cl.getId());
        model.addAttribute("orders", result);
        return "Request";
    }

    @PostMapping("/request/{id}/remove")
    public String deleteorder(@PathVariable(value = "id") long id, Model model) {
        orders order_id = dorders.getByID(id);
        if (order_id == null) {
            model.addAttribute("err", "User not found!");
            return "ErrPage";
        }
        dorders.delete(order_id);
        model.addAttribute("text", "Заказ успешно удален!");
        return "Success";
    }
}
