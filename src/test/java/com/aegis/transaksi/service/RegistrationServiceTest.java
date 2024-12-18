package com.aegis.transaksi.service;

import com.aegis.transaksi.repository.RoleRepository;
import com.aegis.transaksi.entity.Employee;
import com.aegis.transaksi.entity.Roles;
import com.aegis.transaksi.entity.Users;
import com.aegis.transaksi.enums.ERole;
import com.aegis.transaksi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @InjectMocks
    private RegistrationService registrationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    void setup(){
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void registration() {
//
//        Roles roles = new Roles();
//        roles.setRoleName(ERole.ROLE_EMPLOYEE);
//
//        var user = new Users();
//        user.setId(1L);
//        user.setEmail("rainhard@email");
//        user.setEmployee(new Employee());
//        user.setPassword("password");
//
//        List<Roles> role = new ArrayList<>();
//        role.add(roles);
//        user.setRoles(role);
//
//        Mockito.when(passwordEncoder.encode(anyString())).thenReturn("password");
//
//        Mockito.when(userRepository.save(any(Users.class))).thenReturn(user);
//
//        Users registration = registrationService.registration("rainhard@email", "password");
//
//        assertNotNull(registration);
//
//        System.out.println(user.getRoles());
//        Mockito.verify(userRepository).save(any(Users.class));
//    }

//    @Test
//    void findSameEmail() {
//        var user = new Users();
//        user.setEmail("rainhard@email.com");
//
//        Mockito.when(userRepository.getUserByEmail("rainhard@email.com"))
//                .thenReturn(Optional.of(user));
//
//        Boolean service = registrationService.findSameEmail("rainhard@email.com");
//
//        assertEquals(true, service);
//        assertNotNull(service);
//
//
//        Mockito.verify(userRepository).getUserByEmail("rainhard@email.com");
//
//    }
//
//    @Test
//    void role() {
//
//        var role = new Roles();
//        role.setRoleName(ERole.ROLE_EMPLOYEE);
//
//        Mockito.when(roleRepository.findRolesByERole(ERole.ROLE_EMPLOYEE.name())).thenReturn(Optional.of(role));
//
//        List<Roles> roleService = registrationService.role();
//
//        assertNotNull(roleService);
//        assertEquals(ERole.ROLE_EMPLOYEE, roleService.get(0).getRoleName());
//
//        System.out.println(roleService);
//
//        Mockito.verify(roleRepository).findRolesByERole(ERole.ROLE_EMPLOYEE.name());
//
//
//    }
}