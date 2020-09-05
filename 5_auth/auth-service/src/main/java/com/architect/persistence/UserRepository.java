package com.architect.persistence;

import com.architect.persistence.entities.DbUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<DbUser, Long> {
    Optional<DbUser> findByEmail(String email);
}
