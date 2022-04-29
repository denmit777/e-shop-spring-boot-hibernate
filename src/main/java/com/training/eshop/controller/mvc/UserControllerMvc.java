package com.training.eshop.controller.mvc;

import com.training.eshop.model.Cart;
import com.training.eshop.model.User;
import com.training.eshop.model.security.CustomUserDetails;
import com.training.eshop.service.CartService;
import com.training.eshop.service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserControllerMvc {

    private final UserService userService;

    private User user;
    private Cart cart;
    private PasswordEncoder encoder;

    public UserControllerMvc(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        user = new User();
        cart = new Cart();
        encoder = new BCryptPasswordEncoder(4);
    }

    @GetMapping("/register")
    public String getUserByParameters(Model model, HttpSession session) {
        String login = (String) session.getAttribute("login");
        String password = (String) session.getAttribute("password");
        String email = (String) session.getAttribute("email");

        model.addAttribute("login", login);
        model.addAttribute("password", password);
        model.addAttribute("email", email);

        return "register";
    }

    @PostMapping("/register")
    public String register(Model model, HttpServletRequest request, HttpSession session, CustomUserDetails details) {
        User newUser = addNewUser(request);

        details = new CustomUserDetails(newUser);

        String login = details.getUser().getLogin();
        String password = details.getUser().getPassword();
        String email = details.getUser().getEmail();

        model.addAttribute("login", login);
        model.addAttribute("password", password);
        model.addAttribute("email", email);

        session.setAttribute("login", login);
        session.setAttribute("password", password);
        session.setAttribute("email", email);

        updateData(session);

        if (request.getParameter("checkbox") != null && email.contains(login)) {
            return "success";
        }

        return "checkbox_email_Error";
    }

    @GetMapping("/success")
    public ModelAndView success(HttpSession session, Authentication authentication) {
        String login = authentication.getName();

        user.setLogin(login);

        session.setAttribute("login", user.getLogin());

        updateData(session);

        final ModelAndView modelAndView = new ModelAndView("success");
        modelAndView.addObject("login", login);

        return modelAndView;
    }

    @GetMapping("/login")
    public String start() {
        return "login";
    }

    @PostMapping("/login")
    public String login() {
        return "redirect:/success/";
    }

    @GetMapping("/loginError")
    public String loginError() {
        return "loginError";
    }

    private User addNewUser(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        user.setLogin(login);

        List<User> usersList = userService.getAll();
        Long id = (long) usersList.size() + 1;

        user.setId(id);
        user.setPassword(encoder.encode(password));
        user.setEmail(email);

        return user;
    }

    private void updateData(HttpSession session) {
        cart.deleteGoods();

        session.setAttribute("cart", cart);

        String chosenGoods = "Make your order\n";

        session.setAttribute("chosenGoods", chosenGoods);
    }
}
