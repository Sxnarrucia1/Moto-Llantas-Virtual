package com.motollantas.MotoLlantasVirtual.Service;

import com.motollantas.MotoLlantasVirtual.domain.Notification;
import com.motollantas.MotoLlantasVirtual.domain.NotificationPreference;
import com.motollantas.MotoLlantasVirtual.domain.User;

import java.util.List;
import java.util.Optional;

public interface NotificationService {

    void notifyUser(User user, String message);

    void notifyUser(User user, String message, String type, Long referenceId);

    List<Notification> getUserNotifications(Long userId);

    int countUnreadNotifications(Long userId);

    void markAllAsRead(Long userId);

    void savePreferences(NotificationPreference preferences);

    Optional<NotificationPreference> findPreferencesByUserId(Long userId);
}
