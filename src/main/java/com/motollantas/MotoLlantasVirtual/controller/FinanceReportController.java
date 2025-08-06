/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.motollantas.MotoLlantasVirtual.Service.FinanceReportService;
import com.motollantas.MotoLlantasVirtual.Service.ReportExportService;
import com.motollantas.MotoLlantasVirtual.domain.ReportData;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author esteb
 */
@Controller
@RequestMapping("/dashboard")
public class FinanceReportController {

    @Autowired
    private FinanceReportService reportService;
    
    @Autowired
    ReportExportService exportService;

    @PostMapping("/report")
    public String generateReport(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam("period") String period,
            Model model) {

        if (startDate.isAfter(endDate)) {
            model.addAttribute("mensajeError", "La fecha de inicio no puede ser posterior a la fecha de fin.");
            return "dashboard/finance";
        }

        ReportData report = reportService.generateReport(startDate, endDate, period);

        model.addAttribute("report", report);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("period", period);
        ObjectMapper mapper = new ObjectMapper();
        String reportJson;
        try {
            reportJson = mapper.writeValueAsString(report);
            model.addAttribute("reportJson", reportJson);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(FinanceReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "dashboard/financeReport";
    }

    @GetMapping("/report/export")
    public ResponseEntity<byte[]> exportReport(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam("period") String period,
            @RequestParam("format") String format) {

        ReportData report = reportService.generateReport(startDate, endDate, period);
        byte[] fileBytes;
        String filename;
        String contentType;

        try {
            if ("pdf".equalsIgnoreCase(format)) {
                fileBytes = exportService.generatePdf(report);
                filename = "reporte-financiero.pdf";
                contentType = "application/pdf";
            } else if ("excel".equalsIgnoreCase(format)) {
                fileBytes = exportService.generateExcel(report);
                filename = "reporte-financiero.xlsx";
                contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            } else {
                return ResponseEntity.badRequest().build();
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(fileBytes);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
