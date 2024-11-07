package com.aegis.transaksi.controller;

import com.aegis.transaksi.service.RegistrationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/aegis/auth")
public class CashierRegistrationController {

    private final RegistrationService registrationService;

    public CashierRegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }


    @GetMapping
    public String test(){
        return "Test CashierRegistrationController";
    }
}
