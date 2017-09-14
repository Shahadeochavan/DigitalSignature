package com.nextech.erp.dto;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.nextech.erp.newDTO.ClientDTO;

public class CreatePDF {

	public static String answer = "";
	private static Font TIME_ROMAN = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
	private static Font TIME_ROMAN_SMALL = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	public  float SUB_TOTAL = 0;
	public double igstTotal =0;
	public double cgstTotal = 0;
	public double sgstTotal =0;
	/**
	 * @param args
	 */
  

	public  Document createPDF(String file,ProductOrderDTO productOrderDTO,List<ProductOrderData> productOrderDatas,ClientDTO client) throws Exception {

		Document document = null;

		try {
			document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();

			addMetaData(document);

			addTitlePage(document,client,productOrderDTO);

			createTable(document, productOrderDTO,productOrderDatas);

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

	private  void addTitlePage(Document document,ClientDTO client,ProductOrderDTO productOrderDTO)
			throws DocumentException {

		Paragraph preface = new Paragraph();
		   Font bf12 = new Font(FontFamily.TIMES_ROMAN, 10,Font.BOLD); 
		   Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 10);
		  
		   creteEmptyLine(preface, 2);
		   document.add(preface);
		   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		    float[] columnWidths1 = {30f, 30f,30f};
		     PdfPTable table00 = new PdfPTable(columnWidths1);
		     table00.setWidthPercentage(100f);
		   
		     PdfPTable table1 = new PdfPTable(1);
		     table1.setWidthPercentage(100);
		     PdfPTable table12 = new PdfPTable(1);
		     table12.addCell(getCell("Invoice No -"+productOrderDTO.getInvoiceNo(), PdfPCell.ALIGN_LEFT,bf12));
		     
			  PdfPTable table13 = new PdfPTable(2);
			  table13.setWidthPercentage(100);
			     
			  PdfPTable pdtable = new PdfPTable(1);
			  pdtable.addCell(getCell("Date -", PdfPCell.ALIGN_LEFT,bf12));
			  pdtable.addCell(getCell(simpleDateFormat.format(new Date()), PdfPCell.ALIGN_LEFT,bf12));
			     
			   PdfPTable pdtable1 = new PdfPTable(1);
			   pdtable1.addCell(getCell("ORIGINAL FOR RECIPIENT", PdfPCell.ALIGN_LEFT,bf12));
			   table13.addCell(pdtable);
		     table13.addCell(pdtable1);
		     
		     table00.addCell(table1);
		     table00.addCell(table12);
		     table00.addCell(table13);
		     document.add(table00);
		     
		     
		     float[] columnWidths12 = {50f,50f};
		     PdfPTable table14 = new PdfPTable(columnWidths12);
		     table14.setWidthPercentage(100f);
		 
		     PdfPTable table15 = new PdfPTable(1);
		     table15.addCell(getCell("GSTIN : "+"27AACCE3429L1ZI", PdfPCell.ALIGN_LEFT,bf12));
		     table15.addCell(getCell("E.K.ELECTRONICS PVT.LTD", PdfPCell.ALIGN_LEFT,bf12));
		     table15.addCell(getCell("E-64 MIDC Industrial,Ranjangon Tal Shirur Dist pune-412220", PdfPCell.ALIGN_LEFT,font3));
		     table15.addCell(getCell("Email:account@ekelectronics.co.in", PdfPCell.ALIGN_LEFT,font3));
		     table15.addCell(getCell("Serial No. of Invoice  :"+productOrderDTO.getInvoiceNo(), PdfPCell.ALIGN_LEFT,font3));
		     table15.addCell(getCell("Date of Invoice  :"+simpleDateFormat.format(new Date()), PdfPCell.ALIGN_LEFT,font3));
		     
		     PdfPTable table17 = new PdfPTable(1);
		     table17.addCell(getCell("Category of consignee :-", PdfPCell.ALIGN_LEFT,bf12));
		     table17.addCell(getCell("Wholesale dealer/", PdfPCell.ALIGN_LEFT,bf12));
		     table17.addCell(getCell("Industrial consumer/", PdfPCell.ALIGN_LEFT,bf12));
		     table17.addCell(getCell("Government deparment etc", PdfPCell.ALIGN_LEFT,bf12));
		     
		     PdfPTable table141 = new PdfPTable(1);
		      table141.setWidthPercentage(100);
		     PdfPTable pdtable142 = new PdfPTable(1);
		     pdtable142.addCell(getCell("Your P.O.No : "+productOrderDTO.getPoNO(), PdfPCell.ALIGN_LEFT,bf12));
		     table141.addCell(pdtable142);
		     
		     PdfPTable table142 = new PdfPTable(1);
		     table142.setWidthPercentage(100);
		     PdfPTable pdtable143 = new PdfPTable(1);
		     pdtable143.addCell(getCell("Date of  P.O.No : "+productOrderDTO.getCreateDate(), PdfPCell.ALIGN_LEFT,bf12));
		     table142.addCell(pdtable143);
		     
		     table17.addCell(table141);
		     table17.addCell(table142);
		     table14.addCell(table15);
		     table14.addCell(table17);
		     document.add(table14);
		     
		     float[] columnWidths13 = {50f,50f};
		     PdfPTable table133 = new PdfPTable(columnWidths13);
		     table133.setWidthPercentage(100);
		     
		     PdfPTable table31 = new PdfPTable(1);
		     table31.addCell(getCell("Details of Recevier(Billed to)", PdfPCell.ALIGN_LEFT,bf12));
		     table31.addCell(getCell("Name :"+client.getCompanyName(), PdfPCell.ALIGN_LEFT,bf12));
		     table31.addCell(getCell("Address"+client.getAddress(), PdfPCell.ALIGN_LEFT,font3));
		     table31.addCell(getCell("GSTIN/Unique ID:-"+"AAAAGBV1234", PdfPCell.ALIGN_LEFT,bf12));
		     
		     PdfPTable table32 = new PdfPTable(1);
		     table32.addCell(getCell("Details of Recevier(Billed to)", PdfPCell.ALIGN_LEFT,bf12));
		     table32.addCell(getCell("Name :"+client.getCompanyName(), PdfPCell.ALIGN_LEFT,bf12));
		     table32.addCell(getCell("Address  :"+client.getAddress(), PdfPCell.ALIGN_LEFT,font3));
		     table32.addCell(getCell("GSTIN/Unique ID:-"+"AAAAGBV1234", PdfPCell.ALIGN_LEFT,bf12));
		    
		     table133.addCell(table31);
		     table133.addCell(table32);
		     document.add(table133);
		     
		    
	}

	private static void creteEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	private  void createTable(Document document,ProductOrderDTO productorder,List<ProductOrderData> productOrderDatas) throws Exception {
              DecimalFormat df = new DecimalFormat("0.0");
			  Font bf123 = new Font(FontFamily.TIMES_ROMAN, 10,Font.BOLD); 
			  Font bfBold = new Font(FontFamily.TIMES_ROMAN, 8, Font.BOLD, new BaseColor(0, 0, 0));
			   Font bf12 = new Font(FontFamily.TIMES_ROMAN, 10); 
			   
			   Font bf1 = new Font(FontFamily.TIMES_ROMAN, 10,Font.BOLD); 
			   float[] columnWidths = {1.5f, 1.2f, 1.2f, 1.9f,1.9f,1.9f,1.3f};
			   PdfPTable table = new PdfPTable(columnWidths);
			   table.setWidthPercentage(100f);

			   insertCell(table, "Description of goods", Element.ALIGN_RIGHT, 1, bf123);
			   insertCell(table, "Quantity", Element.ALIGN_LEFT, 1, bf123);
			   insertCell(table, "Rate", Element.ALIGN_LEFT, 1, bf123);
			   PdfPTable pdfPTable =  new PdfPTable(2);
			   PdfPCell cell = new PdfPCell(new Phrase("CGST", bf123));
			   cell.setColspan(2);
			   cell.setRowspan(2);
			   cell.setBorder(PdfPCell.NO_BORDER);
			   pdfPTable.addCell(cell);
			   pdfPTable.addCell(new Phrase("Percentage",bfBold));
			   pdfPTable.addCell(new Phrase("Amount",bfBold));
			   table.addCell(pdfPTable);
			   
			   PdfPTable pdfPTable1 =  new PdfPTable(2);
			   PdfPCell cell1 = new PdfPCell(new Phrase("SGST", bf123));
			   cell1.setColspan(2);
			   cell1.setRowspan(2);
			   cell1.setBorder(PdfPCell.NO_BORDER);
			   pdfPTable1.addCell(cell1);
			   pdfPTable1.addCell(new Phrase("Percentage",bfBold));
			   pdfPTable1.addCell(new Phrase("Amount",bfBold));
			   table.addCell(pdfPTable1);
			   
			   PdfPTable pdfPTable2 =  new PdfPTable(2);
			   PdfPCell cell2 = new PdfPCell(new Phrase("IGST", bf123));
			   cell2.setColspan(2);
			   cell2.setRowspan(2);
			   cell2.setBorder(PdfPCell.NO_BORDER);
			   pdfPTable2.addCell(cell2);
			   pdfPTable2.addCell(new Phrase("Percentage",bfBold));
			   pdfPTable2.addCell(new Phrase("Amount",bfBold));
			   table.addCell(pdfPTable2);
			   insertCell(table, "Amount", Element.ALIGN_LEFT, 1, bf123);
			   table.setHeaderRows(1);

	     for (ProductOrderData productOrderData : productOrderDatas) {
		  insertCell(table,productOrderData.getProductName() , Element.ALIGN_RIGHT, 1, bf12);
		    insertCell(table,(Long.toString(productOrderData.getQuantity())), Element.ALIGN_CENTER, 1, bf12);
		    insertCell(table, (Float.toString(productOrderData.getRate())), Element.ALIGN_CENTER, 1, bf12);
		    double cgstTax = productOrderData.getCgst();
		     cgstTax = cgstTax*productOrderData.getRate();
		     cgstTax = cgstTax/100;
		     cgstTotal  =  cgstTotal+cgstTax;
		     PdfPTable table13 = new PdfPTable(2);
		     table13.setWidthPercentage(100);
		     PdfPTable pdtable = new PdfPTable(1);
		     pdtable.addCell(getCell(""+productOrderData.getCgst(), PdfPCell.ALIGN_LEFT,bf12));
		     
		     PdfPTable pdtable1 = new PdfPTable(1);
		     pdtable1.addCell(getCell(""+cgstTax, PdfPCell.ALIGN_LEFT,bf12));
		     table13.addCell(pdtable);
		     table13.addCell(pdtable1);
		     table.addCell(table13);
		    
		    double sgstTax = productOrderData.getSgst();
		    sgstTax = sgstTax*productOrderData.getRate();
		    sgstTax = sgstTax/100;
		    sgstTotal = sgstTotal+sgstTax;
		    PdfPTable table131 = new PdfPTable(2);
		    table131.setWidthPercentage(100);
		     PdfPTable pdtable132 = new PdfPTable(1);
		     pdtable132.addCell(getCell(""+productOrderData.getSgst(), PdfPCell.ALIGN_LEFT,bf12));
		     
		     PdfPTable pdtable133 = new PdfPTable(1);
		     pdtable133.addCell(getCell(""+sgstTax, PdfPCell.ALIGN_LEFT,bf12));
		     table131.addCell(pdtable132);
		     table131.addCell(pdtable133);
		     table.addCell(table131);
		    
		    double igstTax = productOrderData.getIgst();
		    igstTax = igstTax*productOrderData.getRate();
		    igstTax = igstTax/100;
		    igstTotal = igstTotal+igstTax;
		    PdfPTable table141 = new PdfPTable(2);
		      table141.setWidthPercentage(100);
		     PdfPTable pdtable142 = new PdfPTable(1);
		     pdtable142.addCell(getCell(""+productOrderData.getIgst(), PdfPCell.ALIGN_LEFT,bf12));
		     
		     PdfPTable pdtable143 = new PdfPTable(1);
		     pdtable143.addCell(getCell(""+igstTax, PdfPCell.ALIGN_LEFT,bf12));
		     table141.addCell(pdtable142);
		     table141.addCell(pdtable143);
		     table.addCell(table141);	
		    
		    insertCell(table, (Float.toString(productOrderData.getAmount())), Element.ALIGN_RIGHT, 1, bf12);
		    SUB_TOTAL = SUB_TOTAL+productOrderData.getAmount();
	    }
	     insertCell(table, "Assessble Value", Element.ALIGN_RIGHT, 6, bf123);
	     insertCell(table, df.format(SUB_TOTAL), Element.ALIGN_RIGHT, 1, bf123);
	     insertCell(table, "CGST Total", Element.ALIGN_RIGHT, 6, bf123);
	     insertCell(table, Double.toString(cgstTotal), Element.ALIGN_RIGHT, 1, bf123);
	     insertCell(table, "SGST Total", Element.ALIGN_RIGHT, 6, bf123);
	     insertCell(table, Double.toString(sgstTotal), Element.ALIGN_RIGHT, 1, bf123);
	     insertCell(table, "IGST Total", Element.ALIGN_RIGHT, 6, bf123);
	     insertCell(table, Double.toString(igstTotal), Element.ALIGN_RIGHT, 1, bf123);
	     insertCell(table, "Total Tax(CGST+SGST+IGST)", Element.ALIGN_RIGHT, 6, bf123);
	     int totalTax= (int) (cgstTotal+igstTotal+sgstTotal);
	     insertCell(table, Double.toString(totalTax), Element.ALIGN_RIGHT, 1, bf123);
	     int totalFinal = (int) (totalTax+SUB_TOTAL);
	     
	     document.add(table);
	     
			if(totalFinal <= 0)   {                
				System.out.println("Enter numbers greater than 0");
			} else {
				
		 String  totalTaxInWord = calculationInWord(totalFinal);
		 float[] columnwith1 = {88.20f,11.80f};
	     PdfPTable table921 = new PdfPTable(columnwith1);
	     table921.setWidthPercentage(100);
	     
	     PdfPTable table923 = new PdfPTable(1);
	     table923.addCell(getCell("Total Invoice Value In Word  :"+totalTaxInWord, PdfPCell.ALIGN_LEFT,bf1));
	     
	     PdfPTable table909= new PdfPTable(1);
	     
	     table921.addCell(table923);
	     table921.addCell(table909);
	     document.add(table921);
	     
	     float[] columnwith12 = {68.20f, 20f,11.80f};
	     PdfPTable table9 = new PdfPTable(columnwith12);
	     table9.setWidthPercentage(100);
	    
	     PdfPTable table91= new PdfPTable(1);
	     
	     PdfPTable table92 = new PdfPTable(1);
	     table92.addCell(getCell("Total Invoice in fig", PdfPCell.ALIGN_LEFT,bf1));
	     
	     PdfPTable table93 = new PdfPTable(1);
	     table93.addCell(getCell(""+totalFinal, PdfPCell.ALIGN_LEFT,bf1));
	     
	     table9.addCell(table91);
	     table9.addCell(table92);
	     table9.addCell(table93);
	     document.add(table9);
			}
	     
	     DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	     Date date = new Date();
	     PdfPTable lasttable1 = new PdfPTable(2);
	     lasttable1.setWidthPercentage(100);
	     
	     PdfPTable subtable11 = new PdfPTable(1);
	     subtable11.addCell(getCell("Date and Time of Removal", PdfPCell.ALIGN_LEFT,bf12));
	 
	     PdfPTable subtable12 = new PdfPTable(1);
	     subtable12.addCell(getCell(""+dateFormat.format(date), PdfPCell.ALIGN_LEFT,bf12));
	    
	     lasttable1.addCell(subtable11);
	     lasttable1.addCell(subtable12);
	     document.add(lasttable1);
	     
	     float[] columnWidths1 = {65f, 35f};
	     PdfPTable lasttable10 = new PdfPTable(columnWidths1);
	     lasttable10.setWidthPercentage(100f);
	     
	     PdfPTable subtable110 = new PdfPTable(1);
	     subtable110.addCell(getCell("Declaration :", PdfPCell.ALIGN_LEFT,bf1));
	     subtable110.addCell(getCell("1) I/We declare that this invoice shows actual price of goods and services described that all particulars are true and correct", PdfPCell.ALIGN_LEFT,bf1));
	     subtable110.addCell(getCell("2) Error and Omission expected", PdfPCell.ALIGN_LEFT,bf1));
	     subtable110.addCell(getCell("3) Subject to the jurisdiction of courts in shirur", PdfPCell.ALIGN_LEFT,bf1));
	 
	     PdfPTable subtable120 = new PdfPTable(1);
	     subtable120.addCell(getCell("For EK Electronics Pvt.Ltd", PdfPCell.ALIGN_RIGHT,bf123));
	     subtable120.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT,bf12));
	     subtable120.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT,bf12));
	     subtable120.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT,bf12));
	     subtable120.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT,bf12));
	     subtable120.addCell(getCell("Authorised Signature", PdfPCell.ALIGN_RIGHT,bf123));
	    
	     lasttable10.addCell(subtable110);
	     lasttable10.addCell(subtable120);
	     document.add(lasttable10);
	     Paragraph preface = new Paragraph();
	     creteEmptyLine(preface, 1);
	     document.add(preface);
	     
	     

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
	 public PdfPCell getCell(String text, int alignment,Font bf12) {
		    PdfPCell cell = new PdfPCell(new Phrase(text,bf12));
		    cell.setPaddingLeft(10f);
		    cell.setExtraParagraphSpace(1);
		    cell.setVerticalAlignment(alignment);
		    cell.setBorder(PdfPCell.NO_BORDER);
		    return cell;
		}
	 
		public void pw(int n,String ch)
		{
			String  one[]={" "," one"," two"," three"," four"," five"," six"," seven"," eight"," Nine"," ten"," eleven"," twelve"," thirteen"," fourteen","fifteen"," sixteen"," seventeen"," eighteen"," Nineteen"};
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
		public String calculationInWord(int total){
			answer = new String();
			if(total <= 0)   {                
				System.out.println("Enter numbers greater than 0");
			} else {
				CreatePDF a = new CreatePDF();
				a.pw((total/1000000000)," hundred");
				a.pw((total/10000000)%100," crore");
				a.pw(((total/100000)%100)," lakh");
				a.pw(((total/1000)%100)," thousand");
				a.pw(((total/100)%10)," hundred");
				a.pw((total%100)," ");
				answer = answer.trim();
				System.out.println("Final Answer : " +answer);
		}
			return answer;
		}
}
