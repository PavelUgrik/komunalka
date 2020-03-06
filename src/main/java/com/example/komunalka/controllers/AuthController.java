package com.example.komunalka.controllers;

import com.example.komunalka.entities.Role;
import com.example.komunalka.entities.Tarif;
import com.example.komunalka.entities.User;
import com.example.komunalka.repositories.TarifRepository;
import com.example.komunalka.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collections;

@Controller
public class AuthController {
    private UserService userService;
    private TarifRepository tarifRepository;

    @Autowired
    public AuthController(UserService userService, TarifRepository tarifRepository) {
        this.userService = userService;
        this.tarifRepository = tarifRepository;
    }

    @GetMapping("/registration")
    public String singUp() {
        return "registration";
    }

    @PostMapping("/registration")
    public String newUser(@NotNull User user) {
                UserDetails userFromDb = userService.loadUserByUsername(user.getUsername());
                Tarif tarif = tarifRepository.findFirstByOrderByIdDesc();
                if (userFromDb == null) {
                    user.setRoles(Collections.singleton(Role.USER));
                    user.setActive(true);
                    user.setWater(0);
                    user.setElectric(0);
                    user.setGas(0);
                    user.setTarif(tarif);
                    user.setUpdate(LocalDate.now());
                    userService.save(user);
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
