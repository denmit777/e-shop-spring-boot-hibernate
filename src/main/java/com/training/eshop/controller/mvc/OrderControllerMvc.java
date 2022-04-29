package com.training.eshop.controller.mvc;

import com.training.eshop.model.Cart;
import com.training.eshop.model.Order;
import com.training.eshop.model.User;
import com.training.eshop.service.CartService;
import com.training.eshop.service.OrderService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class OrderControllerMvc {

    private final OrderService orderService;
    private final CartService cartService;

    public OrderControllerMvc(OrderService orderService,
                              CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @GetMapping("/order")
    public String getOrder(Model model, HttpSession session) {
        String login = (String) session.getAttribute("login");

        Cart cart = (Cart) session.getAttribute("cart");
        BigDecimal totalPrice = (BigDecimal) session.getAttribute("totalPrice");
        String orderPrint = cartService.print(cart).replace("You have already chosen:\n", "");

        Order newOrder = getNewOrder(session, totalPrice);
        orderService.save(newOrder);

        model.addAttribute("login", login);
        model.addAttribute("order", orderPrint);
        model.addAttribute("totalPrice", newOrder.getTotalPrice());

        return "order";
    }

    @PostMapping("/order")
    public String logout() {
        return "redirect:/login/";
    }

    private Order getNewOrder(HttpSession session, BigDecimal totalPrice) {
        Long id = (Long) session.getAttribute("id");
        User user = new User();
        user.setId(id);

        List<Order> orderList = orderService.getAll();

        Long orderId = (long) orderList.size() + 1;

        Order order = new Order();
        order.setId(orderId);
        order.setUserId(user.getId());
        order.setTotalPrice(totalPrice);

        return order;
    }
}
