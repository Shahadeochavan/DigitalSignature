package com.nextech.erp.dto;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
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
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.nextech.erp.model.Client;
import com.nextech.erp.model.Productorder;
import com.nextech.erp.service.RawmaterialorderService;

public class CreatePDF {

	public static String answer = "";
	private static Font TIME_ROMAN = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
	private static Font TIME_ROMAN_SMALL = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private static float SUB_TOTAL = 0;
	/**
	 * @param args
	 */

	@Autowired
	RawmaterialorderService rawmaterialorderService;
	public  Document createPDF(String file,Productorder productorder,List<ProductOrderData> productOrderDatas,Client client) throws Exception {

		Document document = null;

		try {
			document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();

			addMetaData(document);

			addTitlePage(document,client,productorder);

			createTable(document, productorder,productOrderDatas);

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

	private  void addTitlePage(Document document,Client client,Productorder productorder)
			throws DocumentException {

		Paragraph preface = new Paragraph();
		   Font bf12 = new Font(FontFamily.TIMES_ROMAN, 10,Font.BOLD); 
		   Font bf112 = new Font(FontFamily.TIMES_ROMAN, 15,Font.BOLD); 
		   Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 10);
		  //specify column widths
		   float[] columnWidths = {5f};
		   //create PDF table with the given widths
		   PdfPTable table = new PdfPTable(columnWidths);
		   // set table width a percentage of the page width
		  
		   creteEmptyLine(preface, 2);
		   document.add(preface);
		   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		   PdfPTable pdfPTable = new PdfPTable(1);
		   pdfPTable.setWidthPercentage(100);
		   pdfPTable.addCell(getCell("TAX INVOICE", PdfPCell.ALIGN_CENTER,bf12));
		   document.add(pdfPTable);
		   
		   
		    
		    float[] columnWidths1 = {40f, 30f,30f};
			   //create PDF table with the given widths
		     PdfPTable table00 = new PdfPTable(columnWidths1);
		     table00.setWidthPercentage(100f);
		   
		     PdfPTable table1 = new PdfPTable(1);
		     table1.setWidthPercentage(100);
		     table1.addCell(getCell("From :", PdfPCell.ALIGN_LEFT,bf12));
		     table1.addCell(getCell(" ", PdfPCell.ALIGN_LEFT,bf12));
		     table1.addCell(getCell("E.K.ELECTRONICS PVT.LTD", PdfPCell.ALIGN_LEFT,bf12));
		     table1.addCell(getCell("E-64 MIDC Industrial,Ranjangon Tal Shirur Dist pune-412220", PdfPCell.ALIGN_LEFT,font3));
		     table1.addCell(getCell("Email:account@ekelectronics.co.in", PdfPCell.ALIGN_LEFT,font3));
		     table1.addCell(getCell("Excise Redn/Code No :"+"AACCE3429LEM001", PdfPCell.ALIGN_LEFT,bf12));
		     table1.addCell(getCell("Certificate  No :"+"710304", PdfPCell.ALIGN_LEFT,font3));
		     
		     PdfPTable table12 = new PdfPTable(1);
		     table12.addCell(getCell("Invoice No -"+productorder.getInvoiceNo(), PdfPCell.ALIGN_LEFT,bf12));
		     table12.addCell(getCell("[see rule 11 of Central Excise rule 2002", PdfPCell.ALIGN_LEFT,font3));
		     
		     PdfPTable table13 = new PdfPTable(2);
		     table13.setWidthPercentage(100);
		     
		     PdfPTable pdtable = new PdfPTable(1);
		     pdtable.addCell(getCell("Dated -", PdfPCell.ALIGN_LEFT,bf12));
		     pdtable.addCell(getCell(simpleDateFormat.format(new Date()), PdfPCell.ALIGN_LEFT,bf12));
		     
		     PdfPTable pdtable1 = new PdfPTable(1);
		     pdtable1.addCell(getCell("EXTRA COPY", PdfPCell.ALIGN_LEFT,bf12));
		     table13.addCell(pdtable);
		     table13.addCell(pdtable1);
		     
		     table00.addCell(table1);
		     table00.addCell(table12);
		     table00.addCell(table13);
		     document.add(table00);
		     
		     PdfPTable table14 = new PdfPTable(3);
		     table14.setWidthPercentage(100);
		 
		     PdfPTable table15 = new PdfPTable(1);
		     table15.addCell(getCell("Renge : "+"V(Shirur)", PdfPCell.ALIGN_LEFT,bf12));
		     table15.addCell(getCell("Ice House,41/A Sasoon Road pune 411001", PdfPCell.ALIGN_LEFT,font3));
		     table15.addCell(getCell("Divison :"+"V(South Shirur)", PdfPCell.ALIGN_LEFT,bf12));
		     table15.addCell(getCell("Ice House,41/A Sasoon Road pune 411001", PdfPCell.ALIGN_LEFT,font3));
		     table15.addCell(getCell("Commissionerate : "+"PUNE IV", PdfPCell.ALIGN_LEFT,bf12));
		     
		     PdfPTable table16 = new PdfPTable(1);
		     table16.addCell(getCell("To", PdfPCell.ALIGN_LEFT,bf12));
		     table16.addCell(getCell("Name and address of consignee", PdfPCell.ALIGN_LEFT,bf12));
		     table16.addCell(getCell(client.getCompanyname(), PdfPCell.ALIGN_LEFT,bf12));
		     table16.addCell(getCell(client.getAddress(), PdfPCell.ALIGN_LEFT,font3));
		     table16.addCell(getCell("Tel."+client.getContactnumber(), PdfPCell.ALIGN_LEFT,font3));
		     table16.addCell(getCell("Customer ECC No:-"+client.getCustomerEccNumber(), PdfPCell.ALIGN_LEFT,bf12));
		     table16.addCell(getCell("Renge:-"+client.getRenge(), PdfPCell.ALIGN_LEFT,bf12));
		     table16.addCell(getCell("Divison:-"+client.getDivision(), PdfPCell.ALIGN_LEFT,bf12));
		     table16.addCell(getCell("Commissionerate:-"+client.getCommisionerate(), PdfPCell.ALIGN_LEFT,bf12));
		     table16.addCell(getCell("Your VAT/TIN No:-"+client.getVatNo(), PdfPCell.ALIGN_LEFT,bf12));
		     table16.addCell(getCell("Your CST TIN No:-"+client.getCst(), PdfPCell.ALIGN_LEFT,bf12));
		     
		     PdfPTable table17 = new PdfPTable(1);
		     table17.addCell(getCell("Category of consignee :-", PdfPCell.ALIGN_LEFT,bf12));
		     table17.addCell(getCell("                       "+"Wholesale dealer/", PdfPCell.ALIGN_LEFT,bf12));
		     table17.addCell(getCell("                       "+"Industrial consumer/", PdfPCell.ALIGN_LEFT,bf12));
		     table17.addCell(getCell("                     "+"Government deparment etc", PdfPCell.ALIGN_LEFT,bf12));
		     
		     table14.addCell(table15);
		     table14.addCell(table16);
		     table14.addCell(table17);
		     document.add(table14);
		     
		     PdfPTable lasttable = new PdfPTable(3);
		     lasttable.setWidthPercentage(100);
		     
		     PdfPTable subtable = new PdfPTable(1);
		     subtable.addCell(getCell1("Name of excisable commodity- "+"Connectors", PdfPCell.ALIGN_LEFT,bf12));
		     subtable.addCell(getCell1("Tariff Heading No- "+"8536-9090", PdfPCell.ALIGN_LEFT,bf12));
		     subtable.addCell(getCell1("Rate of duty- "+"12.50%", PdfPCell.ALIGN_LEFT,bf12));
		 
		     PdfPTable subtable1 = new PdfPTable(1);
		     subtable1.addCell(getCell1("Your P.O.No- "+"6527718664", PdfPCell.ALIGN_LEFT,bf12));
		     subtable1.addCell(getCell1("Date- ", PdfPCell.ALIGN_LEFT,bf12));
		     subtable1.addCell(getCell1("our delivery challon No- "+".......", PdfPCell.ALIGN_LEFT,bf12));
		     subtable1.addCell(getCell1("Date- "+".......", PdfPCell.ALIGN_LEFT,bf12));
		     
		     PdfPTable subtable12 = new PdfPTable(1);
		     subtable12.addCell(getCell1("Mode of transport ROAD R.R/G.C NOTE NO. "+"........", PdfPCell.ALIGN_LEFT,bf12));
		     subtable12.addCell(getCell1("if by motor vehicle ,ts regitsration number "+".......", PdfPCell.ALIGN_LEFT,bf12));
		     
		     lasttable.addCell(subtable);
		     lasttable.addCell(subtable1);
		     lasttable.addCell(subtable12);
		     document.add(lasttable);
		    
	}

	private static void creteEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	private  void createTable(Document document,Productorder productorder,List<ProductOrderData> productOrderDatas) throws Exception {
              DecimalFormat df = new DecimalFormat("0.0");
			  Font bf123 = new Font(FontFamily.TIMES_ROMAN, 14,Font.BOLD); 
			  Font bfBold12 = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0)); 
			   Font bf12 = new Font(FontFamily.TIMES_ROMAN, 12); 
			   
