package com.motollantas.MotoLlantasVirtual.Service ;

    import com.motollantas.MotoLlantasVirtual.domain.DocumentType;
    import com.motollantas.MotoLlantasVirtual.domain.User ;
    import java.util.Optional ;

    /**
     *
     * @author esteb
     */

    public interface UserService {

        // --- EXISTENTES (mantener retrocompatibilidad) ---
        Optional<User> findByIdentification(String identification); // DEPRECATED: ver nota abajo
        boolean existsByIdentification(String identification);      // DEPRECATED: ver nota abajo

        // --- NUEVOS (recomendados) ---
        Optional<User> findByDocument(DocumentType documentType, String identification);
        Optional<User> findByDocumentCode(String documentTypeCode, String identification);
        boolean existsByDocument(DocumentType documentType, String identification);
        boolean existsByDocumentCode(String documentTypeCode, String identification);

        // --- Email y sesión ---
        User getCurrentUser();
        User findByEmail(String email);
        boolean existsByEmail(String email);

        // --- Persistencia ---
        void save(User user);        // valida tipo + número + email (si aplica)
        void update(User user);      // opcional, o usa save para ambos
        void deleteByEmail(String email);
    }

