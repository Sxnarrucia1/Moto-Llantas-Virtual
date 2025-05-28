package com.motollantas.MotoLlantasVirtual.Dao;

import com.motollantas.MotoLlantasVirtual.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface registerDao extends JpaRepository<User, Long> {

    public boolean existsByEmail(String email);
}
