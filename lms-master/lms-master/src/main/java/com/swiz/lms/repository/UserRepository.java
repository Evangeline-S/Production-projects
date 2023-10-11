package com.swiz.lms.repository;

import com.swiz.lms.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByEmail(String email);

    AppUser findByUsername(String username);
}

