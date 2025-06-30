/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.controller;

import com.motollantas.MotoLlantasVirtual.Service.RepairOrderService;
import com.motollantas.MotoLlantasVirtual.Service.ServiceTypeService;
import com.motollantas.MotoLlantasVirtual.domain.RepairOrder;
import com.motollantas.MotoLlantasVirtual.domain.ServiceType;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    
    @Autowired
    private ServiceTypeService serviceTypeService;

    @GetMapping("/admin")
    public String showAdminCalendar(Model model) {
        List<ServiceType> serviceTypes = serviceTypeService.findAll();
        model.addAttribute("serviceTypes", serviceTypes);
        return "/calendar/admin";
    }

    @ResponseBody
    @GetMapping("/events")
    public List<Map<String, Object>> getCalendarEvents(@RequestParam(required = false) String serviceType) {

        List<RepairOrder> orders;

        if (serviceType != null && !serviceType.isEmpty()) {
            orders = repairOrderService.findAll().stream()
                    .filter(order -> order.getServiceType() != null
                    && serviceType.equalsIgnoreCase(order.getServiceType().getServiceName()))
                    .collect(Collectors.toList());
            System.out.println(orders);
        } else {
            orders = repairOrderService.findAll();
        }

        return orders.stream().map(order -> {
            Map<String, Object> event = new HashMap<>();
            event.put("id", order.getId());
            event.put("title", order.getFullName() + " - " + order.getMotorcycle().getLicensePlate());
            event.put("start", order.getAppointmentDate().toString());

            Duration duration = order.getServiceType() != null
                    ? order.getServiceType().getDuration()
                    : Duration.ofHours(1);
            LocalDateTime endDateTime = order.getAppointmentDate().plus(duration);
            event.put("end", endDateTime.toString());

            event.put("fullName", order.getFullName());
            event.put("modelName", order.getMotorcycle().getModelName());
            event.put("brand", order.getMotorcycle().getBrand());
            event.put("serviceType", order.getServiceType() != null ? order.getServiceType().getServiceName() : "N/A");
            event.put("licensePlate", order.getMotorcycle().getLicensePlate());
            event.put("color", order.getMotorcycle().getColor());
            event.put("problemDescription", order.getProblemDescription());
            event.put("status", order.getOrderStatus().name());

            return event;
        }).collect(Collectors.toList());
    }

}
