package com.motollantas.MotoLlantasVirtual.dao;

import com.motollantas.MotoLlantasVirtual.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface registerDao extends JpaRepository<User, Long> {

    public boolean existsByEmail(String email);
}