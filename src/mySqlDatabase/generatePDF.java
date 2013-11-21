package mySqlDatabase;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Set;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import control.MainControl;
import domain.Patient;
import domain.Prescription;


public class generatePDF {
  private static String FILE = "FirstPdf.pdf";
  private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
      Font.BOLD);
  private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
      Font.NORMAL, BaseColor.RED);
  private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
      Font.BOLD);
  private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
      Font.BOLD);
  private static Patient patient=MainControl.getMainControl().getCurrentPatient();
  private static Prescription prescription=MainControl.getMainControl().getPrescription();
  private static Document document = new Document();
  

  public static void generateReport() throws Exception{
   
      PdfWriter.getInstance(document, new FileOutputStream(FILE));
      document.open();
      //addMetaData(document);
      generatePrescriptionInformarmation();
      createTable();
      createSignature();
      document.close();
      Desktop.getDesktop().open(new File(FILE));
   
  }

  // iText allows to add metadata to the PDF which can be viewed in your Adobe
  // Reader
  // under File -> Properties
//  private static void addMetaData(Document document) {
//    document.addTitle("My first PDF");
//    document.addSubject("Using iText");
//    document.addKeywords("Java, PDF, iText");
//    document.addAuthor("Lars Vogel");
//    document.addCreator("Lars Vogel");
//  }

  private static void generatePrescriptionInformarmation()
      throws DocumentException {
    Paragraph prescriptionParagraph = new Paragraph();
    // We add one empty line
    addEmptyLine(prescriptionParagraph, 1);
    // Lets write a big header
    Paragraph prescriptionTitle=new Paragraph("Gerard Farrell\n Clinic Name \n Clinic Address\n ClinicTell", catFont);
    addEmptyLine(prescriptionTitle, 2);

    prescriptionTitle.setAlignment(Element.ALIGN_CENTER);
    prescriptionParagraph.add(prescriptionTitle);

    document.add(prescriptionParagraph);
    // Start a new page
    //document.newPage();
    
    Paragraph patientParagraph=new Paragraph("Name:"+patient.getName()+"                    MCP:"+
    patient.getMCP()+"                      Address:"+patient.getAddress(), smallBold);
    addEmptyLine(patientParagraph, 1);

    document.add(patientParagraph);
   
  }
  
  private static void createTable() throws DocumentException {

   Paragraph tableParagraph=new Paragraph();
    PdfPTable table = new PdfPTable(2);

    // t.setBorderColor(BaseColor.GRAY);
    // t.setPadding(4);
    // t.setSpacing(4);
    // t.setBorderWidth(1);

    PdfPCell c1 = new PdfPCell(new Phrase("Drug Name"));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase("Drug Specification"));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(c1);
    
    table.setHeaderRows(1);

    for (String s : prescription.getDrugLines()) {
		String[] medicine=s.split(" ");
    	table.addCell(medicine[0]);
    	String medicine_spec="";
    	for(int i=1; i<medicine.length;i++)
    	{
    		medicine_spec=medicine_spec+" "+medicine[i];
    	}
        table.addCell(medicine_spec);
    }
    
    tableParagraph.add(table);
    document.add(tableParagraph);
  }

  private static void createSignature() throws DocumentException{
	  Paragraph dateSignature=new Paragraph("Date: "+MainControl.getMainControl().getPrescription().getEffectiveDate());
	  document.add(dateSignature);
  }
  private static void addEmptyLine(Paragraph paragraph, int number) {
    for (int i = 0; i < number; i++) {
      paragraph.add(new Paragraph(" "));
    }
  }
} 