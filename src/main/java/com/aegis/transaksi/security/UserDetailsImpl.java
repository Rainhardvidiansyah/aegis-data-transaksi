package com.aegis.transaksi.security;


import com.aegis.transaksi.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class UserDetailsImpl implements UserDetails {

    private Long id;
    private String email;
    private String password;
    private boolean isActive;
    private Collection<? extends GrantedAuthority> authorities;


    public UserDetailsImpl(Long id, String email, String password, boolean isActive,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.authorities = authorities;
    }


    public static UserDetailsImpl of(Users users) {

        List<GrantedAuthority> role = users.getRoles().stream()
                .map(a -> new SimpleGrantedAuthority(a.getRoleName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(users.getId(), users.getEmail(), users.getPassword(),
                users.isActive(), role);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public Long id(){
        return this.id;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
