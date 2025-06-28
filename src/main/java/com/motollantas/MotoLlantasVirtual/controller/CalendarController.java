/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.controller;

import com.motollantas.MotoLlantasVirtual.Service.RepairOrderService;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author esteb
 */
@Controller
@RequestMapping("/calendar")
public class CalendarController {

    @Autowired
    private RepairOrderService repairOrderService;

    @GetMapping("/admin")
    public String showAdminCalendar() {
        return "calendar/admin";
    }

    @ResponseBody
    @GetMapping("/events")
    public List<Map<String, Object>> getCalendarEvents() {
        return repairOrderService.findAll().stream().map(order -> {
            Map<String, Object> event = new HashMap<>();
            event.put("id", order.getId());
            event.put("title", order.getFullName() + " - " + order.getLicensePlate());
            event.put("start", order.getAppointmentDate().toString());

            Duration duration = order.getServiceType() != null
                    ? order.getServiceType().getDuration()
                    : Duration.ofHours(1);
            LocalDateTime endDateTime = order.getAppointmentDate().plus(duration);
            event.put("end", endDateTime.toString());

            event.put("fullName", order.getFullName());
            event.put("modelName", order.getModelName());
            event.put("brand", order.getBrand());
            event.put("serviceType", order.getServiceType() != null ? order.getServiceType().getServiceName() : "N/A");
            event.put("licensePlate", order.getLicensePlate());
            event.put("color", order.getColor());
            event.put("problemDescription", order.getProblemDescription());
            event.put("status", order.getOrderStatus().name());

            return event;
        }).collect(Collectors.toList());
    }

}
