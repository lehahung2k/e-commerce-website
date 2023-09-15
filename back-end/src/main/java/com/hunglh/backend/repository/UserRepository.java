package com.hunglh.backend.repository;

import com.hunglh.backend.entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    List<Users> findAllByOrderByIdAsc();

    Page<Users> findAllByOrderByIdAsc(Pageable pageable);

    Optional<Users> findByActivationCode(String code);

    Optional<Users> findByEmail(String email);

    @Query("SELECT user.email FROM Users user WHERE user.passwordResetCode = :code")
    Optional<String> getEmailByPasswordResetCode(String code);
}
