package com.nextech.erp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nextech.erp.constants.ReportColumn;
import com.nextech.erp.dto.InputParameter;
import com.nextech.erp.dto.ReportInputDTO;
import com.nextech.erp.dto.ReportInputDataDTO;
import com.nextech.erp.dto.ReportQueryDataDTO;
import com.nextech.erp.model.Rawmaterialorder;
import com.nextech.erp.model.Report;
import com.nextech.erp.model.Reportinputassociation;
import com.nextech.erp.model.Reportinputparameter;
import com.nextech.erp.model.Reportoutputassociation;
import com.nextech.erp.model.User;
import com.nextech.erp.service.ReportService;
import com.nextech.erp.service.ReptInpAssoService;
import com.nextech.erp.service.ReptInpParaService;
import com.nextech.erp.service.ReptOptAssoService;
import com.nextech.erp.service.ReptOptParaService;
import com.nextech.erp.status.UserStatus;
import com.nextech.erp.util.DateUtil;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

@SuppressWarnings("deprecation")
@RestController
@Transactional @RequestMapping("/report")
public class ReportController {

	private static final String APPLICATION_XLS = "application/xls";
	private static final String APPLICATION_SCV = "application/csv";
	private static final String APPLICATION_PDF = "application/pdf";
	private static final String APPLICATION_JSON = "application/json";

	private static final long XLS = 1;
	private static final long CSV = 2;
	private static final long PDF = 3;

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	ReptInpAssoService reptInpAssoService;
	
	@Autowired
	ReptInpParaService inpParaService;

	@Autowired
	ReportService reportService;
	
	@Autowired
	ReptOptAssoService reptOptAssoService;
	
