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

public class CreatePDFProductOrder {
	private static Font TIME_ROMAN = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
	private static Font TIME_ROMAN_SMALL = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	public  float grandtotal = 0;
	private static float tax= 0;
	public double igstTotal =0;
	public double cgstTotal = 0;
	public double sgstTotal =0;

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
		   
		   PdfPTable table00 = new PdfPTable(1);
		   table00.setWidthPercentage(100);
		   PdfPTable table1 = new PdfPTable(1);
		     table1.setWidthPercentage(100);
		     table1.addCell(getCell1("E.K.ELECTRONICS PVT.LTD", PdfPCell.ALIGN_CENTER,bf12));
		     table1.addCell(getCell("E-64 MIDC Industrial,Ranjangon Tal Shirur Dist pune-412220", PdfPCell.ALIGN_CENTER));
		     table1.addCell(getCell("Email:sachi@eksgpl.com/purchase@eksgpl.com", PdfPCell.ALIGN_CENTER));
		     table1.addCell(getCell1("PURCHASE ORDER", PdfPCell.ALIGN_CENTER,bf112));
		     table00.addCell(table1);
		     document.add(table00);
		
		     PdfPTable table0 = new PdfPTable(2);
		     table0.setWidthPercentage(100);
		     
		     PdfPTable table5 = new PdfPTable(1);
		     table5.setWidthPercentage(100);
		     table5.addCell(getCell2(vendor.getCompanyName(), PdfPCell.ALIGN_LEFT,font3));
		     table5.addCell(getCell2(vendor.getAddress(), PdfPCell.ALIGN_LEFT,font3));
		    
		     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		     String  vat = vendor.getVatNo();
		     String cst =vendor.getCst();
		     String ecc =vendor.getCustomerEccNumber();
		     PdfPTable table6 = new PdfPTable(1);
		     table6.setWidthPercentage(100);
		     table6.addCell(getCell2("P.O.No:"+rawmaterialOrderDTO.getName(), PdfPCell.ALIGN_LEFT,bf));
		     table6.addCell(getCell2("Date :"+ simpleDateFormat.format(new Date()), PdfPCell.ALIGN_LEFT,bf));
		     table6.addCell(getCell2("VAT TIN NO :"+vat, PdfPCell.ALIGN_LEFT,bf));
		     table6.addCell(getCell2("CST TIN NO :"+cst, PdfPCell.ALIGN_LEFT,bf));
		     table6.addCell(getCell2("ECC NO :"+ecc, PdfPCell.ALIGN_LEFT,bf));
		  
		     table0.addCell(table5);
		     table0.addCell(table6);
		     
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

		   //insert column headings
		   insertCell(table, "Item Description", Element.ALIGN_RIGHT, 1, bfBold12);
		   insertCell(table, "Quantity", Element.ALIGN_LEFT, 1, bfBold12);
		   insertCell(table, "Rate", Element.ALIGN_LEFT, 1, bfBold12);
		   
		   PdfPTable pdfPTable =  new PdfPTable(2);
		   PdfPCell cell = new PdfPCell(new Phrase("CGST", bfBold12));
		   cell.setColspan(2);
		   cell.setRowspan(2);
		   pdfPTable.addCell(cell);
		   pdfPTable.addCell(new Phrase("Percentage",bfBold));
		   pdfPTable.addCell(new Phrase("Amount",bfBold));
		   table.addCell(pdfPTable);
		   
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
	     PdfPTable table13 = new PdfPTable(2);
	     table13.setWidthPercentage(100);
	     PdfPTable pdtable = new PdfPTable(1);
	     pdtable.addCell(getCell1(""+rmOrderModelData.getCgst(), PdfPCell.ALIGN_LEFT,bf1));
	     
	     PdfPTable pdtable1 = new PdfPTable(1);
	     pdtable1.addCell(getCell1(""+cgstTax, PdfPCell.ALIGN_LEFT,bf1));
	     table13.addCell(pdtable);
	     table13.addCell(pdtable1);
	     table.addCell(table13);
	    
	    double sgstTax = rmOrderModelData.getSgst();
	    sgstTax = sgstTax*rmOrderModelData.getPricePerUnit();
	    sgstTax = sgstTax/100;
	    sgstTotal = sgstTotal+sgstTax;
	    PdfPTable table131 = new PdfPTable(2);
	    table131.setWidthPercentage(100);
	     PdfPTable pdtable132 = new PdfPTable(1);
	     pdtable132.addCell(getCell1(""+rmOrderModelData.getSgst(), PdfPCell.ALIGN_LEFT,bf1));
	     
	     PdfPTable pdtable133 = new PdfPTable(1);
	     pdtable133.addCell(getCell1(""+sgstTax, PdfPCell.ALIGN_LEFT,bf1));
	     table131.addCell(pdtable132);
	     table131.addCell(pdtable133);
	     table.addCell(table131);
	    
	    double igstTax = rmOrderModelData.getIgst();
	    igstTax = igstTax*rmOrderModelData.getPricePerUnit();
	    igstTax = igstTax/100;
	    igstTotal = igstTotal+igstTax;
	    PdfPTable table141 = new PdfPTable(2);
	      table141.setWidthPercentage(100);
	     PdfPTable pdtable142 = new PdfPTable(1);
	     pdtable142.addCell(getCell1(""+rmOrderModelData.getIgst(), PdfPCell.ALIGN_LEFT,bf1));
	     
