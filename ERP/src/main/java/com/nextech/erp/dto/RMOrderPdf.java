package com.nextech.erp.dto;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.nextech.erp.model.Rawmaterialorder;
import com.nextech.erp.model.Vendor;
import com.nextech.erp.newDTO.VendorDTO;
import com.nextech.erp.service.ProductorderService;
import com.nextech.erp.service.ProductorderassociationService;

public class RMOrderPdf {
	private static Font TIME_ROMAN = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
	private static Font TIME_ROMAN_SMALL = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	public  float grandtotal = 0;
	private static float tax= 0;
	public double igstTotal =0;
	public double cgstTotal = 0;
	public double sgstTotal =0;
	public String igst ="";
	public String cgst ="";
	public String sgst ="";
	public String subtoatl ="";

	/**
	 * @param args
	 */
	
	@Autowired
	ProductorderassociationService productorderassociationService;
	
	@Autowired
	ProductorderService productorderService;

	public Document createPDF(String file,RawmaterialOrderDTO rawmaterialOrderDTO,List<RMOrderModelData> rmOrderModelDatas,VendorDTO vendor)
			throws Exception {

		Document document = null;

		try {
			document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();

			addMetaData(document);

			addTitlePage(document,vendor,rawmaterialOrderDTO);

			createTable(document, rawmaterialOrderDTO,rmOrderModelDatas);

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

	private  void addTitlePage(Document document,VendorDTO vendor,RawmaterialOrderDTO rawmaterialOrderDTO)
			throws DocumentException {
		Paragraph preface = new Paragraph();
		   Font bf12 = new Font(FontFamily.TIMES_ROMAN, 20,Font.BOLD); 
		   Font bf112 = new Font(FontFamily.TIMES_ROMAN, 15,Font.BOLD); 
		   Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
		   Font bf = new Font(FontFamily.TIMES_ROMAN, 12,Font.BOLD); 
		  //specify column widths
		   float[] columnWidths = {5f};
		   //create PDF table with the given widths
		   PdfPTable table = new PdfPTable(columnWidths);
		   // set table width a percentage of the page width
		   PdfPCell cell =  new PdfPCell();
		  
		   creteEmptyLine(preface, 2);
		   document.add(preface);
		   
		   PdfPTable fromEKTable = new PdfPTable(1);
		   fromEKTable.setWidthPercentage(100);
		   PdfPTable ekTable = new PdfPTable(1);
		   ekTable.setWidthPercentage(100);
		   ekTable.addCell(getCell1("E.K.ELECTRONICS PVT.LTD", PdfPCell.ALIGN_CENTER,bf12));
		   ekTable.addCell(getCell("E-64 MIDC Industrial,Ranjangon Tal Shirur Dist pune-412220", PdfPCell.ALIGN_CENTER));
		   ekTable.addCell(getCell("Email:sachi@eksgpl.com/purchase@eksgpl.com", PdfPCell.ALIGN_CENTER));
		   ekTable.addCell(getCell1("PURCHASE ORDER", PdfPCell.ALIGN_CENTER,bf112));
		     fromEKTable.addCell(ekTable);
		     document.add(fromEKTable);
		
		     PdfPTable table0 = new PdfPTable(2);
		     table0.setWidthPercentage(100);
		     
		     PdfPTable vendorInfoTable = new PdfPTable(1);
		     vendorInfoTable.setWidthPercentage(100);
		     vendorInfoTable.addCell(getCell2(vendor.getCompanyName(), PdfPCell.ALIGN_LEFT,font3));
		     vendorInfoTable.addCell(getCell2(vendor.getAddress(), PdfPCell.ALIGN_LEFT,font3));
		    
		     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		     String  vat = vendor.getVatNo();
		     String cst =vendor.getCst();
		     String ecc =vendor.getCustomerEccNumber();
		     PdfPTable rmOrderTabel = new PdfPTable(1);
		     rmOrderTabel.setWidthPercentage(100);
		     rmOrderTabel.addCell(getCell2("P.O.No:"+rawmaterialOrderDTO.getName(), PdfPCell.ALIGN_LEFT,bf));
		     rmOrderTabel.addCell(getCell2("Date :"+ simpleDateFormat.format(new Date()), PdfPCell.ALIGN_LEFT,bf));
		     rmOrderTabel.addCell(getCell2("VAT TIN NO :"+vat, PdfPCell.ALIGN_LEFT,bf));
		     rmOrderTabel.addCell(getCell2("CST TIN NO :"+cst, PdfPCell.ALIGN_LEFT,bf));
		     rmOrderTabel.addCell(getCell2("ECC NO :"+ecc, PdfPCell.ALIGN_LEFT,bf));
		  
		     table0.addCell(vendorInfoTable);
		     table0.addCell(rmOrderTabel);
		     
		     document.add(table0);
		     

	}

	private static void creteEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	private void createTable(Document document,RawmaterialOrderDTO RawmaterialOrderDTO,List<RMOrderModelData> rmOrderModelDatas)
			throws Exception {

		  Paragraph paragraph = new Paragraph();
	/*	 creteEmptyLine(paragraph, 2);
		 document.add(paragraph);*/
		  DecimalFormat df = new DecimalFormat("0.00");
		  Font bf123 = new Font(FontFamily.TIMES_ROMAN, 14,Font.BOLD); 
		  Font bf112 = new Font(FontFamily.TIMES_ROMAN, 10,Font.BOLD); 
		  Font bfBold12 = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0)); 
		  Font bfBold = new Font(FontFamily.TIMES_ROMAN, 8, Font.BOLD, new BaseColor(0, 0, 0));
		   Font bf12 = new Font(FontFamily.TIMES_ROMAN, 12); 
		   Font bf1 = new Font(FontFamily.TIMES_ROMAN, 10); 
		  //specify column widths
		   float[] columnWidths = {1.8f, 1.1f, 1.0f, 1.9f,1.9f,1.9f,1.0f};
		   //create PDF table with the given widths
		   PdfPTable table = new PdfPTable(columnWidths);
		   // set table width a percentage of the page width
		   table.setWidthPercentage(100f);

		   insertCell(table, "Item Description", Element.ALIGN_RIGHT, 1, bfBold12);
		   insertCell(table, "Quantity", Element.ALIGN_LEFT, 1, bfBold12);
		   insertCell(table, "Rate", Element.ALIGN_LEFT, 1, bfBold12);
		   
		   PdfPTable perAmoutCGSTTable =  new PdfPTable(2);
		   PdfPCell cgstCell = new PdfPCell(new Phrase("CGST", bfBold12));
		   cgstCell.setColspan(2);
		   cgstCell.setRowspan(2);
		   perAmoutCGSTTable.addCell(cgstCell);
		   perAmoutCGSTTable.addCell(new Phrase("Percentage",bfBold));
		   perAmoutCGSTTable.addCell(new Phrase("Amount",bfBold));
		   table.addCell(perAmoutCGSTTable);
		   
		   PdfPTable pdfPTable1 =  new PdfPTable(2);
		   PdfPCell cell1 = new PdfPCell(new Phrase("SGST", bfBold12));
		   cell1.setColspan(2);
		   cell1.setRowspan(2);
		   pdfPTable1.addCell(cell1);
		   pdfPTable1.addCell(new Phrase("Percentage",bfBold));
		   pdfPTable1.addCell(new Phrase("Amount",bfBold));
		   table.addCell(pdfPTable1);
		   
		   PdfPTable pdfPTable2 =  new PdfPTable(2);
		   PdfPCell cell2 = new PdfPCell(new Phrase("IGST", bfBold12));
		   cell2.setColspan(2);
		   cell2.setRowspan(2);
		   pdfPTable2.addCell(cell2);
		   pdfPTable2.addCell(new Phrase("Percentage",bfBold));
		   pdfPTable2.addCell(new Phrase("Amount",bfBold));
		   table.addCell(pdfPTable2);
		   insertCell(table, "Amount", Element.ALIGN_LEFT, 1, bfBold12);
		   table.setHeaderRows(1);

     for (RMOrderModelData rmOrderModelData : rmOrderModelDatas) {
	  insertCell(table,rmOrderModelData.getRmName() , Element.ALIGN_RIGHT, 1, bf1);
	    insertCell(table,(Long.toString(rmOrderModelData.getQuantity())), Element.ALIGN_RIGHT, 1, bf1);
	    insertCell(table, (Float.toString(rmOrderModelData.getPricePerUnit())), Element.ALIGN_RIGHT, 1, bf1);
	    
	    double cgstTax = rmOrderModelData.getCgst();
	     cgstTax = cgstTax*rmOrderModelData.getPricePerUnit();
	     cgstTax = cgstTax/100;
	     cgstTotal  =  cgstTotal+cgstTax;
	     double d2 =cgstTotal;
	     cgst= String.format("%.02f", d2);
	     PdfPTable perAccountGSTTable = new PdfPTable(2);
	     perAccountGSTTable.setWidthPercentage(100);
	     PdfPTable perCST = new PdfPTable(1);
	     perCST.addCell(getCell1(""+rmOrderModelData.getCgst(), PdfPCell.ALIGN_LEFT,bf1));
	     
	     PdfPTable accountCGSTTable = new PdfPTable(1);
	     accountCGSTTable.addCell(getCell1(""+cgstTax, PdfPCell.ALIGN_LEFT,bf1));
	     perAccountGSTTable.addCell(perCST);
	     perAccountGSTTable.addCell(accountCGSTTable);
	     table.addCell(perAccountGSTTable);
	    
	    double sgstTax = rmOrderModelData.getSgst();
	    sgstTax = sgstTax*rmOrderModelData.getPricePerUnit();
	    sgstTax = sgstTax/100;
	    sgstTotal = sgstTotal+sgstTax;
	    
	    double d1 =sgstTotal;
	    sgst= String.format("%.02f", d1);
	    PdfPTable perAccountSGSTTable = new PdfPTable(2);
	    perAccountSGSTTable.setWidthPercentage(100);
	     PdfPTable perSGSTTable = new PdfPTable(1);
	     perSGSTTable.addCell(getCell1(""+rmOrderModelData.getSgst(), PdfPCell.ALIGN_LEFT,bf1));
	     
	     PdfPTable pdtable133 = new PdfPTable(1);
	     pdtable133.addCell(getCell1(""+sgstTax, PdfPCell.ALIGN_LEFT,bf1));
	     perAccountSGSTTable.addCell(perSGSTTable);
	     perAccountSGSTTable.addCell(pdtable133);
	     table.addCell(perAccountSGSTTable);
	    
	    double igstTax = rmOrderModelData.getIgst();
	    igstTax = igstTax*rmOrderModelData.getPricePerUnit();
	    igstTax = igstTax/100;
	    igstTotal = igstTotal+igstTax;
	    double d =igstTotal;
	    igst= String.format("%.02f", d);
	    PdfPTable perAccountIGSTTable = new PdfPTable(2);
	    perAccountIGSTTable.setWidthPercentage(100);
	     PdfPTable perIGSTTable = new PdfPTable(1);
	     perIGSTTable.addCell(getCell1(""+rmOrderModelData.getIgst(), PdfPCell.ALIGN_LEFT,bf1));
	     
	     PdfPTable pdtable143 = new PdfPTable(1);
	     pdtable143.addCell(getCell1(""+igstTax, PdfPCell.ALIGN_LEFT,bf1));
	     perAccountIGSTTable.addCell(perIGSTTable);
	     perAccountIGSTTable.addCell(pdtable143);
	     table.addCell(perAccountIGSTTable);	
	    
	    insertCell(table, (Float.toString(rmOrderModelData.getAmount())), Element.ALIGN_RIGHT, 1, bf1);
	    grandtotal = grandtotal+rmOrderModelData.getAmount();
	    tax = rmOrderModelData.getTax();
	    double d3 =grandtotal;
	    subtoatl= String.format("%.02f", d3);
    }
     insertCell(table, "CGST TOTAL", Element.ALIGN_RIGHT, 6, bfBold12);
     insertCell(table, cgst, Element.ALIGN_RIGHT, 1, bfBold12);
     insertCell(table, "SGST Total", Element.ALIGN_RIGHT, 6, bfBold12);
     insertCell(table, sgst, Element.ALIGN_RIGHT, 1, bfBold12);
     insertCell(table, "IGST Total", Element.ALIGN_RIGHT, 6, bfBold12);
     insertCell(table, igst, Element.ALIGN_RIGHT, 1, bfBold12);
     insertCell(table, "Total", Element.ALIGN_RIGHT, 6, bfBold12);
     int totalTax= (int) (cgstTotal+igstTotal+sgstTotal);
     Integer intValue = (int) Float.parseFloat(subtoatl);
     grandtotal = (int) (totalTax+intValue);
     insertCell(table, (Float.toString(grandtotal)), Element.ALIGN_RIGHT, 1, bfBold12);
     document.add(table);
     PdfPTable termsAndCondtionTable = new PdfPTable(2);
     termsAndCondtionTable.setWidthPercentage(100);
     
     PdfPTable termsTable = new PdfPTable(1);
     termsTable.setWidthPercentage(30);
     termsTable.addCell(getCell1("Terms & Conditions", PdfPCell.ALIGN_LEFT,bf123));
     termsTable.addCell(getCell2("Payment Terms", PdfPCell.ALIGN_LEFT,bf12));
     termsTable.addCell(getCell2("Inspection", PdfPCell.ALIGN_LEFT,bf12));
     termsTable.addCell(getCell2("Sale Tax ", PdfPCell.ALIGN_LEFT,bf12));
     termsTable.addCell(getCell2("Excise Duty ", PdfPCell.ALIGN_LEFT,bf12));
     termsTable.addCell(getCell2("Delivery ", PdfPCell.ALIGN_LEFT,bf12));
     termsTable.addCell(getCell2("freight ", PdfPCell.ALIGN_LEFT,bf12));
     termsTable.addCell(getCell1("Quality ", PdfPCell.ALIGN_LEFT,bf123));
     termsTable.addCell(getCell2(" ", PdfPCell.ALIGN_LEFT,bf12));
     termsTable.addCell(getCell2(" ", PdfPCell.ALIGN_LEFT,bf12));
     termsTable.addCell(getCell2(" ", PdfPCell.ALIGN_LEFT,bf12));
     termsTable.addCell(getCell2(" ", PdfPCell.ALIGN_LEFT,bf12));
     termsTable.addCell(getCell1("Note ", PdfPCell.ALIGN_LEFT,bf123));
     termsTable.addCell(getCell2("1. Please send duplicate bill mentioned with our P.O and item code ", PdfPCell.ALIGN_LEFT,bf12));
     termsTable.addCell(getCell2("2. Bill & challan must be issued in favor of M/s EK Electronics pvt.ltd. at above address ", PdfPCell.ALIGN_LEFT,bf12));
     termsTable.addCell(getCell2("3. Please make sure all material should be rohs compliance", PdfPCell.ALIGN_LEFT,bf12));
     
     PdfPTable noteTable = new PdfPTable(1);
     noteTable.setWidthPercentage(70);
     noteTable.addCell(getCell2(" ", PdfPCell.ALIGN_LEFT,bf12));
     noteTable.addCell(getCell2("60 Days on receipt of matrial", PdfPCell.ALIGN_LEFT,bf12));
     noteTable.addCell(getCell2("At our works after receipt of material", PdfPCell.ALIGN_LEFT,bf12));
     noteTable.addCell(getCell2("As applicable", PdfPCell.ALIGN_LEFT,bf12));
     noteTable.addCell(getCell2("As applicable ", PdfPCell.ALIGN_LEFT,bf12));
     noteTable.addCell(getCell2("Excise Duty ", PdfPCell.ALIGN_LEFT,bf12));
     noteTable.addCell(getCell2("At our Ranjangon factory", PdfPCell.ALIGN_LEFT,bf12));
     noteTable.addCell(getCell2("Nil ", PdfPCell.ALIGN_LEFT,bf12));
     noteTable.addCell(getCell2("Supply material of high quality only to avoid rejection ", PdfPCell.ALIGN_LEFT,bf12));
     noteTable.addCell(getCell2("1)PDIR REPORT ", PdfPCell.ALIGN_LEFT,bf112));
     noteTable.addCell(getCell2("2)MATERIAL TEST REPORT ", PdfPCell.ALIGN_LEFT,bf112));
     noteTable.addCell(getCell2("3)ROHS ", PdfPCell.ALIGN_LEFT,bf112));
     noteTable.addCell(getCell2("4)MSDS ", PdfPCell.ALIGN_LEFT,bf112));
     
     termsAndCondtionTable.addCell(termsTable);
     termsAndCondtionTable.addCell(noteTable);
     document.add(termsAndCondtionTable);
     
     
     PdfPTable table11 = new PdfPTable(1);
     table11.setWidthPercentage(100);
     
    PdfPTable table1 = new PdfPTable(1);
     table1.setWidthPercentage(100);
     table1.addCell(getCell12("For EK Electronics Pvt.Ltd", PdfPCell.ALIGN_RIGHT,bf123));
     table1.addCell(getCell12("Authorised Signature", PdfPCell.ALIGN_RIGHT,bf123));
     table11.addCell(table1);
     document.add(table11);
    
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
	 
	 public PdfPCell getCell3(String text, int alignment) {
		    PdfPCell cell = new PdfPCell(new Phrase(text));
		    cell.setExtraParagraphSpace(4);
		    cell.setVerticalAlignment(alignment);
		    cell.setBorder(PdfPCell.NO_BORDER);
		    return cell;
		}
	 
	 public PdfPCell getCell12(String text, int alignment,Font font) {
		    PdfPCell cell = new PdfPCell(new Phrase(text,font));
		    cell.setPadding(0);
		    cell.setExtraParagraphSpace(30);
		    cell.setHorizontalAlignment(alignment);
		    cell.setBorder(PdfPCell.NO_BORDER);
		    return cell;
		}
}	
