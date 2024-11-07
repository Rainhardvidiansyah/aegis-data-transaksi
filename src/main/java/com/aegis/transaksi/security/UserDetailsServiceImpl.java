package com.aegis.transaksi.security;

import com.aegis.transaksi.entity.Users;
import com.aegis.transaksi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = this.userRepository.getUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User tidak ditemukan: " + email));

        if(!users.isActive()){
            throw new DisabledException("Anda harus diaktivasi dulu");
        }
        return UserDetailsImpl.of(users);
    }
}
