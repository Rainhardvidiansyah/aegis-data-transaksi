package com.aegis.transaksi.repository;

import com.aegis.transaksi.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {


    @Query(value = "SELECT * FROM roles where role_name = :role_name", nativeQuery = true)
    Optional<Roles> findRolesByERole(@Param("role_name") String roleName);

    @Query(value = "SELECT * FROM roles", nativeQuery = true)
    List<Roles> findRoleByRoleName();
}
