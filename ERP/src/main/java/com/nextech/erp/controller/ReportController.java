package com.nextech.erp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
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
import com.nextech.erp.model.Report;
import com.nextech.erp.model.Reportinputassociation;
import com.nextech.erp.model.Reportinputparameter;
import com.nextech.erp.model.Reportoutputassociation;
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

@SuppressWarnings("deprecation")
@RestController
@RequestMapping("/report")
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
	
	@Transactional
	@RequestMapping(value = "/query", method = RequestMethod.POST , produces = APPLICATION_JSON, headers = "Accept=application/json")
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

	@Transactional
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/inputParameters/{id}", method = RequestMethod.GET, produces = APPLICATION_SCV, headers = "Accept=application/json")
	public List<ReportInputDTO> inputParameters(@PathVariable("id") long id, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		List<Reportinputassociation> list = reptInpAssoService.getReportInputParametersByReportId(id);
		List<ReportInputDTO> reprtInputList = new ArrayList<ReportInputDTO>();
		if (list != null && list.size() > 0) {
			for (Reportinputassociation reportinputassociation : list) {
				ReportInputDTO inputDTO = new ReportInputDTO();
				Reportinputparameter reportinputparameter = reportinputassociation.getReportinputparameter();
				if (reportinputparameter != null && reportinputparameter.isQueryParameter()) {
					Query query1 = sessionFactory.openSession().createSQLQuery(reportinputparameter.getQuery());
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

	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = APPLICATION_SCV, headers = "Accept=application/json")
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

	@Scheduled(initialDelay=10000, fixedRate=60000)
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
}