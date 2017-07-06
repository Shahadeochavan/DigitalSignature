package com.nextech.erp.constants;

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
}
