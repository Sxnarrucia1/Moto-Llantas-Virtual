/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.Service.NotificationService;
import com.motollantas.MotoLlantasVirtual.dao.UserDao;
import com.motollantas.MotoLlantasVirtual.domain.User;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 *
 * @author esteb
 */
@Component
@ControllerAdvice
public class GlobalModelAttributes {

    @Autowired
    UserDao userDao;

    @Autowired
    NotificationService notificationService;

    @ModelAttribute("unreadCount")
    public int populateUnreadNotificationCount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getName().equals("anonymousUser")) {
            return 0;
        }

        String email = authentication.getName();
        Optional<User> optionalUser = userDao.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return notificationService.countUnreadNotifications(user.getIdUser());
        }

        return 0;
    }

}
