/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.dao;

import com.motollantas.MotoLlantasVirtual.domain.DocumentType;
import com.motollantas.MotoLlantasVirtual.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDao extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByIdentification(String identification);

    void deleteByEmail(String email);

    public boolean existsByEmail(String email);

    public boolean existsByIdentification(String identification);

    Optional<User> findByDocumentTypeAndIdentification(DocumentType documentType, String identification);
    boolean existsByDocumentTypeAndIdentification(DocumentType documentType, String identification);

    // Alternativa por código (evita cargar DocumentType)
    @Query("""
           SELECT u FROM User u
           WHERE u.documentType.code = :code
             AND u.identification = :identification
           """)
    Optional<User> findByDocumentTypeCodeAndIdentification(@Param("code") String code,
                                                           @Param("identification") String identification);

    @Query("""
           SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END
           FROM User u
           WHERE u.documentType.code = :code
             AND u.identification = :identification
           """)
    boolean existsByDocumentTypeCodeAndIdentification(@Param("code") String code,
                                                      @Param("identification") String identification);
}
