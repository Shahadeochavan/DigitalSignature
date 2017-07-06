package com.nextech.erp.dto;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.nextech.erp.service.BomService;

public class CreatePdfForBomProduct {
	public static String answer = "";
	private static Font TIME_ROMAN = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
	private static Font TIME_ROMAN_SMALL = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private static int total = 0;
	private static float tax = 0;
	/**
	 * @param args
	 */
	
	@Autowired
	BomService bomService;

	public Document createPDF(String file, List<BomRMVendorModel> bomRMVendorModels,ProductBomDTO productBomDTO)
			throws Exception {

		Document document = null;

		try {
			document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();

			addMetaData(document);

			addTitlePage(document,productBomDTO);

			createTable(document, bomRMVendorModels);

			document.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return document;

	}

	private static void addMetaData(Document document) {
		document.addTitle("Generate PDF report");
		document.addSubject("Generate PDF report");
		document.addAuthor("Java Honk");
		document.addCreator("Java Honk");
	}

	private  void addTitlePage(Document document,ProductBomDTO productBomDTO)
			throws DocumentException {

		Paragraph preface = new Paragraph();
		creteEmptyLine(preface, 1);
		preface.add(new Paragraph("BOM INVOICE", TIME_ROMAN));
		
		   Font bf12 = new Font(FontFamily.TIMES_ROMAN, 20,Font.BOLD); 
		   Font bf112 = new Font(FontFamily.TIMES_ROMAN, 15,Font.BOLD); 
		   Font bf113 = new Font(FontFamily.TIMES_ROMAN, 12,Font.BOLD); 
		   
		  PdfPTable table00 = new PdfPTable(1);
		   table00.setWidthPercentage(100);
		   PdfPTable table1 = new PdfPTable(1);
		     table1.setWidthPercentage(100);
		     table1.addCell(getCell1("E.K.ELECTRONICS PVT.LTD", PdfPCell.ALIGN_CENTER,bf12));
		     table1.addCell(getCell("E-64 MIDC Industrial,Ranjangon Tal Shirur Dist pune-412220", PdfPCell.ALIGN_CENTER));
		     table1.addCell(getCell("Email:sachi@eksgpl.com/purchase@eksgpl.com", PdfPCell.ALIGN_CENTER));
		     table1.addCell(getCell1("BILL OF MATERIAL(BOM)", PdfPCell.ALIGN_LEFT,bf112));
		     table00.addCell(table1);
		     document.add(table00);
		     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		     PdfPTable table12 = new PdfPTable(1);
		      table12.setWidthPercentage(100);
			   PdfPTable table = new PdfPTable(1);
			   table.addCell(getCell1("PRODUCT PART NUMBER :"+productBomDTO.getProductPartNumber(), PdfPCell.ALIGN_LEFT,bf113));
			   table.addCell(getCell1("CLIENT PART NUMBER :"+productBomDTO.getClinetPartNumber(), PdfPCell.ALIGN_LEFT,bf113));
			   table.addCell(getCell1("BOM CREATED DATE :"+ simpleDateFormat.format(productBomDTO.getCreatedDate()), PdfPCell.ALIGN_LEFT,bf113));
			   table12.addCell(table);
			   document.add(table12);

	}

	private static void creteEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	private void createTable(Document document, List<BomRMVendorModel> bomRMVendorModels)
			throws Exception {
		
		Paragraph paragraph = new Paragraph();
		DecimalFormat df = new DecimalFormat("0.00");

		  Font bfBold12 = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0)); 
		   Font bf12 = new Font(FontFamily.TIMES_ROMAN, 12); 
		  //specify column widths
		   float[] columnWidths = {1.5f, 1.5f, 1.5f, 1.5f,1.5f};
		   //create PDF table with the given widths
		   PdfPTable table = new PdfPTable(columnWidths);
		   // set table width a percentage of the page width
		   table.setWidthPercentage(100f);

		   //insert column headings
		   insertCell(table, "RM NAME", Element.ALIGN_RIGHT, 1, bfBold12);
		   insertCell(table, "VENDOR NAME", Element.ALIGN_LEFT, 1, bfBold12);
		   insertCell(table, "PRICE PER UNIT", Element.ALIGN_LEFT, 1, bfBold12);
		   insertCell(table, "QUANTITY", Element.ALIGN_LEFT, 1, bfBold12);
		   insertCell(table, "AMOUNT", Element.ALIGN_RIGHT, 1, bfBold12);
		   table.setHeaderRows(1);
	
