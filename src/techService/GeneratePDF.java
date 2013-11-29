package techService;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.itextpdf.awt.geom.Rectangle;
import com.itextpdf.text.Image;
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
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64.InputStream;

import control.MainControl;
import domain.Patient;
import domain.Prescription;

/**
 * Class to generate the prescription report
 * by calling one method named generateReport
 * the libary used for generating PDF is Itext 
 */
public class GeneratePDF {

	private static String temp_file;
	private static String File;
	private static String refill=MainControl.getMainControl().getPrescription().getRefill();
	private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
			Font.BOLD);
	private static Font rxFont = new Font(Font.FontFamily.TIMES_ROMAN, 20,
			Font.NORMAL, BaseColor.BLACK);
	private static Font tableHeaderFont = new Font(Font.FontFamily.TIMES_ROMAN, 12);
	private static Font tableFont = new Font(Font.FontFamily.TIMES_ROMAN, 12);

	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.BOLD);
	private static Patient patient=MainControl.getMainControl().getCurrentPatient();
	private static Prescription prescription=MainControl.getMainControl().getPrescription();
	private static Document document;


	/**
	 * the only public method that can be called\
	 * to generate the PDF report
	 */
	public static void generateReport() throws Exception{
		
		temp_file = MainControl.getMainControl().getPrescription().getIssueDate()+".pdf";
		System.out.println("Temp file naame is" +MainControl.getMainControl().getPrescription().getIssueDate()+".pdf");
		File=temp_file.replaceAll(":", "-");
		document= new Document();
		document.setPageSize(PageSize.NOTE);
		System.out.println("Effective date is PDF= "+File);
		PdfWriter pdfReader=PdfWriter.getInstance(document, new FileOutputStream(File));
		pdfReader.setViewerPreferences(PdfWriter.FitWindow);

		document.open();
		//addMetaData(document);
		generatePrescriptionInformarmation();
		//createTable();
		creatRxImage();
		createDrugLine();
		createRefill();
		if(!MainControl.getMainControl().isLocum()){
			System.out.println(MainControl.getMainControl().isLocum());
			createSignature();
		}
		createDate();
		System.out.println("File name is"+File);
		document.close();
		Desktop.getDesktop().open(new File(File));

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

	/**
	 * Used to generate the Prescription title and patient information
	 */
	private static void generatePrescriptionInformarmation()
			throws DocumentException {
		Paragraph prescriptionParagraph = new Paragraph();
		// We add one empty line
		addEmptyLine(prescriptionParagraph, 1);
		// Lets write a big header
		Paragraph prescriptionTitle=new Paragraph(""+MainControl.getMainControl().getPhysicianName()+"\n Clinic Name \n Clinic Address\n ClinicTell", titleFont);
		addEmptyLine(prescriptionTitle, 2);

		prescriptionTitle.setAlignment(Element.ALIGN_CENTER);
		prescriptionParagraph.add(prescriptionTitle);

		document.add(prescriptionParagraph);
		// Start a new page
		//document.newPage();

		Paragraph patientParagraph=new Paragraph("Name:"+patient.getName()+"\nAddress:"+patient.getAddress()+
				"\nMCP:"+patient.getMCP()+"\nPhone:"+patient.getTel(), smallBold);
		addEmptyLine(patientParagraph, 1);

		document.add(patientParagraph);
	}

	/**
	 * Generate drug line information fot the patient
	 */
	private static void createDrugLine() throws DocumentException{

		Paragraph grugInformation=new Paragraph();
		addEmptyLine(grugInformation,1);
		for (String s : prescription.getDrugLines()) {
			Paragraph drugline=new Paragraph(s);
			drugline.setIndentationLeft(100);
			grugInformation.add(drugline);
			System.out.println("Drug line to be added in the PDF "+s);

		}
		addEmptyLine(grugInformation,1);
		document.add(grugInformation);
	}

	/**
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws DocumentException
	 * Add the Rx image to the report
	 */
	private static void  creatRxImage() throws MalformedURLException, IOException, DocumentException{
		Image image = Image.getInstance("src/Rx.jpg");
		image.setAlignment(Element.ALIGN_LEFT);
		//image.setAbsolutePosition(500f, 650f);
		image.scaleAbsolute(100f, 75f);
		document.add(image);
	}

	/**
	 * @throws DocumentException
	 * create the drug line as table with 2 columns
	 * drug name and drug specefication
	 */
	private static void createTable() throws DocumentException {

		Paragraph tableParagraph=new Paragraph();
		PdfPTable table = new PdfPTable(2);

		PdfPCell c1 = new PdfPCell(new Phrase("Drug Name"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBorderColor(BaseColor.WHITE);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Drug Specification"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		table.setHeaderRows(1);

		for (String s : prescription.getDrugLines()) {
			String[] medicine=s.split(" ");
			Phrase medicineCell = new Phrase(medicine[0].toString(), tableFont);
			table.addCell(medicineCell);
			String medicine_spec="";
			for(int i=1; i<medicine.length;i++)
			{
				medicine_spec=medicine_spec+" "+medicine[i];
			}
			Phrase medicineSpecCell = new Phrase(medicine_spec.toString(), tableFont);
			table.addCell(medicineSpecCell);
		}
		tableParagraph.add(table);
		document.add(tableParagraph);
	}

	/**
	 * @throws DocumentException
	 * @throws IOException
	 * get the signature image from the database for the current physician 
	 * and add it to the report
	 */
	private static void createSignature() throws DocumentException, IOException{
		ByteArrayInputStream in = new ByteArrayInputStream(MainControl.getMainControl().getPatientManager().getSignature(MainControl.getMainControl().getPhysicianID()));
		BufferedImage bImageFromConvert = ImageIO.read(in);
		System.out.println("Before creating image file");
		ImageIO.write(bImageFromConvert, "jpg", new File("text.jpg"));
		//String.format("D:\","text.jpg");
		System.out.println("after creating image file");
		Image image = Image.getInstance("text.jpg");
		image.setAlignment(Element.ALIGN_RIGHT);
		//image.setAbsolutePosition(500f, 650f);
		image.scaleAbsolute(80f, 40f);

		document.add(image);
	}

	/**
	 * @throws DocumentException
	 * adding the effective date to the PDF report
	 */
	private static void createDate() throws DocumentException{
		Paragraph issueDate=new Paragraph("Date: "+MainControl.getMainControl().getPrescription().getEffectiveDate(),tableFont);
		issueDate.setAlignment(Element.ALIGN_RIGHT);
		document.add(issueDate);
	}
	
	private static void createRefill() throws DocumentException{
		Paragraph refillParagraph=new Paragraph("Refill: "+refill,new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.UNDERLINE));
		//addEmptyLine(refillParagraph,3);
		refillParagraph.setAlignment(Element.ALIGN_LEFT);
		refillParagraph.setIndentationLeft(5);
		document.add(refillParagraph);
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
} 