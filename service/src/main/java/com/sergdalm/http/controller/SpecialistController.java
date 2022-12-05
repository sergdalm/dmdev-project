package com.sergdalm.http.controller;

import com.sergdalm.dao.filter.SpecialistFilter;
import com.sergdalm.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Controller
@RequestMapping("/specialists")
public class SpecialistController {

    private final UserService userService;

    @GetMapping
    public String findAll(Model model, SpecialistFilter specialistFilter) {
        model.addAttribute("specialists", userService.findAll(specialistFilter));
        return "/specialists";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        return userService.findById(id)
                .map(user -> {
                            model.addAttribute("specialist", user);
                            return "/specialist";
                        }
                )
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
