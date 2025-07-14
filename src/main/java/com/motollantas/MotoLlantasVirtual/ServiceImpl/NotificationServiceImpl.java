/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.Service.NotificationService;
import com.motollantas.MotoLlantasVirtual.dao.NotificationDao;
import com.motollantas.MotoLlantasVirtual.domain.Notification;
import com.motollantas.MotoLlantasVirtual.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author esteb
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationDao notificationDao;

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
        System.out.println("Creando notificación: " + message + " | Tipo: " + type + " | RefID: " + referenceId);
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
}
