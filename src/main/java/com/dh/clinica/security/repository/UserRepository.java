package com.dh.clinica.security.repository;

import com.dh.clinica.security.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);

    @Query(value = "SELECT * FROM app_user WHERE name = :name", nativeQuery = true)
    AppUser appUser(@Param("name") String name);
}
