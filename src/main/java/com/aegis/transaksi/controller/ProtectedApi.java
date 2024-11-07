package com.aegis.transaksi.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/aegis/protected")
public class ProtectedApi {

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public String getProtectedApi(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "halo " + authentication.getName();
    }
}