			   Font bf1 = new Font(FontFamily.TIMES_ROMAN, 10,Font.BOLD); 
			  //specify column widths
			   float[] columnWidths = {1.5f, 1.5f, 1.5f, 1.5f};
			   //create PDF table with the given widths
			   PdfPTable table = new PdfPTable(columnWidths);
			   // set table width a percentage of the page width
			   table.setWidthPercentage(100f);

			   //insert column headings
			   insertCell(table, "Description of goods", Element.ALIGN_RIGHT, 1, bfBold12);
			   insertCell(table, "Quantity", Element.ALIGN_LEFT, 1, bfBold12);
			   insertCell(table, "Rate", Element.ALIGN_LEFT, 1, bfBold12);
			   insertCell(table, "Amount", Element.ALIGN_LEFT, 1, bfBold12);
			   table.setHeaderRows(1);

	     for (ProductOrderData productOrderData : productOrderDatas) {
		  insertCell(table,productOrderData.getProductName() , Element.ALIGN_RIGHT, 1, bf12);
		    insertCell(table,(Long.toString(productOrderData.getQuantity())), Element.ALIGN_RIGHT, 1, bf12);
		    insertCell(table, (Float.toString(productOrderData.getRate())), Element.ALIGN_RIGHT, 1, bf12);
		    insertCell(table, (Float.toString(productOrderData.getAmount())), Element.ALIGN_RIGHT, 1, bf12);
		    SUB_TOTAL = SUB_TOTAL+productOrderData.getAmount();
	    }
	     insertCell(table, "Sub Total", Element.ALIGN_RIGHT, 3, bfBold12);
	     insertCell(table, df.format(SUB_TOTAL), Element.ALIGN_RIGHT, 1, bfBold12);
	     insertCell(table, "Tax", Element.ALIGN_RIGHT, 3, bfBold12);
	     float tax =0;
	     tax = 18*SUB_TOTAL;
	     tax =tax/100;
	     insertCell(table, (Float.toString(tax)), Element.ALIGN_RIGHT, 1, bfBold12);
	     insertCell(table, "Total", Element.ALIGN_RIGHT, 3, bfBold12);
	     int total=0;
	      total = (int) (SUB_TOTAL+tax);
	     insertCell(table, (Float.toString(total)), Element.ALIGN_RIGHT, 1, bfBold12);
	     document.add(table);
	     

