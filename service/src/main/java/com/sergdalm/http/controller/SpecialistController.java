package com.sergdalm.http.controller;

import com.sergdalm.dao.filter.SpecialistFilter;
import com.sergdalm.dto.PageResponse;
import com.sergdalm.dto.SpecialistDto;
import com.sergdalm.entity.Gender;
import com.sergdalm.entity.ServiceName;
import com.sergdalm.service.AddressService;
import com.sergdalm.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final AddressService addressService;

    @GetMapping
    public String findAll(Model model, SpecialistFilter specialistFilter, Pageable pageable) {
        Page<SpecialistDto> page = userService.findAll(specialistFilter, pageable);
        model.addAttribute("specialists", PageResponse.of(page));
        model.addAttribute("addresses", addressService.findAll());
        model.addAttribute("genders", Gender.values());
        model.addAttribute("serviceNames", ServiceName.values());
        model.addAttribute("filter", specialistFilter);
        return "specialists/specialists";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        return userService.findSpecialistById(id)
                .map(specialist -> {
                            model.addAttribute("specialist", specialist);
                            return "specialists/specialist";
                        }
                )
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