	@Autowired
	ReptOptParaService reptOptParaService;
	
	
	@Transactional @RequestMapping(value = "/query", method = RequestMethod.POST , produces = APPLICATION_JSON, headers = "Accept=application/json")
	public List<ReportInputDTO> fetchReport( @RequestBody ReportQueryDataDTO reportQueryDataDTO, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		Report report = reportService.getEntityById(Report.class, reportQueryDataDTO.getReportId());
		String query = report.getReportQuery();
		if(reportQueryDataDTO != null && reportQueryDataDTO.getData()!=null && !reportQueryDataDTO.getData().isEmpty()){
			for (InputParameter inputParameter : reportQueryDataDTO.getData()) {
				Reportinputparameter reportinputparameter = inpParaService.getEntityById(Reportinputparameter.class, inputParameter.getId());
				if(reportinputparameter.getInputType().equals("LIST")){
					query = query.replace("%"+inputParameter.getId()+"%", inputParameter.getValue().toString());
				}
				else if(reportinputparameter.getInputType().equals("TEXT")){
					query = query.replace("%"+inputParameter.getId()+"%", "\""+inputParameter.getValue()+"\"");
				}
				else if(reportinputparameter.getInputType().equals("DATE")){
					query = query.replace("%"+inputParameter.getId()+"%", "'"+DateUtil.convertToString(DateUtil.convertToDate(inputParameter.getValue().toString()))+"'");
				}
				else {
					query = query.replace("%"+inputParameter.getId()+"%", inputParameter.getValue().toString());
				}
			}
		}
		List<Reportoutputassociation> reportoutputassociations = reptOptAssoService.getReportOutputParametersByReportId(reportQueryDataDTO.getReportId());
		JasperReportBuilder jasperReportBuilder = DynamicReports.report();
		if(reportoutputassociations != null && !reportoutputassociations.isEmpty()){
			for (Reportoutputassociation reportoutputassociation : reportoutputassociations) {
				if(reportoutputassociation.getReportoutputparameter().getDatatype().equals("TEXT")){
					jasperReportBuilder.addColumn(Columns.column(reportoutputassociation.getReportoutputparameter().getDisplayName(), reportoutputassociation.getReportoutputparameter().getName(), DataTypes.stringType()));
				}else if(reportoutputassociation.getReportoutputparameter().getDatatype().equals("LONG")){
					jasperReportBuilder.addColumn(Columns.column(reportoutputassociation.getReportoutputparameter().getDisplayName(), reportoutputassociation.getReportoutputparameter().getName(), DataTypes.integerType()));
				}else if(reportoutputassociation.getReportoutputparameter().getDatatype().equals("DATE")){
					jasperReportBuilder.addColumn(Columns.column(reportoutputassociation.getReportoutputparameter().getDisplayName(), reportoutputassociation.getReportoutputparameter().getName(), DataTypes.dateType()));
				}
				
			}
			
		}
		Connection connection = sessionFactory.getSessionFactoryOptions().getServiceRegistry().getService(ConnectionProvider.class).getConnection();
		jasperReportBuilder.title(Components.text(report.getReport_Name()).setHorizontalAlignment(HorizontalAlignment.CENTER));
		File f = new File(report.getReportLocation());
		if(!f.exists())
			f.mkdirs();
		jasperReportBuilder.setDataSource(query, connection);
		downloadReport(jasperReportBuilder, reportQueryDataDTO.getReportType(), report.getReportLocation() + report.getFileName(), response);
		return null;
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional @RequestMapping(value = "/inputParameters/{id}", method = RequestMethod.GET, produces = APPLICATION_SCV, headers = "Accept=application/json")
	public List<ReportInputDTO> inputParameters(@PathVariable("id") long id, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		List<Reportinputassociation> list = reptInpAssoService.getReportInputParametersByReportId(id);
		List<ReportInputDTO> reprtInputList = new ArrayList<ReportInputDTO>();
		if (list != null && list.size() > 0) {
			for (Reportinputassociation reportinputassociation : list) {
				ReportInputDTO inputDTO = new ReportInputDTO();
				Reportinputparameter reportinputparameter = reportinputassociation.getReportinputparameter();
				if (reportinputparameter != null && reportinputparameter.isQueryParameter()) {
					Query query1 = sessionFactory.getCurrentSession().createSQLQuery(reportinputparameter.getQuery());
					query1.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
					List results = query1.list();
					List<ReportInputDataDTO> dataDTOs = new ArrayList<ReportInputDataDTO>();
					for (Object object : results) {
						Map<String, Object> data = (Map<String, Object>) object;
						ReportInputDataDTO dto = new ReportInputDataDTO();
						dto.setName(data.get("Name") + "");
						dto.setId(data.get("ID") + "");
						dataDTOs.add(dto);
					}
					inputDTO.setData(dataDTOs);
				}
				inputDTO.setDisplayName(reportinputparameter.getDisplayName());
				inputDTO.setInputType(reportinputparameter.getInputType());
				inputDTO.setId(reportinputparameter.getId());
				reprtInputList.add(inputDTO);
			}
		}
		return reprtInputList;
	}

	@Transactional @RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = APPLICATION_SCV, headers = "Accept=application/json")
	public UserStatus login(@PathVariable("id") long id, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		Connection connection = null;
		try {
			connection = sessionFactory.getSessionFactoryOptions().getServiceRegistry()
					.getService(ConnectionProvider.class).getConnection();
			JasperReportBuilder report = DynamicReports.report();
			report.addColumn(ReportColumn.USER_ID);
			report.addColumn(ReportColumn.FIRST_NAME);
			report.addColumn(ReportColumn.LAST_NAME);
			report.title(Components.text(ReportColumn.USER_REPORT).setHorizontalAlignment(HorizontalAlignment.CENTER));
			report.setDataSource(ReportColumn.USER_REPORT_QUERY, connection);
			String fileName = ReportColumn.USER_REPORT_PATH;

			downloadReport(report, id, fileName, response);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	@Transactional @RequestMapping(value = "/client/{id}", method = RequestMethod.GET, produces = APPLICATION_SCV, headers = "Accept=application/json")
	public UserStatus getClinetList(@PathVariable("id") long id, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		Connection connection = null;
		try {
			connection = sessionFactory.getSessionFactoryOptions().getServiceRegistry()
					.getService(ConnectionProvider.class).getConnection();
			JasperReportBuilder report = DynamicReports.report();
			report.addColumn(ReportColumn.CLIENT_ID);
			report.addColumn(ReportColumn.COMPANY_NAME);
			report.addColumn(ReportColumn.EMAIL_ID);
			report.addColumn(ReportColumn.ADDRESS);
			report.addColumn(ReportColumn.CONTACT_NUMBER);
			report.title(Components.text(ReportColumn.CLIENT_REPORT).setHorizontalAlignment(HorizontalAlignment.CENTER));
			report.setDataSource(ReportColumn.CLIENT_REPORT_QUERY, connection);
			String fileName = ReportColumn.CLIENT_REPORT_PATH;

			downloadReport(report, id, fileName, response);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	@Transactional @RequestMapping(value = "/clientReport", method = RequestMethod.POST , produces = APPLICATION_JSON, headers = "Accept=application/json")
	public List<ReportInputDTO> fetchReportClient( @RequestBody ReportQueryDataDTO reportQueryDataDTO, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		Report report = reportService.getEntityById(Report.class, reportQueryDataDTO.getReportId());
		String query = report.getReportQuery();
		if(reportQueryDataDTO != null && reportQueryDataDTO.getData()!=null && !reportQueryDataDTO.getData().isEmpty()){
			for (InputParameter inputParameter : reportQueryDataDTO.getData()) {
				Reportinputparameter reportinputparameter = inpParaService.getEntityById(Reportinputparameter.class, inputParameter.getId());
				if(reportinputparameter.getInputType().equals("LIST")){
					query = query.replace("%"+inputParameter.getId()+"%", inputParameter.getValue().toString());
				}
				else if(reportinputparameter.getInputType().equals("TEXT")){
					query = query.replace("%"+inputParameter.getId()+"%", "\""+inputParameter.getValue()+"\"");
				}
				else if(reportinputparameter.getInputType().equals("DATE")){
					query = query.replace("%"+inputParameter.getId()+"%", "'"+DateUtil.convertToString(DateUtil.convertToDate(inputParameter.getValue().toString()))+"'");
				}
				else {
					query = query.replace("%"+inputParameter.getId()+"%", inputParameter.getValue().toString());
				}
			}
		}
		List<Reportoutputassociation> reportoutputassociations = reptOptAssoService.getReportOutputParametersByReportId(reportQueryDataDTO.getReportId());
		JasperReportBuilder jasperReportBuilder = DynamicReports.report();
		if(reportoutputassociations != null && !reportoutputassociations.isEmpty()){
			for (Reportoutputassociation reportoutputassociation : reportoutputassociations) {
				if(reportoutputassociation.getReportoutputparameter().getDatatype().equals("TEXT")){
					jasperReportBuilder.addColumn(Columns.column(reportoutputassociation.getReportoutputparameter().getDisplayName(), reportoutputassociation.getReportoutputparameter().getName(), DataTypes.stringType()));
				}else if(reportoutputassociation.getReportoutputparameter().getDatatype().equals("LONG")){
					jasperReportBuilder.addColumn(Columns.column(reportoutputassociation.getReportoutputparameter().getDisplayName(), reportoutputassociation.getReportoutputparameter().getName(), DataTypes.integerType()));
				}else if(reportoutputassociation.getReportoutputparameter().getDatatype().equals("DATE")){
					jasperReportBuilder.addColumn(Columns.column(reportoutputassociation.getReportoutputparameter().getDisplayName(), reportoutputassociation.getReportoutputparameter().getName(), DataTypes.dateType()));
				}
				
			}
			
		}
		Connection connection = sessionFactory.getSessionFactoryOptions().getServiceRegistry().getService(ConnectionProvider.class).getConnection();
		jasperReportBuilder.title(Components.text(report.getReport_Name()).setHorizontalAlignment(HorizontalAlignment.CENTER));
		File f = new File(report.getReportLocation());
		if(!f.exists())
			f.mkdirs();
		jasperReportBuilder.setDataSource(query, connection);
		downloadReport(jasperReportBuilder, reportQueryDataDTO.getReportType(), report.getReportLocation() + report.getFileName(), response);
		return null;
	}

	//@Scheduled(initialDelay=10000, fixedRate=60000)
	private void executeSchedular(){
		System.out.println("Executed Scheduled method.");
	}
	
	private void downloadReport(JasperReportBuilder report, long reportType, String fileName,
			HttpServletResponse response) {
		try {
			if (reportType == XLS) {
				fileName = fileName + ".xls";
				report.toXls(new FileOutputStream(fileName));
				response.setContentType(APPLICATION_XLS);
			} else if (reportType == CSV) {
				fileName = fileName + ".csv";
				report.toCsv(new FileOutputStream(fileName));
				response.setContentType(APPLICATION_SCV);
			} else if (reportType == PDF) {
				fileName = fileName + ".pdf";
				report.toPdf(new FileOutputStream(fileName));
				response.setContentType(APPLICATION_PDF);
			}
			File file = new File(fileName);
			InputStream in = new FileInputStream(file);

			response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
			response.setHeader("Content-Length", String.valueOf(file.length()));
			FileCopyUtils.copy(in, response.getOutputStream());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DRException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/generateReport", method = RequestMethod.POST)
	public String generateReport(@Valid @ModelAttribute("rawmaterialorder") Rawmaterialorder rawmaterialorder,BindingResult result,Model model, HttpServletRequest request,HttpServletResponse response) throws ParseException {
	 
	    if (result.hasErrors()) {
	        System.out.println("validation error occured in jasper input form");
	        return "loadJasper";
	 
	    }
	 
	    String reportFileName = "JREmp1";
	 
	    Connection conn = null;
	    try {
	        try {
	 
	             Class.forName("com.mysql.jdbc.Driver");
	            } catch (ClassNotFoundException e) {
	                System.out.println("Please include Classpath Where your MySQL Driver is located");
	                e.printStackTrace();
	            }  
	 
	         conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/root","root","******");
	 
	     if (conn != null)
	     {
	         System.out.println("Database Connected");
	     }
	     else
	     {
	         System.out.println(" connection Failed ");
	     }
	 
	          String rptFormat = "pdf";
	          long noy = rawmaterialorder.getStatus().getId();
	 
	          System.out.println("rpt format " + rptFormat);
	          System.out.println("no of years " + noy);
	 
	           //Parameters as Map to be passed to Jasper
	           HashMap<String,Object> hmParams=new HashMap<String,Object>();
	 
	           hmParams.put("noy", new Long(noy));
	 
	                   hmParams.put("Title", "Rawmaterialorder working more than "+ noy + " Years");
	 
	            JasperReport jasperReport = getCompiledFile(reportFileName, request);
	 

	         if  (rptFormat.equalsIgnoreCase("pdf") )  {
	 
	            generateReportPDF(response, hmParams, jasperReport, conn); // For PDF report
	 
	            }
	 
	       } catch (Exception sqlExp) {
	 
	           System.out.println( "Exception::" + sqlExp.toString());
	 
	       } finally {
	 
	            try {
	 
	            if (conn != null) {
	                conn.close();
	                conn = null;
	            }
	 
	            } catch (SQLException expSQL) {
	 
	                System.out.println("SQLExp::CLOSING::" + expSQL.toString());
	 
	            }
	 
	           }
	 
	return null;
	 
	}
	 
	private JasperReport getCompiledFile(String fileName, HttpServletRequest request) throws JRException {
	    System.out.println("path " + request.getSession().getServletContext().getRealPath("/jasper/" + fileName + ".jasper"));
	    File reportFile = new File( request.getSession().getServletContext().getRealPath("/jasper/" + fileName + ".jasper"));
	    // If compiled file is not found, then compile XML template
	    if (!reportFile.exists()) {
	               JasperCompileManager.compileReportToFile(request.getSession().getServletContext().getRealPath("/jasper/" + fileName + ".jrxml"),request.getSession().getServletContext().getRealPath("/jasper/" + fileName + ".jasper"));
	        }
	        JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportFile.getPath());
	       return jasperReport;
	    } 
	 
	 
	    private void generateReportPDF (HttpServletResponse resp, Map parameters, JasperReport jasperReport, Connection conn)throws JRException, NamingException, SQLException, IOException {
	        byte[] bytes = null;
	        bytes = JasperRunManager.runReportToPdf(jasperReport,parameters,conn);
	        resp.reset();
	        resp.resetBuffer();
	        resp.setContentType("application/pdf");
	        resp.setContentLength(bytes.length);
	        ServletOutputStream ouputStream = resp.getOutputStream();
	        ouputStream.write(bytes, 0, bytes.length);
	        ouputStream.flush();
	        ouputStream.close();
	    } 
	 
	}
