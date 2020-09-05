package com.architect.persistence;

import com.architect.persistence.entities.DbSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface SessionRepository extends JpaRepository<DbSession, Long> {
    Optional<DbSession> findByToken(String token);
    void removeByUserId(Long userId);
}
