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
import com.nextech.erp.model.Client;
import com.nextech.erp.model.Productorder;
import com.nextech.erp.service.BomService;

public class CreatePdfForDispatchProduct {
	public static String answer = "";
	private static Font TIME_ROMAN = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
	private static Font TIME_ROMAN_SMALL = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private static float SUB_TOTAL = 0;
	private static float tax = 0;
	private static float total = 0;
	/**
	 * @param args
	 */
	
	@Autowired
	BomService bomService;

	public Document createPDF(String file,Productorder productorder, List<DispatchProductDTO> dispatchProductDTOs,Client client,DispatchDTO dispatchDTO)
			throws Exception {

		Document document = null;

		try {
			document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();

			addMetaData(document);

			addTitlePage(document,dispatchDTO);

			createTable(document, dispatchProductDTOs,productorder);

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

	private  void addTitlePage(Document document,DispatchDTO dispatchDTO)
			throws DocumentException {

		Paragraph preface = new Paragraph();
		creteEmptyLine(preface, 1);
		preface.add(new Paragraph("BOM INVOICE", TIME_ROMAN));
		
		   Font bf12 = new Font(FontFamily.TIMES_ROMAN, 20,Font.BOLD); 
		   Font bf112 = new Font(FontFamily.TIMES_ROMAN, 15,Font.BOLD); 
		   Font bf113 = new Font(FontFamily.TIMES_ROMAN, 12,Font.BOLD);
		   Font bf1 = new Font(FontFamily.TIMES_ROMAN, 10,Font.BOLD); 
		   
		  PdfPTable table00 = new PdfPTable(1);
		   table00.setWidthPercentage(100);
		   PdfPTable table1 = new PdfPTable(1);
		     table1.setWidthPercentage(100);
		     table1.addCell(getCell1("E.K.ELECTRONICS PVT.LTD", PdfPCell.ALIGN_CENTER,bf12));
		     table1.addCell(getCell("E-64 MIDC Industrial,Ranjangon Tal Shirur Dist pune-412220", PdfPCell.ALIGN_CENTER));
		     table1.addCell(getCell("Email:sachi@eksgpl.com/purchase@eksgpl.com", PdfPCell.ALIGN_CENTER));
		     table1.addCell(getCell1("DISPATCH PRODUCT", PdfPCell.ALIGN_LEFT,bf112));
		     table00.addCell(table1);
		     document.add(table00);
		     
		     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		     PdfPTable table0 = new PdfPTable(1);
		     table0.setWidthPercentage(100);
		     PdfPTable table = new PdfPTable(1); 
		     table.addCell(getCell1("DISPATCH INVOICE NUMBER : "+dispatchDTO.getInvoiceNo(), PdfPCell.ALIGN_LEFT,bf1));
		     table.addCell(getCell1("DISCRIPTION : "+dispatchDTO.getDescription(), PdfPCell.ALIGN_LEFT,bf1));
		     table.addCell(getCell1("DISPATCHED DATE : "+ simpleDateFormat.format(new Date()), PdfPCell.ALIGN_LEFT,bf1));
		     table0.addCell(table);
		     document.add(table0);

	}

	private static void creteEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	private void createTable(Document document, List<DispatchProductDTO> dispatchProductDTOs,Productorder productorder)
			throws Exception {
		
		Paragraph paragraph = new Paragraph();
		DecimalFormat df = new DecimalFormat("0.00");

		  Font bfBold12 = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0)); 
		   Font bf12 = new Font(FontFamily.TIMES_ROMAN, 12); 
		  //specify column widths
		   float[] columnWidths = {1.5f, 1.5f, 1.5f,1.5f};
		   //create PDF table with the given widths
		   PdfPTable table = new PdfPTable(columnWidths);
		   // set table width a percentage of the page width
		   table.setWidthPercentage(100f);

		   //insert column headings
		   insertCell(table, "PRODUCT PART NUMBER", Element.ALIGN_LEFT, 1, bfBold12);
		   insertCell(table, "CLIENT PART NUMBER", Element.ALIGN_LEFT, 1, bfBold12);
		   insertCell(table, "QUANTITY DISPATCHED", Element.ALIGN_LEFT, 1, bfBold12);
		   insertCell(table, "AMOUNT", Element.ALIGN_LEFT, 1, bfBold12);
		   table.setHeaderRows(1);
	
		 //  insertCell(table, "", Element.ALIGN_LEFT, 6, bfBold12);
		   //create section heading by cell merging
		 //  insertCell(table, "BOM DETAILS ...", Element.ALIGN_LEFT, 6, bfBold12);

     for (DispatchProductDTO dispatchProductDTO : dispatchProductDTOs) {
	    insertCell(table, dispatchProductDTO.getProductName(), Element.ALIGN_CENTER, 1, bf12);
	    insertCell(table, dispatchProductDTO.getClientPartNumber(), Element.ALIGN_CENTER, 1, bf12);
	    insertCell(table, (Long.toString(dispatchProductDTO.getQuantityDispatched())), Element.ALIGN_CENTER, 1, bf12);
	    insertCell(table, (Float.toString(dispatchProductDTO.getTotalCost())), Element.ALIGN_CENTER, 1, bf12);
	    SUB_TOTAL = SUB_TOTAL+dispatchProductDTO.getTotalCost();
	    tax = 18*SUB_TOTAL;
	     tax =tax/100;
	     
    }
     total = tax+SUB_TOTAL;
     insertCell(table, "Sub Total", Element.ALIGN_RIGHT, 3, bfBold12);
     insertCell(table, Float.toString(SUB_TOTAL), Element.ALIGN_CENTER, 1, bfBold12);
     insertCell(table, "Tax", Element.ALIGN_RIGHT, 3, bfBold12);
     insertCell(table, Float.toString(tax), Element.ALIGN_CENTER, 1, bfBold12);
     insertCell(table, "Total", Element.ALIGN_RIGHT, 3, bfBold12);
     insertCell(table, Float.toString(total), Element.ALIGN_CENTER, 1, bfBold12);
     document.add(table);
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
