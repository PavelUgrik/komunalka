package com.example.komunalka.controllers;

import com.example.komunalka.entities.Receipt;
import com.example.komunalka.entities.Tarif;
import com.example.komunalka.entities.User;
import com.example.komunalka.repositories.ReceiptRepository;
import com.example.komunalka.repositories.TarifRepository;
import com.example.komunalka.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Controller
public class MainController {
    private UserService userService;
    private TarifRepository tarifRepository;
    private ReceiptRepository receiptRepository;

    @Autowired
    public MainController(UserService userService, TarifRepository tarifRepository, ReceiptRepository receiptRepository) {
        this.userService = userService;
        this.tarifRepository = tarifRepository;
        this.receiptRepository = receiptRepository;
    }

    @GetMapping("/")
    public String main(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = (User) userService.loadUserByUsername(username);
        model.addAttribute("user", user);
        return "index";
    }

    @PostMapping("/input")
    public String input(@RequestParam(name = "id") Long id,
                        @RequestParam(name = "water_current") @NotNull Integer water_current,
                        @RequestParam(name = "electric_current") @NotNull Integer electric_current,
                        @RequestParam(name = "gas_current") @NotNull Integer gas_current) {
        User user = (User) userService.findById(id);
        Tarif tarif = tarifRepository.findFirstByOrderByIdDesc();
        user.setTarif(tarif);
//        if (user.getUpdate().getMonthValue() != LocalDate.now().getMonthValue()) {
            Receipt receipt = Receipt.builder()
                    .electric_last(user.getElectric())
                    .electric_current(electric_current)
                    .water_last(user.getWater())
                    .water_current(water_current)
                    .gas_last(user.getGas())
                    .gas_current(gas_current)
                    .user(user)
                    .created(LocalDate.now())
                    .build();
            receipt.calculate();
            receiptRepository.save(receipt);
            List<Receipt> receipts = user.getReceipts();
            receipts.add(receipt);
            user.setReceipts(receipts);
//        }
        if (water_current > user.getWater()) {
            user.setWater(water_current);
        }
        if (electric_current > user.getElectric()) {
            user.setElectric(electric_current);
        }
        if (gas_current > user.getGas()) {
            user.setGas(gas_current);
        }
        user.setUpdate(LocalDate.now());
        userService.save(user);
        return "redirect:/";
    }
}
