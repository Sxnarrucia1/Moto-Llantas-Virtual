/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.Service;

import com.motollantas.MotoLlantasVirtual.domain.ReportData;

/**
 *
 * @author esteb
 */
public interface ReportExportService {
    
    public byte[] generatePdf(ReportData reportData);
    
    public byte[] generateExcel(ReportData reportData);
}
