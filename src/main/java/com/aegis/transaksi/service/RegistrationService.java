package com.aegis.transaksi.service;


import com.aegis.transaksi.entity.Roles;
import com.aegis.transaksi.entity.Users;
import com.aegis.transaksi.enums.ERole;
import com.aegis.transaksi.repository.RoleRepository;
import com.aegis.transaksi.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {

    private static final Logger log = LoggerFactory.getLogger(RegistrationService.class);

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<Roles> role(){

        List<Roles> roles = new ArrayList<>();

        Optional<Roles> roleUser = this.roleRepository.findRolesByERole(ERole.ROLE_CASHIER.name());

        if(roleUser.isPresent()){
            roles.add(roleUser.get());
        }

        return roles;
    }


    public Users registration(String email, String password){

        try{
            Users users = new Users();
            users.setEmail(email);
            users.setPassword(passwordEncoder.encode(password));
            users.setActive(false);
            users.setRoles(this.role());
            return userRepository.save(users);
        }catch (IllegalArgumentException e){
            throw new IllegalStateException("Registrasi gagal");
        }

    }

    public boolean findSameEmail(String email){
        return this.userRepository.getUserByEmail(email).isPresent();
    }

}
