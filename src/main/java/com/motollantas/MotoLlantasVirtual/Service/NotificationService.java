/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.Service;

import com.motollantas.MotoLlantasVirtual.domain.Notification;
import com.motollantas.MotoLlantasVirtual.domain.User;
import java.util.List;

/**
 *
 * @author esteb
 */
public interface NotificationService {

    void notifyUser(User user, String message);

    List<Notification> getUserNotifications(Long userId);

    int countUnreadNotifications(Long userId);

    void markAllAsRead(Long userId);

}
