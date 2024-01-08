package com.hunglh.backend.repositories;

import com.hunglh.backend.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email);

    @Query("SELECT u FROM Users u JOIN u.role r WHERE r.role = :role")
    List<Users> findAllByRolesRole(@Param("role") String role);
}
