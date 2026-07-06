package com.htv.user.repo;

import com.htv.user.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {
    Optional<AppUser> findByUsernameOrEmail(String username, String email);
    boolean existsByUsernameOrEmail(String username, String email);
}
