import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class PdfService {

    public void generatePoPdf(PurchaseOrder po) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("PO_" + po.getId() + ".pdf"));
        document.open();

        document.add(new Paragraph("Purchase Order ID: " + po.getId()));
        document.add(new Paragraph("Description: " + po.getDescription()));
        document.add(new Paragraph("Total Amount: " + po.getTotalAmount()));

        for (ApprovalWorkflow workflow : po.getApprovalWorkflows()) {
            document.add(new Paragraph("Approval Level: " + workflow.getApprovalLevel()));
            document.add(new Paragraph("Approver: " + workflow.getUser().getUsername()));
            document.add(new Paragraph("Status: " + workflow.getStatus()));
        }

        document.close();
    }
}
