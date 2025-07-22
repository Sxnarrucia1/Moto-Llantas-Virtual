package com.motollantas.MotoLlantasVirtual.dao;

import com.motollantas.MotoLlantasVirtual.domain.NotificationPreference;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationPreferenceDao extends JpaRepository<NotificationPreference, Long> {
    Optional<NotificationPreference> findByUserId(Long userId);
}
