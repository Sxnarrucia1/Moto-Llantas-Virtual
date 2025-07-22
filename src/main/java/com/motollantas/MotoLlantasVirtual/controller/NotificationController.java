package com.motollantas.MotoLlantasVirtual.controller;

import com.motollantas.MotoLlantasVirtual.Service.NotificationService;
import com.motollantas.MotoLlantasVirtual.dao.NotificationDao;
import com.motollantas.MotoLlantasVirtual.dao.UserDao;
import com.motollantas.MotoLlantasVirtual.domain.Notification;
import com.motollantas.MotoLlantasVirtual.domain.NotificationPreference;
import com.motollantas.MotoLlantasVirtual.domain.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationDao notificationDao;

    @Autowired
    private UserDao userDao;

    @GetMapping("/notification")
    public String listNotifications(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userDao.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        notificationService.markAllAsRead(user.getIdUser());

        List<Notification> notifications = notificationService.getUserNotifications(user.getIdUser());
        model.addAttribute("notifications", notifications);

        NotificationPreference preferences = notificationService.findPreferencesByUserId(user.getIdUser())
                .orElseGet(() -> {
                    NotificationPreference defaultPrefs = new NotificationPreference();
                    defaultPrefs.setPromotions(false);
                    defaultPrefs.setUpdates(true);
                    defaultPrefs.setReminders(true);
                    defaultPrefs.setFrequency("mensual");
                    return defaultPrefs;
                });

        model.addAttribute("preferences", preferences);
        return "notifications/notification";
    }

    @PostMapping("/preferences")
    public String savePreferences(NotificationPreference preferences, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userDao.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        preferences.setUserId(user.getIdUser());
        notificationService.savePreferences(preferences);

        redirectAttributes.addFlashAttribute("successMessage", "Preferencias de notificación actualizadas correctamente.");
        return "redirect:/notifications/notification";
    }

    @PostMapping("/delete")
    public String deleteNotification(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        notificationDao.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Notificación eliminada.");
        return "redirect:/notifications/notification";
    }

    @PostMapping("/deleteAll")
    public String deleteAllNotifications(RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userDao.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Notification> notifications = notificationService.getUserNotifications(user.getIdUser());
        notificationDao.deleteAll(notifications);

        redirectAttributes.addFlashAttribute("successMessage", "Todas las notificaciones fueron eliminadas.");
        return "redirect:/notifications/notification";
    }
}
