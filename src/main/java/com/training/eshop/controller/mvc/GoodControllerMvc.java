package com.training.eshop.controller.mvc;

import com.training.eshop.model.Good;
import com.training.eshop.service.GoodService;
import com.training.eshop.model.Cart;
import com.training.eshop.service.CartService;
import com.training.eshop.service.mail.EmailService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.context.Context;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class GoodControllerMvc {

    private static final String REGEX_ONLY_LETTERS = "[^A-Za-z]";
    private static final String REGEX_ONLY_FIGURES = "[A-Za-z]";
    private static final String REGEX_LETTERS_FIGURES_POINT = "[^A-Za-z0-9.]";

    private final GoodService goodService;
    private final CartService cartService;
    private final EmailService emailService;

    private Cart cart;

    public GoodControllerMvc(GoodService goodService,
                             CartService cartService,
                             EmailService emailService) {
        this.goodService = goodService;
        this.cartService = cartService;
        this.emailService = emailService;
    }

    @PostConstruct
    public void init() {
        cart = new Cart();
    }

    @GetMapping("/good")
    public String getAllGoods(Model model, HttpSession session) {
        String login = (String) session.getAttribute("login");
        session.setAttribute("login", login);

        List<Good> goods = goodService.getAll();

        String options = getStringOfOptionsForDroppingMenuFromGoodList(goods);
        String chosenGoods = (String) session.getAttribute("chosenGoods");
        String choice = getChoice(chosenGoods);

        model.addAttribute("login", login);
        model.addAttribute("options", options);
        model.addAttribute("choice", choice);

        return "good";
    }

    @PostMapping("/createCart")
    public String createCart(HttpServletRequest request) throws MessagingException {
        HttpSession session = request.getSession();

        createCart(session);

        BigDecimal totalPrice = cartService.getTotalPrice(cart);

        session.setAttribute("cart", cart);
        session.setAttribute("totalPrice", totalPrice);

        String command = request.getParameter("submit");

        if ("Add Good".equals(command)) {
            String option = getStringOfNameAndPriceFromOptionMenu(request.getParameter("goodName"));

            addGoodToCart(option);

            String chosenGoods = cartService.print(cart);
            session.setAttribute("chosenGoods", chosenGoods);

            return "redirect:/good/";
        }
        if ("Log out".equals(command)) {
            return "redirect:/login/";
        }

        sendEmail(session);

        return "redirect:/order/";
    }

    private String getStringOfOptionsForDroppingMenuFromGoodList(List<Good> goods) {
        return goods.stream()
                .map(good -> "<option>" + good.getName() + " (" + good.getPrice() + ") </option>\n")
                .collect(Collectors.joining());
    }

    private String getChoice(String chosenGoods) {
        if (chosenGoods != null) {
            return chosenGoods;
        }
        return "Make your order\n";
    }

    private void createCart(HttpSession session) {
        if (session.getAttribute("cart") != null) {
            cart = (Cart) session.getAttribute("cart");
        }
    }

    private String getStringOfNameAndPriceFromOptionMenu(String s) {
        return s.replaceAll(REGEX_LETTERS_FIGURES_POINT, "");
    }

    private void addGoodToCart(String option) {
        String name = option.replaceAll(REGEX_ONLY_LETTERS, "");
        String price = option.replaceAll(REGEX_ONLY_FIGURES, "");

        cart.addGood(new Good(name, BigDecimal.valueOf(Double.parseDouble(price))));
    }

    private void sendEmail(HttpSession session) throws MessagingException {
        String email = (String) session.getAttribute("email");
        String subject = "Order details";
        String template = "orderDetails";

        final Context ctx = new Context();

        ctx.setVariable("login", session.getAttribute("login"));
        ctx.setVariable("order", cartService.print(cart).replace("You have already chosen:\n", ""));
        ctx.setVariable("totalPrice", session.getAttribute("totalPrice"));

        if ("beigruq@yopmail.com".equals(email)) {
            emailService.sendEmail(email, subject, ctx, template);
        }
    }
}
