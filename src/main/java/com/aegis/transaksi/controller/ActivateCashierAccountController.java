package com.aegis.transaksi.controller;

import com.aegis.transaksi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin")
public class ActivateCashierAccountController {


    private final UserService userService;

    public ActivateCashierAccountController(UserService userService){
        this.userService = userService;
    }


    @PutMapping("/activate") //activate?email=rainhard@email.com
    @PreAuthorize("ROLE_ADMIN")
    public ResponseEntity<?> activateCashierAccount(@RequestParam String email){

        var user = userService.getUsers(email);

        if(user == null){
            return new ResponseEntity<>("Email tidak ada", HttpStatus.NOT_FOUND);
        }

        this.userService.createActiveuser(user.getEmail());

        return new ResponseEntity<>(user.getEmail() + " telah aktif", HttpStatus.OK);
    }


}
