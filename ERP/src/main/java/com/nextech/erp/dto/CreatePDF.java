package com.nextech.erp.dto;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
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
import com.itextpdf.text.Image;
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
			throws DocumentException, MalformedURLException, IOException {

		Paragraph preface = new Paragraph();
		   Font bf12 = new Font(FontFamily.TIMES_ROMAN, 10,Font.BOLD); 
		   Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 10);
		  
		   creteEmptyLine(preface, 2);
		   document.add(preface);
		   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		    float[] columnWidthImageInvoice = {30f, 30f,30f};
		     PdfPTable imageInvoiceDateTable = new PdfPTable(columnWidthImageInvoice);
		     imageInvoiceDateTable.setWidthPercentage(100f);
		   
		     PdfPTable imageTable = new PdfPTable(1);
		     imageTable.setWidthPercentage(100);
		     //TODO change image path
		     Image img = Image.getInstance("C:/Users/welcome/git/erp-be/ERP/src/main/webapp/img/ekimage.png");
		     imageTable.addCell(img);
		     //   document.add(img);
		     
		     PdfPTable invoiceTable = new PdfPTable(1);
		     invoiceTable.addCell(getCell("Invoice No -"+productOrderDTO.getInvoiceNo(), PdfPCell.ALIGN_LEFT,bf12));
		     
			  PdfPTable dateCopyTable = new PdfPTable(2);
			  dateCopyTable.setWidthPercentage(100);
			     
			  PdfPTable dateTable = new PdfPTable(1);
			  dateTable.addCell(getCell("Date -", PdfPCell.ALIGN_LEFT,bf12));
			  dateTable.addCell(getCell(simpleDateFormat.format(new Date()), PdfPCell.ALIGN_LEFT,bf12));
			     
			   PdfPTable recipientTable = new PdfPTable(1);
			   recipientTable.addCell(getCell("ORIGINAL FOR RECIPIENT", PdfPCell.ALIGN_LEFT,bf12));
			   dateCopyTable.addCell(dateTable);
			   dateCopyTable.addCell(recipientTable);
		     
		     imageInvoiceDateTable.addCell(imageTable);
		     imageInvoiceDateTable.addCell(invoiceTable);
		     imageInvoiceDateTable.addCell(dateCopyTable);
		     document.add(imageInvoiceDateTable);
		     
		     
		     float[] fromConsigneeColumWidth = {50f,50f};
		     PdfPTable fromConsigneeTable = new PdfPTable(fromConsigneeColumWidth);
		     fromConsigneeTable.setWidthPercentage(100f);
		 
		     PdfPTable fromTabel = new PdfPTable(1);
		     fromTabel.addCell(getCell("GSTIN : "+"27AACCE3429L1ZI", PdfPCell.ALIGN_LEFT,bf12));
		     fromTabel.addCell(getCell("E.K.ELECTRONICS PVT.LTD", PdfPCell.ALIGN_LEFT,bf12));
		     fromTabel.addCell(getCell("E-64 MIDC Industrial,Ranjangon Tal Shirur Dist pune-412220", PdfPCell.ALIGN_LEFT,font3));
		     fromTabel.addCell(getCell("Email:account@ekelectronics.co.in", PdfPCell.ALIGN_LEFT,font3));
		     fromTabel.addCell(getCell("Serial No. of Invoice  :"+productOrderDTO.getInvoiceNo(), PdfPCell.ALIGN_LEFT,font3));
		     fromTabel.addCell(getCell("Date of Invoice  :"+simpleDateFormat.format(new Date()), PdfPCell.ALIGN_LEFT,font3));
		     
		     PdfPTable consigneeTable = new PdfPTable(1);
		     consigneeTable.addCell(getCell("Category of consignee :-", PdfPCell.ALIGN_LEFT,bf12));
		     consigneeTable.addCell(getCell("Wholesale dealer/", PdfPCell.ALIGN_LEFT,bf12));
		     consigneeTable.addCell(getCell("Industrial consumer/", PdfPCell.ALIGN_LEFT,bf12));
		     consigneeTable.addCell(getCell("Government deparment etc", PdfPCell.ALIGN_LEFT,bf12));
		     
		     PdfPTable poNoDateTable = new PdfPTable(1);
		     poNoDateTable.setWidthPercentage(100);
		     
		     PdfPTable poNOTable = new PdfPTable(1);
		     poNOTable.addCell(getCell("Your P.O.No : "+productOrderDTO.getPoNO(), PdfPCell.ALIGN_LEFT,bf12));
		     poNoDateTable.addCell(poNOTable);
		     
		     PdfPTable poDateTable = new PdfPTable(1);
		     poDateTable.setWidthPercentage(100);
		     
		     PdfPTable datePoTable = new PdfPTable(1);
		     datePoTable.addCell(getCell("Date of  P.O.No : "+productOrderDTO.getCreateDate(), PdfPCell.ALIGN_LEFT,bf12));
		     poDateTable.addCell(datePoTable);
		     
		     consigneeTable.addCell(poNoDateTable);
		     consigneeTable.addCell(poDateTable);
		     fromConsigneeTable.addCell(fromTabel);
		     fromConsigneeTable.addCell(consigneeTable);
		     document.add(fromConsigneeTable);
		     
		     float[] recevierColumnWidth = {50f,50f};
		     PdfPTable toRacevierTable = new PdfPTable(recevierColumnWidth);
		     toRacevierTable.setWidthPercentage(100);
		     
		     PdfPTable reciverTable = new PdfPTable(1);
		     reciverTable.addCell(getCell("Details of Recevier(Billed to)", PdfPCell.ALIGN_LEFT,bf12));
		     reciverTable.addCell(getCell("Name :"+client.getCompanyName(), PdfPCell.ALIGN_LEFT,bf12));
		     reciverTable.addCell(getCell("Address  :"+client.getAddress(), PdfPCell.ALIGN_LEFT,font3));
		     reciverTable.addCell(getCell("GSTIN/Unique ID:-"+"AAAAGBV1234", PdfPCell.ALIGN_LEFT,bf12));
		     
		     PdfPTable reciverTable1 = new PdfPTable(1);
		     reciverTable1.addCell(getCell("Details of Recevier(Billed to)", PdfPCell.ALIGN_LEFT,bf12));
		     reciverTable1.addCell(getCell("Name :"+client.getCompanyName(), PdfPCell.ALIGN_LEFT,bf12));
		     reciverTable1.addCell(getCell("Address  :"+client.getAddress(), PdfPCell.ALIGN_LEFT,font3));
		     reciverTable1.addCell(getCell("GSTIN/Unique ID:-"+"AAAAGBV1234", PdfPCell.ALIGN_LEFT,bf12));
		    
		     toRacevierTable.addCell(reciverTable);
		     toRacevierTable.addCell(reciverTable1);
		     document.add(toRacevierTable);
		     
		    
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
			   float[] columnWidths = {1.3f, 1.1f, 1.2f, 2.0f,2.0f,2.0f,1.3f};
			   PdfPTable table = new PdfPTable(columnWidths);
			   table.setWidthPercentage(100f);

			   insertCell(table, "Description of goods", Element.ALIGN_RIGHT, 1, bf123);
			   insertCell(table, "Quantity", Element.ALIGN_LEFT, 1, bf123);
			   insertCell(table, "Rate", Element.ALIGN_LEFT, 1, bf123);
			   
			   PdfPTable cgstTable =  new PdfPTable(2);
			   PdfPCell cgstCell = new PdfPCell(new Phrase("CGST", bf123));
			   cgstCell.setColspan(2);
			   cgstCell.setRowspan(2);
			   cgstCell.setBorder(PdfPCell.NO_BORDER);
			   cgstTable.addCell(cgstCell);
			   cgstTable.addCell(new Phrase("Percentage",bfBold));
			   cgstTable.addCell(new Phrase("Amount",bfBold));
			   table.addCell(cgstTable);
			   
			   PdfPTable sgstTable =  new PdfPTable(2);
			   PdfPCell sgstCell = new PdfPCell(new Phrase("SGST", bf123));
			   sgstCell.setColspan(2);
			   sgstCell.setRowspan(2);
			   sgstCell.setBorder(PdfPCell.NO_BORDER);
			   sgstTable.addCell(sgstCell);
			   sgstTable.addCell(new Phrase("Percentage",bfBold));
			   sgstTable.addCell(new Phrase("Amount",bfBold));
			   table.addCell(sgstTable);
			   
			   PdfPTable igstTable =  new PdfPTable(2);
			   PdfPCell cell2 = new PdfPCell(new Phrase("IGST", bf123));
			   cell2.setColspan(2);
			   cell2.setRowspan(2);
			   cell2.setBorder(PdfPCell.NO_BORDER);
			   igstTable.addCell(cell2);
			   igstTable.addCell(new Phrase("Percentage",bfBold));
			   igstTable.addCell(new Phrase("Amount",bfBold));
			   table.addCell(igstTable);
			   
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
		     PdfPTable perAmountCGSTTable = new PdfPTable(2);
		     perAmountCGSTTable.setWidthPercentage(100);
		    // perAmountCGSTTable.getDefaultCell().setBorder(0);
		     
		     PdfPTable perCGSTTable = new PdfPTable(1);
		     perCGSTTable.addCell(getCel1(""+productOrderData.getCgst(), PdfPCell.ALIGN_LEFT,bf12));
		    // perCGSTTable.getDefaultCell().setBorderWidthRight(1);

		     
		     PdfPTable amountCGSTTable = new PdfPTable(1);
		     amountCGSTTable.addCell(getCel1(""+cgstTax, PdfPCell.ALIGN_LEFT,bf12));
		     perAmountCGSTTable.addCell(perCGSTTable);
		     perAmountCGSTTable.addCell(amountCGSTTable);
		     table.addCell(perAmountCGSTTable);
		    
		    double sgstTax = productOrderData.getSgst();
		    sgstTax = sgstTax*productOrderData.getRate();
		    sgstTax = sgstTax/100;
		    sgstTotal = sgstTotal+sgstTax;
		    PdfPTable perAmountSGSTTable = new PdfPTable(2);
		    perAmountSGSTTable.setWidthPercentage(100);
		     PdfPTable perSGSTTable = new PdfPTable(1);
		     perSGSTTable.addCell(getCel1(""+productOrderData.getSgst(), PdfPCell.ALIGN_LEFT,bf12));
		     
		     PdfPTable amountSGSTTable = new PdfPTable(1);
		     amountSGSTTable.addCell(getCel1(""+sgstTax, PdfPCell.ALIGN_LEFT,bf12));
		     perAmountSGSTTable.addCell(perSGSTTable);
		     perAmountSGSTTable.addCell(amountSGSTTable);
		     table.addCell(perAmountSGSTTable);
		    
		    double igstTax = productOrderData.getIgst();
		    igstTax = igstTax*productOrderData.getRate();
		    igstTax = igstTax/100;
		    igstTotal = igstTotal+igstTax;
		    PdfPTable perAmoutIGSTTable = new PdfPTable(2);
		    perAmoutIGSTTable.setWidthPercentage(100);
		    
		     PdfPTable perIGSTTable = new PdfPTable(1);
		     perIGSTTable.addCell(getCel1(""+productOrderData.getIgst(), PdfPCell.ALIGN_LEFT,bf12));
		     
		     PdfPTable amoutIGSTTable = new PdfPTable(1);
		     amoutIGSTTable.addCell(getCel1(""+igstTax, PdfPCell.ALIGN_LEFT,bf12));
		     perAmoutIGSTTable.addCell(perIGSTTable);
		     perAmoutIGSTTable.addCell(amoutIGSTTable);
		     table.addCell(perAmoutIGSTTable);	
		    
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
		 float[] invoiceValueColumnwidth = {88.20f,11.80f};
	     PdfPTable invoiceValueTable = new PdfPTable(invoiceValueColumnwidth);
	     invoiceValueTable.setWidthPercentage(100);
	     
	     PdfPTable valueTable = new PdfPTable(1);
	     valueTable.addCell(getCell("Total Invoice Value In Word  :"+totalTaxInWord, PdfPCell.ALIGN_LEFT,bf1));
	     
	     PdfPTable valueBlankTable= new PdfPTable(1);
	     
	     invoiceValueTable.addCell(valueTable);
	     invoiceValueTable.addCell(valueBlankTable);
	     document.add(invoiceValueTable);
	     
	     float[] invoiceFigcolumnwidth = {68.20f, 20f,11.80f};
	     PdfPTable invoiceFigTable = new PdfPTable(invoiceFigcolumnwidth);
	     invoiceFigTable.setWidthPercentage(100);
	    
	     PdfPTable figTable= new PdfPTable(1);
	     
	     PdfPTable invoiceTable1 = new PdfPTable(1);
	     invoiceTable1.addCell(getCell("Total Invoice in fig", PdfPCell.ALIGN_LEFT,bf1));
	     
	     PdfPTable table93 = new PdfPTable(1);
	     table93.addCell(getCell(""+totalFinal, PdfPCell.ALIGN_LEFT,bf1));
	     
	     invoiceFigTable.addCell(figTable);
	     invoiceFigTable.addCell(invoiceTable1);
	     invoiceFigTable.addCell(table93);
	     document.add(invoiceFigTable);
			}
	     
	     DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	     Date date = new Date();
	     PdfPTable dateTimeTable = new PdfPTable(2);
	     dateTimeTable.setWidthPercentage(100);
	     
	     PdfPTable dateTimeRemovalTable = new PdfPTable(1);
	     dateTimeRemovalTable.addCell(getCell("Date and Time of Removal", PdfPCell.ALIGN_LEFT,bf12));
	 
	     PdfPTable dateRemovalTable = new PdfPTable(1);
	     dateRemovalTable.addCell(getCell(""+dateFormat.format(date), PdfPCell.ALIGN_LEFT,bf12));
	    
	     dateTimeTable.addCell(dateTimeRemovalTable);
	     dateTimeTable.addCell(dateRemovalTable);
	     document.add(dateTimeTable);
	     
	     float[] declrationWidth = {65f, 35f};
	     PdfPTable declarationekTable = new PdfPTable(declrationWidth);
	     declarationekTable.setWidthPercentage(100f);
	     
	     PdfPTable declarationTable = new PdfPTable(1);
	     declarationTable.addCell(getCell("Declaration :", PdfPCell.ALIGN_LEFT,bf1));
	     declarationTable.addCell(getCell("1) I/We declare that this invoice shows actual price of goods and services described that all particulars are true and correct", PdfPCell.ALIGN_LEFT,bf1));
	     declarationTable.addCell(getCell("2) Error and Omission expected", PdfPCell.ALIGN_LEFT,bf1));
	     declarationTable.addCell(getCell("3) Subject to the jurisdiction of courts in shirur", PdfPCell.ALIGN_LEFT,bf1));
	 
	     PdfPTable ekTable = new PdfPTable(1);
	     ekTable.addCell(getCell("For EK Electronics Pvt.Ltd", PdfPCell.ALIGN_RIGHT,bf123));
	     ekTable.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT,bf12));
	     ekTable.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT,bf12));
	     ekTable.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT,bf12));
	     ekTable.addCell(getCell("Authorised Signature", PdfPCell.ALIGN_RIGHT,bf123));
	    
	     declarationekTable.addCell(declarationTable);
	     declarationekTable.addCell(ekTable);
	     document.add(declarationekTable);
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
	 
	 public PdfPCell getCel1(String text, int alignment,Font bf12) {
		    PdfPCell cell = new PdfPCell(new Phrase(text,bf12));
		    cell.setVerticalAlignment(alignment);
		    cell.setBorder(PdfPCell.NO_BORDER);
		    cell.setExtraParagraphSpace(10);
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
