package com.trackforge.repository;

import com.trackforge.entity.RefreshToken;
import com.trackforge.entity.User;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser(User user);

    void deleteByUser(User user);
    
    @Transactional
    int deleteByUserId(Long userId);
    
    
    void deleteByToken(String token);

}
