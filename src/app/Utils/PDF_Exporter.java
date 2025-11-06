package app.Utils;

import app.Object.Bill;
import app.Object.BillDetail;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.io.font.PdfEncodings;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PDF_Exporter {

    private final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private final SimpleDateFormat fileFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss");

    private final PdfFont regularFont;
    private final PdfFont boldFont;

    public PDF_Exporter() throws IOException {
 
        String FONT_PATH = "font/"; 
        
        // Font Regular (Bình thường)
        regularFont = PdfFontFactory.createFont(
                FONT_PATH + "RobotoMono-Regular.ttf", 
                PdfEncodings.IDENTITY_H, // Dùng IDENTITY_H cho tiếng Việt (Unicode)
                PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
        
        // Font Bold (In đậm)
        boldFont = PdfFontFactory.createFont(
                FONT_PATH + "RobotoMono-Bold.ttf", 
                PdfEncodings.IDENTITY_H,
                PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
    }

 
    public String exportBillToPDF(Bill bill) throws IOException {
        
        String fileName = "HoaDon_" + bill.getBillId() + "_" + fileFormatter.format(new Date()) + ".pdf";
        File billDir = new File("bill"); 
        if (!billDir.exists()) {
            billDir.mkdirs(); 
        }
        String filePath = billDir.getAbsolutePath() + File.separator + fileName;

        PdfWriter writer = new PdfWriter(filePath);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        document.setMargins(40, 40, 40, 40);
        

        document.setFont(regularFont);
        
        // 3. Tiêu đề Hóa đơn
        document.add(new Paragraph("HÓA ĐƠN THANH TOÁN")
                .setFont(boldFont) 
                .setFontSize(20)
                .setTextAlignment(TextAlignment.CENTER)); 
        
        document.add(new Paragraph("--- Cửa hàng cà phê Brew Coffee ---")
                .setFontSize(10)
                .setTextAlignment(TextAlignment.CENTER));
        
        document.add(new Paragraph("Ngày xuất: " + dateTimeFormatter.format(new Date()))
                .setFontSize(10)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(15));
        
        // 4. Nội dung
        addSummaryInfo(document, bill);
        addBillDetailTable(document, bill.getDetails());
        addFooterSummary(document, bill);

        // 5. Đóng Document
        document.close();
        
        return filePath;
    }
    
    private void addSummaryInfo(Document document, Bill bill) {
        String customerName; //Ghép tên
        if (bill.getCustomer().getFirstName() == null && bill.getCustomer().getLastName() == null) {
            customerName = "Khách lẻ";
        } else {
            customerName = bill.getCustomer().getFirstName() + " " + bill.getCustomer().getLastName();
        }

        String employeeName; //Ghép tên
        if (bill.getEmployee().getFirstName() == null && bill.getEmployee().getLastName() == null) {
            employeeName = "N/A";
        } else {
            employeeName = bill.getEmployee().getFirstName() + " " + bill.getEmployee().getLastName();
        }

        Table summaryTable = new Table(UnitValue.createPercentArray(new float[]{1, 1}));
        summaryTable.setWidth(UnitValue.createPercentValue(100));

        summaryTable.addCell(createCell("Mã HĐ: " + bill.getBillId(), false));
        summaryTable.addCell(createCell("Ngày tạo: " + bill.getDateCreated().toString(), false));

        summaryTable.addCell(createCell("Khách hàng: " + customerName, false));
        summaryTable.addCell(createCell("SĐT: " + (bill.getPhoneNumber() != null ? bill.getPhoneNumber() : "N/A"), false));
        
        summaryTable.addCell(createCell("Nhân viên: " + employeeName, false));
        summaryTable.addCell(createCell("Trạng thái: " + bill.getStatus(), false));
        
        document.add(summaryTable.setMarginBottom(15));
    }
    
    private void addBillDetailTable(Document document, List<BillDetail> details) {
        document.add(new Paragraph("Chi tiết sản phẩm").setFont(boldFont).setFontSize(12).setMarginBottom(5));
        
        Table table = new Table(UnitValue.createPercentArray(new float[]{35, 15, 20, 30}));
        table.setWidth(UnitValue.createPercentValue(100));
        
        table.addHeaderCell(createCell("Tên Sản Phẩm", true));
        table.addHeaderCell(createCell("SL", true).setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(createCell("Đơn Giá", true).setTextAlignment(TextAlignment.RIGHT));
        table.addHeaderCell(createCell("Tổng Cộng", true).setTextAlignment(TextAlignment.RIGHT));
        
        if (details != null) {
            for (BillDetail bd : details) {
                table.addCell(createCell(bd.getItemName() != null ? bd.getItemName() : "Món đã xóa", false));
                table.addCell(createCell(String.valueOf(bd.getQuantity()), false).setTextAlignment(TextAlignment.CENTER));
                table.addCell(createCell(String.format("%,.0f", bd.getPrice()), false).setTextAlignment(TextAlignment.RIGHT));
                table.addCell(createCell(String.format("%,.0f", bd.getTotal_price()), false).setTextAlignment(TextAlignment.RIGHT));
            }
        }
        
        document.add(table.setMarginBottom(15));
    }
    
    private void addFooterSummary(Document document, Bill bill) {
        Table footerTable = new Table(UnitValue.createPercentArray(new float[]{1.5f, 1}));
        footerTable.setWidth(UnitValue.createPercentValue(100));
        
        footerTable.addCell(createCell("TỔNG CỘNG:", true).setTextAlignment(TextAlignment.RIGHT));
        footerTable.addCell(createCell(String.format("%,.0f VNĐ", bill.getTotal()), true).setTextAlignment(TextAlignment.RIGHT));
        
        footerTable.addCell(createCell("Tiền khách trả:", false).setTextAlignment(TextAlignment.RIGHT));
        footerTable.addCell(createCell(String.format("%,.0f VNĐ", bill.getCustPayment()), false).setTextAlignment(TextAlignment.RIGHT));

        double excess = bill.getCustPayment() - bill.getTotal();
        footerTable.addCell(createCell("Tiền thừa:", false).setTextAlignment(TextAlignment.RIGHT));
        footerTable.addCell(createCell(String.format("%,.0f VNĐ", excess > 0 ? excess : 0), false).setTextAlignment(TextAlignment.RIGHT));

        document.add(footerTable.setMarginBottom(20));
        
        document.add(new Paragraph("Cảm ơn quý khách và hẹn gặp lại!")
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER));
    }

    private com.itextpdf.layout.element.Cell createCell(String content, boolean isHeader) {
        com.itextpdf.layout.element.Cell cell = new com.itextpdf.layout.element.Cell()
                .add(new Paragraph(content));
        
        // Nếu là Header, phải dùng font bold
        if (isHeader) {
            cell.setFont(boldFont); 
        } else {
            cell.setFont(regularFont);
        }

        cell.setBorder(isHeader ? null : Border.NO_BORDER)
            .setFontSize(10);
        
        if (isHeader) {
            cell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
        }
        
        return cell;
    }
}