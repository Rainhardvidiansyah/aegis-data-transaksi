package com.aegis.transaksi.repository;

import com.aegis.transaksi.entity.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM users where email = :email")
    Optional<Users> getUserByEmail(@Param("email") String email);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users u SET u.is_active = true WHERE u.email = :email", nativeQuery = true)
    int activateUserByEmail(@Param("email") String email);
}