			if(total <= 0)   {                
				System.out.println("Enter numbers greater than 0");
			} else {
				CreatePDF a = new CreatePDF();
				a.pw((total/1000000000)," Hundred");
				a.pw((total/10000000)%100," Crore");
				a.pw(((total/100000)%100)," Lakh");
				a.pw(((total/1000)%100)," Thousand");
				a.pw(((total/100)%10)," Hundred");
				a.pw((total%100)," ");
				answer = answer.trim();
				System.out.println("Final Answer : " +answer);
			
           PdfPTable lasttable = new PdfPTable(2);
	       lasttable.setWidthPercentage(100);
	     
	     PdfPTable subtable = new PdfPTable(1);
	     subtable.addCell(getCell1("Total centeral excise dutey payable (In fig)", PdfPCell.ALIGN_LEFT,bf1));
	     subtable.addCell(getCell1(answer, PdfPCell.ALIGN_LEFT,bf12));
	 
	     PdfPTable subtable1 = new PdfPTable(1);
	     subtable1.addCell(getCell1("Grand Total", PdfPCell.ALIGN_LEFT,bf1));
	     subtable1.addCell(getCell1(answer, PdfPCell.ALIGN_LEFT,bf1));
	    
	     lasttable.addCell(subtable);
	     lasttable.addCell(subtable1);
	     document.add(lasttable);
			}
	     
