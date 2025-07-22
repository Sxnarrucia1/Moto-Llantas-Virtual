package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.Service.NotificationService;
import com.motollantas.MotoLlantasVirtual.dao.NotificationDao;
import com.motollantas.MotoLlantasVirtual.dao.NotificationPreferenceDao;
import com.motollantas.MotoLlantasVirtual.domain.Notification;
import com.motollantas.MotoLlantasVirtual.domain.NotificationPreference;
import com.motollantas.MotoLlantasVirtual.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationDao notificationDao;

    @Autowired
    private NotificationPreferenceDao preferencesDao;

    @Override
    public void notifyUser(User user, String message) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setCreatedAt(LocalDateTime.now());
        notificationDao.save(notification);
    }

    @Override
    public void notifyUser(User user, String message, String type, Long referenceId) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setType(type);
        notification.setReferenceId(referenceId);
        notification.setCreatedAt(LocalDateTime.now());
        notificationDao.save(notification);
    }

    @Override
    public List<Notification> getUserNotifications(Long userId) {
        return notificationDao.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public int countUnreadNotifications(Long userId) {
        return notificationDao.countByUserIdAndReadFalse(userId);
    }

    @Override
    public void markAllAsRead(Long userId) {
        List<Notification> unread = notificationDao.findByUserIdAndReadFalse(userId);
        for (Notification notification : unread) {
            notification.setRead(true);
        }
        notificationDao.saveAll(unread);
    }

    @Override
    public void savePreferences(NotificationPreference preferences) {
        Optional<NotificationPreference> existing = preferencesDao.findByUserId(preferences.getUserId());

        if (existing.isPresent()) {
            NotificationPreference pref = existing.get();
            pref.setPromotions(preferences.isPromotions());
            pref.setUpdates(preferences.isUpdates());
            pref.setReminders(preferences.isReminders());
            pref.setFrequency(preferences.getFrequency());
            preferencesDao.save(pref);
        } else {
            preferencesDao.save(preferences);
        }
    }

    @Override
    public Optional<NotificationPreference> findPreferencesByUserId(Long userId) {
        return preferencesDao.findByUserId(userId);
    }
}
