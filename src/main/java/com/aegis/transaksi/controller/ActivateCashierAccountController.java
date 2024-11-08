package com.aegis.transaksi.controller;

import com.aegis.transaksi.service.UserService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/aegis/admin")
public class ActivateCashierAccountController {


    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(ActivateCashierAccountController.class);

    public ActivateCashierAccountController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/")
    public String test(){
        logger.info("This is get hit");
        return "test";
    }

    @PutMapping("/activate-cashier") //activate?email=rainhard@email.com
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> activateCashierAccount(@RequestParam String email){

        logger.info("activateCashierAccount just got hit");

        var user = userService.getUsers(email);

        if(user == null){
            return new ResponseEntity<>("Email tidak ada", HttpStatus.NOT_FOUND);
        }else if(user.isActive()){
            return new ResponseEntity<>("Akun telah diaktivasi", HttpStatus.BAD_REQUEST);
        }

        this.userService.createActiveuser(user.getEmail());

        return new ResponseEntity<>(user.getEmail() + " telah aktif", HttpStatus.OK);
    }


}
