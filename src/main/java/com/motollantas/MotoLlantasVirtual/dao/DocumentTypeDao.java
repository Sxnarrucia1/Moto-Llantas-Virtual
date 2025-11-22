package com.motollantas.MotoLlantasVirtual.dao;

import com.motollantas.MotoLlantasVirtual.domain.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentTypeDao extends JpaRepository<DocumentType, Integer> {

    List<DocumentType> findByIsActiveTrue();
    Optional<DocumentType> findByCode(String code);
}
