package com.motollantas.MotoLlantasVirtual.dao;

import com.motollantas.MotoLlantasVirtual.domain.DocumentType;
import com.motollantas.MotoLlantasVirtual.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface registerDao extends JpaRepository<User, Long> {

    public boolean existsByEmail(String email);
    boolean existsByDocumentTypeAndIdentification(DocumentType documentType, String identification);
    Optional<User> findByEmail(String email);
}