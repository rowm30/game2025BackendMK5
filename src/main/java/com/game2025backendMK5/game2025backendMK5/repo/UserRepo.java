package com.game2025backendMK5.game2025backendMK5.repo;

import com.game2025backendMK5.game2025backendMK5.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
}
