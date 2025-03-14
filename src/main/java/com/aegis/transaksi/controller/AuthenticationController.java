package com.aegis.transaksi.controller;

import com.aegis.transaksi.security.jwt.JwtService;
import com.aegis.transaksi.service.EmployeeService;
import com.aegis.transaksi.dto.AuthDto;
import com.aegis.transaksi.dto.AuthResponseDto;
import com.aegis.transaksi.dto.RegistrationDto;
import com.aegis.transaksi.service.RegistrationService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/aegis/auth")
public class AuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationDto dto){
        if(dto.getEmail().isEmpty() && dto.getPassword().isEmpty()){
            return new ResponseEntity<>("Tidak berhasil mendaftar",
                    HttpStatusCode.valueOf(400));
        }

        if(registrationService.findSameEmail(dto.getEmail())){
            return new ResponseEntity<>("Email sudah terdaftar", HttpStatusCode.valueOf(400));
        }

        var users = registrationService.registration(dto.getEmail(), dto.getPassword());
        var employee = employeeService.saveEmployee(users);
        log.info("Data employee: {}", employee);
        return new ResponseEntity<>("Berhasil mendaftar", HttpStatusCode.valueOf(200));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDto authDto){
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getPassword())
            );
            log.info("Otoritas dalam user: {}", authentication.getAuthorities());
            log.info("Principal dalam user: {}", authentication.getPrincipal());

            if (!authentication.isAuthenticated()){
                log.info("Gagal login");
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String token = jwtService.generateToken(userDetails);

            List<String> roles = authentication.getAuthorities()
                    .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

            var response = new AuthResponseDto(userDetails.getUsername(), token, roles);

            return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
        }
        catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("password atau email salah");
        }

    }





}
