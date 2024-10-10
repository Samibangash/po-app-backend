package com.poapp.repository;

import com.poapp.model.User;
import java.util.Optional; // Correct import for Optional
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