		 //  insertCell(table, "", Element.ALIGN_LEFT, 6, bfBold12);
		   //create section heading by cell merging
		 //  insertCell(table, "BOM DETAILS ...", Element.ALIGN_LEFT, 6, bfBold12);

     for (BomRMVendorModel bomRMVendorModel : bomRMVendorModels) {
	  insertCell(table,bomRMVendorModel.getDescription() , Element.ALIGN_CENTER, 1, bf12);
	    insertCell(table, bomRMVendorModel.getVendorName(), Element.ALIGN_CENTER, 1, bf12);
	    insertCell(table, (Float.toString(bomRMVendorModel.getPricePerUnit())), Element.ALIGN_CENTER, 1, bf12);
	    insertCell(table, (Long.toString(bomRMVendorModel.getQuantity())), Element.ALIGN_CENTER, 1, bf12);
	    insertCell(table, (Float.toString(bomRMVendorModel.getAmount())), Element.ALIGN_CENTER, 1, bf12);
	    total = (int) (total+bomRMVendorModel.getAmount());
	    tax = 18*total;
	     tax =tax/100;
    }
     total = (int) (total+tax);
     insertCell(table, "Other Charges", Element.ALIGN_CENTER, 4, bfBold12);
     insertCell(table, "  ", Element.ALIGN_CENTER, 1, bfBold12);
     insertCell(table, "Tax", Element.ALIGN_CENTER, 4, bfBold12);
     insertCell(table, Float.toString(tax), Element.ALIGN_CENTER, 1, bfBold12);
     insertCell(table, "Total", Element.ALIGN_CENTER, 4, bfBold12);
     insertCell(table, df.format(total), Element.ALIGN_CENTER, 1, bfBold12);
		if(total <= 0)   {                
			System.out.println("Enter numbers greater than 0");
		} else {
			CreatePdfForBomProduct a = new CreatePdfForBomProduct();
			a.pw((total/1000000000)," Hundred");
			a.pw((total/10000000)%100," Crore");
			a.pw(((total/100000)%100)," Lakh");
			a.pw(((total/1000)%100)," Thousand");
			a.pw(((total/100)%10)," Hundred");
			a.pw((total%100)," ");
			answer = answer.trim();
			 System.out.println("Final Answer : " +answer);
		   insertCell(table, answer, Element.ALIGN_LEFT, 4, bfBold12);
           document.add(table);
	}
	}
	 private void insertCell(PdfPTable table, String text, int align, int colspan, Font font){
	  
	  //create a new cell with the specified Text and Font
	  PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
	  //set the cell alignment
	  cell.setHorizontalAlignment(align);
	  //set the cell column span in case you want to merge two or more cells
	  cell.setColspan(colspan);
	  //in case there is no text and you wan to create an empty row
	  if(text.trim().equalsIgnoreCase("")){
	   cell.setMinimumHeight(15f);
	  }
	  //add the call to the table
	  table.addCell(cell);
	  
	 }
	 
	 public PdfPCell getCell(String text, int alignment) {
		    PdfPCell cell = new PdfPCell(new Phrase(text));
		    cell.setPadding(0);
		    cell.setExtraParagraphSpace(1);
		    cell.setHorizontalAlignment(alignment);
		    cell.setBorder(PdfPCell.NO_BORDER);
		    return cell;
		}
	 
	 public PdfPCell getCell1(String text, int alignment,Font bf12) {
		    PdfPCell cell = new PdfPCell(new Phrase(text,bf12));
		    cell.setPadding(0);
		    cell.setHorizontalAlignment(alignment);
		    cell.setBorder(PdfPCell.NO_BORDER);
		    return cell;
		}
	 public PdfPCell getCell2(String text, int alignment,Font font) {
		    PdfPCell cell = new PdfPCell(new Phrase(text));
		    cell.setPadding(0);
		    cell.setExtraParagraphSpace(1);
		    cell.setVerticalAlignment(alignment);
		    cell.setBorder(PdfPCell.NO_BORDER);
		    return cell;
		}
		public void pw(int n,String ch)
		{
			String  one[]={" "," one"," two"," three"," four"," five"," six"," seven"," eight"," Nine"," ten"," eleven"," twelve"," thirteen"," fourteen","fifteen"," sixteen"," seventeen"," eighteen"," nineteen"};
			String ten[]={" "," "," twenty"," thirty"," forty"," fifty"," sixty","seventy"," eighty"," ninety"};

			if(n > 19) {
				answer += ten[n/10]+" "+one[n%10];
			} else { 
				String S=one[n];
				answer += S;
			}

			if(n > 0) {
				answer += ch;
			}
		}
}
