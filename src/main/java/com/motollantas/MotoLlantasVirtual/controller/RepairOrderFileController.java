/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.controller;

import com.motollantas.MotoLlantasVirtual.Service.EmployeeService;
import com.motollantas.MotoLlantasVirtual.Service.RepairOrderService;
import com.motollantas.MotoLlantasVirtual.Service.ServiceTypeService;
import com.motollantas.MotoLlantasVirtual.dao.RepairOrderFileDao;
import com.motollantas.MotoLlantasVirtual.domain.OrderPriority;
import com.motollantas.MotoLlantasVirtual.domain.OrderStatus;
import com.motollantas.MotoLlantasVirtual.domain.RepairOrder;
import com.motollantas.MotoLlantasVirtual.domain.RepairOrderFile;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author esteb
 */
@Controller
@RequestMapping("/garage")
public class RepairOrderFileController {

    @Autowired
    RepairOrderService repairOrderService;

    @Autowired
    RepairOrderFileDao repairOderDao;

    @Autowired
    ServiceTypeService serviceTypeService;

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file,
            @RequestParam("repairOrderId") Long repairOrderId,
            Model model) {
        try {
            RepairOrder order = repairOrderService.findById(repairOrderId)
                    .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));

            String fileType = file.getContentType();
            if (!List.of("image/jpeg", "image/png", "image/jpg").contains(fileType)) {
                model.addAttribute("mensajeError", "Tipo de archivo no permitido.");
            } else {
                RepairOrderFile fileEntity = new RepairOrderFile();
                fileEntity.setFileName(file.getOriginalFilename());
                fileEntity.setFileType(fileType);
                fileEntity.setData(file.getBytes());
                fileEntity.setRepairOrder(order);

                repairOderDao.save(fileEntity);
                model.addAttribute("mensajeExito", "Archivo subido exitosamente.");
            }

            model.addAttribute("repairOrder", order);
            model.addAttribute("services", serviceTypeService.findAll());
            model.addAttribute("orderStatuses", OrderStatus.values());
            model.addAttribute("orderPriorities", OrderPriority.values());
            model.addAttribute("mechanics", employeeService.filterByRole("MECANICO"));

            return "garage/fragments :: editAdmin"; 
        } catch (IOException e) {
            model.addAttribute("mensajeError", "Error al subir el archivo.");
            return "garage/fragments :: editAdmin";
        }
    }

}