	     DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	     Date date = new Date();
	     PdfPTable lasttable1 = new PdfPTable(2);
	     lasttable1.setWidthPercentage(100);
	     
	     PdfPTable subtable11 = new PdfPTable(1);
	     subtable11.addCell(getCell1("Company VAT TIN:"+"2752076747V", PdfPCell.ALIGN_LEFT,bf12));
	     subtable11.addCell(getCell1("Company CST NO"+"27455555C", PdfPCell.ALIGN_LEFT,bf12));
	 
	     PdfPTable subtable12 = new PdfPTable(1);
	     subtable12.addCell(getCell1("Serial No in PLA/RG-23", PdfPCell.ALIGN_LEFT,bf12));
	     subtable12.addCell(getCell1("Date & Time of invoice :"+dateFormat.format(date), PdfPCell.ALIGN_LEFT,bf12));
	     subtable12.addCell(getCell1("Date & Time of removal :"+dateFormat.format(date), PdfPCell.ALIGN_LEFT,bf12));
	    
	     lasttable1.addCell(subtable11);
	     lasttable1.addCell(subtable12);
	     document.add(lasttable1);
	     
	     float[] columnWidths1 = {65f, 35f};
	     PdfPTable lasttable10 = new PdfPTable(columnWidths1);
	     lasttable10.setWidthPercentage(100f);
	     
	     PdfPTable subtable110 = new PdfPTable(1);
	     subtable110.addCell(getCell1("Declaration :", PdfPCell.ALIGN_LEFT,bf1));
	     subtable110.addCell(getCell1("I/We herby certify that my/our registration certificate under the Maharashtra vlue added tax Act"
	    		+"2002 is in force on the date on which the sale of goods spcified in this tax invoice is madhe by "
	    		 +"me/us and that the transaction of sale coverd by this tax invoice has been effcted by me us its shall be "
	    		 + "accounted for in the turnover of sales while of sales while filing of retur and the due tax ,if any"
	    		 + "payable on the sale has been paid or shall be paid", PdfPCell.ALIGN_LEFT,bf1));
	 
	     PdfPTable subtable120 = new PdfPTable(1);
	     subtable120.addCell(getCell1("For EK Electronics Pvt.Ltd", PdfPCell.ALIGN_RIGHT,bf123));
	     subtable120.addCell(getCell1(" ", PdfPCell.ALIGN_RIGHT,bf12));
	     subtable120.addCell(getCell1(" ", PdfPCell.ALIGN_RIGHT,bf12));
	     subtable120.addCell(getCell1(" ", PdfPCell.ALIGN_RIGHT,bf12));
	     subtable120.addCell(getCell1(" ", PdfPCell.ALIGN_RIGHT,bf12));
	     subtable120.addCell(getCell1("Authorised Signature", PdfPCell.ALIGN_RIGHT,bf123));
	    
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
		    cell.setPadding(0);
		    cell.setExtraParagraphSpace(1);
		    cell.setVerticalAlignment(alignment);
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
		    cell.setVerticalAlignment(alignment);
		    cell.setHorizontalAlignment(alignment);
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
