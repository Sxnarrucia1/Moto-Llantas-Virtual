/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.motollantas.MotoLlantasVirtual.Service.ReportExportService;
import com.motollantas.MotoLlantasVirtual.domain.FinancialSummary;
import com.motollantas.MotoLlantasVirtual.domain.ReportData;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

/**
 *
 * @author esteb
 */
@Service
public class ReportExportServiceImpl implements ReportExportService {

    @Override
    public byte[] generatePdf(ReportData reportData) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (Document document = new Document(PageSize.A4)) {
            PdfWriter.getInstance(document, out);

            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

            document.add(new Paragraph("Reporte Financiero", titleFont));
            document.add(new Paragraph(" ")); // Espacio

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            Stream.of("Periodo", "Ingresos", "Egresos", "Balance").forEach(header -> {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            });

            for (FinancialSummary summary : reportData.getSummaries()) {
                table.addCell(new Phrase(summary.getLabel(), cellFont));
                table.addCell(new Phrase(summary.getIncome().toString(), cellFont));
                table.addCell(new Phrase(summary.getExpense().toString(), cellFont));
                table.addCell(new Phrase(summary.getBalance().toString(), cellFont));
            }

            PdfPCell totalLabel = new PdfPCell(new Phrase("Totales", headerFont));
            totalLabel.setColspan(1);
            table.addCell(totalLabel);
            table.addCell(new Phrase(reportData.getTotalIncome().toString(), cellFont));
            table.addCell(new Phrase(reportData.getTotalExpense().toString(), cellFont));
            table.addCell(new Phrase(reportData.getNetBalance().toString(), cellFont));

            document.add(table);
        }

        return out.toByteArray();
    }

    @Override
    public byte[] generateExcel(ReportData reportData) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reporte Financiero");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Periodo");
        header.createCell(1).setCellValue("Ingresos");
        header.createCell(2).setCellValue("Egresos");
        header.createCell(3).setCellValue("Balance");

        int rowIdx = 1;
        for (FinancialSummary summary : reportData.getSummaries()) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(summary.getLabel());
            row.createCell(1).setCellValue(summary.getIncome().doubleValue());
            row.createCell(2).setCellValue(summary.getExpense().doubleValue());
            row.createCell(3).setCellValue(summary.getBalance().doubleValue());
        }

        Row totalRow = sheet.createRow(rowIdx);
        totalRow.createCell(0).setCellValue("Totales");
        totalRow.createCell(1).setCellValue(reportData.getTotalIncome().doubleValue());
        totalRow.createCell(2).setCellValue(reportData.getTotalExpense().doubleValue());
        totalRow.createCell(3).setCellValue(reportData.getNetBalance().doubleValue());

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            workbook.write(out);
        } catch (IOException ex) {
            Logger.getLogger(ReportExportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            workbook.close();
        } catch (IOException ex) {
            Logger.getLogger(ReportExportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return out.toByteArray();
    }

}
