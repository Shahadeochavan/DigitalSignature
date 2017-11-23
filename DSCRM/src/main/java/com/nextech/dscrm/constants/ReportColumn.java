package com.nextech.dscrm.constants;

import net.sf.dynamicreports.report.builder.column.ColumnBuilder;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;

public class ReportColumn {
	
	public static String USER_REPORT = "USERS";
	public static String USER_REPORT_PATH = "D:/report/report.pdf";
	public static String USER_REPORT_QUERY = "SELECT id, first_name as firstname, last_name FROM ekerp.user";
	/**************************** COLUMNS **********************************/
	public static ColumnBuilder<?, ?> USER_ID = Columns.column("User Id", "id", DataTypes.integerType());
	public static ColumnBuilder<?, ?> FIRST_NAME = Columns.column("First Name", "firstname", DataTypes.stringType());
	public static ColumnBuilder<?, ?> LAST_NAME = Columns.column("Last Name", "last_name", DataTypes.stringType());
	
	public static String CLIENT_REPORT = "CLIENT";
	public static String CLIENT_REPORT_PATH = "D:/report/client.pdf";
	public static String CLIENT_REPORT_QUERY = "SELECT id, companyname,address,emailid,contactnumber FROM ekerp.client";
	
	/**************************** COLUMNS **********************************/
	public static ColumnBuilder<?, ?> CLIENT_ID = Columns.column("Client Id", "id", DataTypes.integerType());
	public static ColumnBuilder<?, ?> COMPANY_NAME = Columns.column("Company name", "companyname", DataTypes.stringType());
	public static ColumnBuilder<?, ?> EMAIL_ID = Columns.column("Email Id", "emailid", DataTypes.stringType());
	public static ColumnBuilder<?, ?> ADDRESS = Columns.column("Address", "address", DataTypes.stringType());
	public static ColumnBuilder<?, ?> CONTACT_NUMBER = Columns.column("Contact Number", "contactnumber", DataTypes.stringType());
	
	public static String RM_REPORT = "RM";
	public static String RM_REPORT_PATH = "D:/report/rm.pdf";
	public static String RM_REPORT_QUERY = "SELECT ID,description,part_number from ekerp.rawmaterial where rmTypeId = ";
	
	/*********************************COLUMNS**************************************/
	public static ColumnBuilder<?, ?> RM_ID = Columns.column("RM Id", "id", DataTypes.integerType());
	public static ColumnBuilder<?, ?> RM_DESCRIPTION = Columns.column("Description", "description", DataTypes.stringType());
	public static ColumnBuilder<?, ?> PART_NUMBER = Columns.column("RM Part Number", "part_number", DataTypes.stringType());
	
}
