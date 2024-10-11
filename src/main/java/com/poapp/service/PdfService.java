package com.poapp.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.poapp.model.ApprovalWorkflow;
import com.poapp.model.PurchaseOrder;
import com.poapp.model.Item;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;

@Service
public class PdfService {

    public void generatePdf(ByteArrayOutputStream out, PurchaseOrder po) throws DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, out);  // Pass the ByteArrayOutputStream directly

        document.open();

        // Adding title and meta details
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA);

        document.add(new Paragraph("Purchase Order (PO-" + po.getPoNumber() + ")", headerFont));
        document.add(new Paragraph("Date: " + "0000", normalFont));
        document.add(new Paragraph("Description: " + po.getDescription(), normalFont));
        document.add(new Paragraph("Total Amount: $" + po.getTotalAmount(), normalFont));
        document.add(new Paragraph("Status: " + (po.getStatus() != null ? po.getStatus() : "Pending"), normalFont));
        document.add(new Paragraph(" "));

        // Adding Items Table
        PdfPTable itemsTable = new PdfPTable(4);
        itemsTable.setWidthPercentage(100);
        itemsTable.setWidths(new int[]{3, 1, 2, 2});
        itemsTable.addCell(getCell("Item Name", PdfPCell.ALIGN_CENTER, headerFont));
        itemsTable.addCell(getCell("Quantity", PdfPCell.ALIGN_CENTER, headerFont));
        itemsTable.addCell(getCell("Unit Price", PdfPCell.ALIGN_CENTER, headerFont));
        itemsTable.addCell(getCell("Total Price", PdfPCell.ALIGN_CENTER, headerFont));

        for (Item item : po.getItems()) {
            itemsTable.addCell(getCell(item.getItemName(), PdfPCell.ALIGN_CENTER, normalFont));
            itemsTable.addCell(getCell(String.valueOf(item.getQuantity()), PdfPCell.ALIGN_CENTER, normalFont));
            itemsTable.addCell(getCell("$" + String.format("%.2f", item.getPrice()), PdfPCell.ALIGN_CENTER, normalFont));
            itemsTable.addCell(getCell("$" + String.format("%.2f", item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))), PdfPCell.ALIGN_CENTER, normalFont));
        }

        document.add(itemsTable);
        document.add(new Paragraph(" "));

        // Adding Approval Workflow Table
        PdfPTable approvalTable = new PdfPTable(3);
        approvalTable.setWidthPercentage(100);
        approvalTable.setWidths(new int[]{1, 3, 2});
        approvalTable.addCell(getCell("Level", PdfPCell.ALIGN_CENTER, headerFont));
        approvalTable.addCell(getCell("Approver Name", PdfPCell.ALIGN_CENTER, headerFont));
        approvalTable.addCell(getCell("Status", PdfPCell.ALIGN_CENTER, headerFont));

        for (ApprovalWorkflow workflow : po.getApprovalWorkflows()) {
            approvalTable.addCell(getCell(String.valueOf(workflow.getApprovalLevel()), PdfPCell.ALIGN_CENTER, normalFont));
            approvalTable.addCell(getCell(workflow.getUser().getUsername(), PdfPCell.ALIGN_CENTER, normalFont));
            approvalTable.addCell(getCell(workflow.getStatus(), PdfPCell.ALIGN_CENTER, normalFont));
        }

        document.add(approvalTable);

        document.close();
    }

    private PdfPCell getCell(String text, int alignment, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }
}
