package com.example.bookstore.controller;

import com.example.bookstore.entity.User;
import com.example.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        if (userRepository.existsByUsername(user.getUsername())) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }
        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            Model model) {
        User foundUser = userRepository.findByUsername(username);
        if (foundUser == null || !foundUser.getPassword().equals(password)) {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
        return "redirect:/";
    }

}