	     PdfPTable pdtable143 = new PdfPTable(1);
	     pdtable143.addCell(getCell1(""+igstTax, PdfPCell.ALIGN_LEFT,bf1));
	     table141.addCell(pdtable142);
	     table141.addCell(pdtable143);
	     table.addCell(table141);	
	    
	    insertCell(table, (Float.toString(rmOrderModelData.getAmount())), Element.ALIGN_RIGHT, 1, bf1);
	    grandtotal = grandtotal+rmOrderModelData.getAmount();
	    tax = rmOrderModelData.getTax();
    }
     insertCell(table, "CGST TOTAL", Element.ALIGN_RIGHT, 6, bfBold12);
     insertCell(table, Double.toString(cgstTotal), Element.ALIGN_RIGHT, 1, bfBold12);
     insertCell(table, "SGST Total", Element.ALIGN_RIGHT, 6, bfBold12);
     insertCell(table, Double.toString(sgstTotal), Element.ALIGN_RIGHT, 1, bfBold12);
     insertCell(table, "IGST Total", Element.ALIGN_RIGHT, 6, bfBold12);
     insertCell(table, Double.toString(igstTotal), Element.ALIGN_RIGHT, 1, bfBold12);
     insertCell(table, "Total", Element.ALIGN_RIGHT, 6, bfBold12);
     int totalTax= (int) (cgstTotal+igstTotal+sgstTotal);
     grandtotal = (int) (totalTax+grandtotal);
     insertCell(table, (Float.toString(grandtotal)), Element.ALIGN_RIGHT, 1, bfBold12);
     document.add(table);
     PdfPTable table00 = new PdfPTable(2);
     table00.setWidthPercentage(100);
     
     PdfPTable table5 = new PdfPTable(1);
     table5.setWidthPercentage(30);
     table5.addCell(getCell1("Terms & Conditions", PdfPCell.ALIGN_LEFT,bf123));
     table5.addCell(getCell2("Payment Terms", PdfPCell.ALIGN_LEFT,bf12));
     table5.addCell(getCell2("Inspection", PdfPCell.ALIGN_LEFT,bf12));
     table5.addCell(getCell2("Sale Tax ", PdfPCell.ALIGN_LEFT,bf12));
     table5.addCell(getCell2("Excise Duty ", PdfPCell.ALIGN_LEFT,bf12));
     table5.addCell(getCell2("Delivery ", PdfPCell.ALIGN_LEFT,bf12));
     table5.addCell(getCell2("freight ", PdfPCell.ALIGN_LEFT,bf12));
     table5.addCell(getCell1("Quality ", PdfPCell.ALIGN_LEFT,bf123));
     table5.addCell(getCell2(" ", PdfPCell.ALIGN_LEFT,bf12));
     table5.addCell(getCell2(" ", PdfPCell.ALIGN_LEFT,bf12));
     table5.addCell(getCell2(" ", PdfPCell.ALIGN_LEFT,bf12));
     table5.addCell(getCell2(" ", PdfPCell.ALIGN_LEFT,bf12));
     table5.addCell(getCell1("Note ", PdfPCell.ALIGN_LEFT,bf123));
     table5.addCell(getCell2("1. Please send duplicate bill mentioned with our P.O and item code ", PdfPCell.ALIGN_LEFT,bf12));
     table5.addCell(getCell2("2. Bill & challan must be issued in favor of M/s EK Electronics pvt.ltd. at above address ", PdfPCell.ALIGN_LEFT,bf12));
     table5.addCell(getCell2("3. Please make sure all material should be rohs compliance", PdfPCell.ALIGN_LEFT,bf12));
     
     PdfPTable table6 = new PdfPTable(1);
     table6.setWidthPercentage(70);
     table6.addCell(getCell2(" ", PdfPCell.ALIGN_LEFT,bf12));
     table6.addCell(getCell2("60 Days on receipt of matrial", PdfPCell.ALIGN_LEFT,bf12));
     table6.addCell(getCell2("At our works after receipt of material", PdfPCell.ALIGN_LEFT,bf12));
     table6.addCell(getCell2("As applicable", PdfPCell.ALIGN_LEFT,bf12));
     table6.addCell(getCell2("As applicable ", PdfPCell.ALIGN_LEFT,bf12));
     table6.addCell(getCell2("Excise Duty ", PdfPCell.ALIGN_LEFT,bf12));
     table6.addCell(getCell2("At our Ranjangon factory", PdfPCell.ALIGN_LEFT,bf12));
     table6.addCell(getCell2("Nil ", PdfPCell.ALIGN_LEFT,bf12));
     table6.addCell(getCell2("Supply material of high quality only to avoid rejection ", PdfPCell.ALIGN_LEFT,bf12));
     table6.addCell(getCell2("1)PDIR REPORT ", PdfPCell.ALIGN_LEFT,bf112));
     table6.addCell(getCell2("2)MATERIAL TEST REPORT ", PdfPCell.ALIGN_LEFT,bf112));
     table6.addCell(getCell2("3)ROHS ", PdfPCell.ALIGN_LEFT,bf112));
     table6.addCell(getCell2("4)MSDS ", PdfPCell.ALIGN_LEFT,bf112));
     
     table00.addCell(table5);
     table00.addCell(table6);
     document.add(table00);
     
     
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
