package com.nextech.erp.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dao.ProductionplanningDao;
import com.nextech.erp.dao.ProductorderassociationDao;
import com.nextech.erp.dto.ProductProductionPlan;
import com.nextech.erp.dto.ProductionPlan;
import com.nextech.erp.factory.ProductionPlanningRequestResponseFactory;
import com.nextech.erp.model.Product;
import com.nextech.erp.model.Productinventory;
import com.nextech.erp.model.Productionplanning;
import com.nextech.erp.model.Productorderassociation;
import com.nextech.erp.model.Status;
import com.nextech.erp.newDTO.ProductionPlanningDTO;
import com.nextech.erp.service.ProductService;
import com.nextech.erp.service.ProductinventoryService;
import com.nextech.erp.service.ProductionplanningService;
import com.nextech.erp.service.ProductorderService;
import com.nextech.erp.service.ProductorderassociationService;
import com.nextech.erp.service.StatusService;
@Service
public class ProductionplanningServiceImpl extends
		CRUDServiceImpl<Productionplanning> implements
		ProductionplanningService {

	@Autowired
	ProductionplanningDao productionplanningDao;

	@Autowired
	ProductinventoryService productinventoryService;

	@Autowired
	ProductorderassociationService productorderassociationService;

	@Autowired
	ProductorderService productorderService;

	@Autowired
	ProductService productService;
	@Autowired
	StatusService statusService;

	@Autowired
	ProductorderassociationDao productorderassociationDao;

	@Autowired
	private MessageSource messageSource;

	@Override
	public Productionplanning getProductionPlanningforCurrentMonthByProductIdAndDate(
			long pId,Date date) throws Exception {
		
		return productionplanningDao.getProductionPlanningforCurrentMonthByProductIdAndDate(pId,date);
	}

	@Override
	public List<ProductionPlanningDTO> getProductionplanningByMonth(
			Date month) throws Exception {
		
		List<ProductionPlanningDTO> productionPlanningDTOs =  new ArrayList<ProductionPlanningDTO>();
		List<Productionplanning> productionplannings =  productionplanningDao.getProductionplanningByCurrentMonth(month);
		if(productionplannings.isEmpty()){
			return null;
		}
		for (Productionplanning productionplanning : productionplannings) {
			ProductionPlanningDTO productionPlanningDTO = ProductionPlanningRequestResponseFactory.setProductionPlanningDTO(productionplanning);
			productionPlanningDTOs.add(productionPlanningDTO);
		}
		return productionPlanningDTOs;
	}
	@Override
	public ProductionPlanningDTO getProductionplanByDateAndProductId(Date date,long pId) throws Exception {
		
		Productionplanning productionplanning = productionplanningDao.getProductionplanByDateAndProductId(date,pId);
		if(productionplanning==null){
			return null;
		}
		ProductionPlanningDTO productionPlanningDTO = ProductionPlanningRequestResponseFactory.setProductionPlanningDTO(productionplanning);
		return productionPlanningDTO;
	}
	@Override
	public List<ProductionPlan> getProductionPlanForCurrentMonth(
			) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date startDate = cal.getTime();
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date endDate = cal.getTime();
		List<Productionplanning> productionplanningList = productionplanningDao.getProductionPlanByMonthYear(startDate,endDate);
		return getProductionPlan(getProductProductPlanMap(productionplanningList), new SimpleDateFormat("MM-yyyy").format(new Date()));
	}

	private List<ProductionPlan> getProductionPlan(
			Map<Long, List<ProductProductionPlan>> productProductPlanMap, String month_year) throws Exception {
		Set<Entry<Long, List<ProductProductionPlan>>> productionPlanEntries = productProductPlanMap.entrySet();
		List<ProductionPlan> productionPlans = new ArrayList<>();
		for (Iterator<Entry<Long, List<ProductProductionPlan>>> iterator = productionPlanEntries.iterator(); iterator.hasNext();) {
			Entry<Long, List<ProductProductionPlan>> entry = (Entry<Long, List<ProductProductionPlan>>) iterator.next();
			Productinventory productinventory = productinventoryService.getProductinventoryByProductId(entry.getKey());
			List<Productorderassociation> productorderassociations = productorderassociationService.getProductorderassociationByProductId(entry.getKey());
			long remainingAmt = 0;
			if(productorderassociations != null){
				for (Productorderassociation productorderassociation : productorderassociations) {
					remainingAmt = remainingAmt + productorderassociation.getRemainingQuantity();
				}
			}
			ProductionPlan productionPlan = new ProductionPlan();
			productionPlan.setProductId(entry.getKey());
			productionPlan.setProductBalanceQty(productinventory == null ? 0 : productinventory.getQuantityavailable());
			productionPlan.setProductTargetQty(remainingAmt);
			productionPlan.setProductProductionPlan(entry.getValue());
			productionPlan.setProductName(productService.getEntityById(Product.class, entry.getKey()).getPartNumber());
			productionPlan.setMonth_year(month_year);
			productionPlans.add(productionPlan);
		}
		return productionPlans;
	}

	private Map<Long,List<ProductProductionPlan>> getProductProductPlanMap(
			List<Productionplanning> productionplanningList) {
		Map<Long,List<ProductProductionPlan>> productionPlanMap = new HashMap<Long, List<ProductProductionPlan>>();
		for (Iterator<Productionplanning> iterator = productionplanningList.iterator(); iterator.hasNext();) {
			Productionplanning productionplanning = (Productionplanning) iterator.next();
			List<ProductProductionPlan> productProductionPlans = null;
			if(productionPlanMap.get(productionplanning.getProduct().getId()) == null){
				productProductionPlans = new ArrayList<ProductProductionPlan>();
			}else{
				productProductionPlans = productionPlanMap.get(productionplanning.getProduct().getId());
			}
			productProductionPlans.add(getProductProductionPlanBean(productionplanning));
			productionPlanMap.put(productionplanning.getProduct().getId(), productProductionPlans);
		}
		return productionPlanMap;
	}

	private ProductProductionPlan getProductProductionPlanBean(
			Productionplanning productionplanning) {
		ProductProductionPlan productProductionPlan = new ProductProductionPlan();
		productProductionPlan.setAchived_quantity(productionplanning.getCompletedQuantity());
		productProductionPlan.setDispatch_quantity(productionplanning.getDispatchQuantity());
		productProductionPlan.setTarget_quantity(productionplanning.getTargetQuantity());
		productProductionPlan.setProductionDate(productionplanning.getDate());
		return productProductionPlan;
	}

	@Override
	public List<ProductionPlanningDTO> updateProductionPlanByMonthYear(
			String month_year) throws Exception {
		
		List<ProductionPlanningDTO> productionPlanningDTOs =  new ArrayList<ProductionPlanningDTO>();
		List<Productionplanning> productionplannings =  productionplanningDao.updateProductionPlanByMonthYear(month_year);
		if(productionplannings.isEmpty()){
			return null;
		}
		for (Productionplanning productionplanning : productionplannings) {
			ProductionPlanningDTO productionPlanningDTO = ProductionPlanningRequestResponseFactory.setProductionPlanningDTO(productionplanning);
			productionPlanningDTOs.add(productionPlanningDTO);
		}
		return productionPlanningDTOs;
	}

	@Override
	public List<Productionplanning> createProductionPlanMonthYear(List<Product> productList,String month_year,HttpServletRequest request,HttpServletResponse response) throws Exception {
		List<Productionplanning> productionPlanList = new ArrayList<Productionplanning>();
		Productionplanning productionplanning = null;
		Calendar cal = Calendar.getInstance();

		for (Iterator<Product> iterator = productList.iterator(); iterator.hasNext();) {
			Product product = (Product) iterator.next();
			cal.setTime(new Date());
			cal.set(Calendar.DAY_OF_MONTH, 1);
			int myMonth=cal.get(Calendar.MONTH);
			List<Productorderassociation> productorderassociations = productorderassociationDao.getIncompleteProductOrderAssoByProductId(product.getId());
			if(productorderassociations != null && !productorderassociations.isEmpty()){
					while (myMonth==cal.get(Calendar.MONTH)) {
				
						productionplanning = productorderassociationService.getProductionPlanningforCurrentMonthByProductIdAndDate(
								product.getId(),
								cal.getTime());
						if ( productionplanning == null){
							productionplanning = new Productionplanning();
							productionplanning.setProduct(product);
							productionplanning.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
							productionplanning.setIsactive(true);
							productionplanning.setStatus(statusService.getEntityById(Status.class, Long.parseLong(messageSource.getMessage(ERPConstants.PROD_PLAN_NEW, null, null))));
							productionplanning.setDate(cal.getTime());
							productionplanningDao.add(productionplanning);
						} else {
							System.out.println("Production Plan exists for Product : " + product.getId());
						}
						productionPlanList.add(productionplanning);
						System.out.print("product : " + product.getId() +" Date :" + new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()));
						  cal.add(Calendar.DAY_OF_MONTH, 1);
					}
				}else{
					System.out.println("There are no orders for Product so there is no need to create a production plan : " + product.getId());
				}
		}
		return productionPlanList;
	}

	@Override
	public void updateProductionplanningForCurrentMonth(
			List<ProductionPlan> productionplanningList,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Calendar cal = Calendar.getInstance();
		for (Iterator<ProductionPlan> iterator = productionplanningList.iterator(); iterator
				.hasNext();) {
			ProductionPlan productionPlan = (ProductionPlan) iterator.next();
			List<ProductProductionPlan> productProductionPlans = productionPlan.getProductProductionPlan();
			for (Iterator<ProductProductionPlan> iterator2 = productProductionPlans.iterator(); iterator2
					.hasNext();) {
				ProductProductionPlan productProductionPlan = (ProductProductionPlan) iterator2
						.next();
				cal.setTime(productProductionPlan.getProductionDate());
				cal.set(Calendar.HOUR, 0);
				cal.set(Calendar.MINUTE, 0);
				Date productionDateStart = cal.getTime();
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.HOUR, 23);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);
				Date productionDateEnd = cal.getTime();
				Productionplanning productionplanning = productionplanningDao.getProductionPlanningByDateAndProductId(productionDateStart, productionDateEnd, productionPlan.getProductId());
				productionplanning.setCompletedQuantity(productProductionPlan.getAchived_quantity());
				productionplanning.setDispatchQuantity(productProductionPlan.getDispatch_quantity());
				productionplanning.setTargetQuantity(productProductionPlan.getTarget_quantity());
				Productorderassociation productorderassociation = productorderassociationDao.getById(Productorderassociation.class, productionplanning.getProduct().getId());
				productionplanning.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
				productionplanningDao.update(productionplanning);
			}
		}
	}
	@Override
	public List<ProductionPlanningDTO> getProductionplanByDate(Date date)
			throws Exception {
		List<ProductionPlanningDTO> productionPlanningDTOs =  new ArrayList<ProductionPlanningDTO>();
		List<Productionplanning> productionplannings = productionplanningDao.getProductionplanByDate(date);
		if(productionplannings.isEmpty()){
			return null;
		}
		for (Productionplanning productionplanning : productionplannings) {
			ProductionPlanningDTO productionPlanningDTO = ProductionPlanningRequestResponseFactory.setProductionPlanningDTO(productionplanning);
			productionPlanningDTOs.add(productionPlanningDTO);
		}
		return productionPlanningDTOs;
	}

	@Override
	public List<ProductionPlanningDTO> getProductionplanByProdutId(Date date,long productID)
			throws Exception {
		List<ProductionPlanningDTO> productionPlanningDTOs =  new ArrayList<ProductionPlanningDTO>();
		List<Productionplanning> productionplannings = productionplanningDao.getProductionplanByProdutId(date,productID);
		if(productionplannings.isEmpty()){
			return null;
		}
		for (Productionplanning productionplanning : productionplannings) {
			ProductionPlanningDTO productionPlanningDTO = ProductionPlanningRequestResponseFactory.setProductionPlanningDTO(productionplanning);
			productionPlanningDTOs.add(productionPlanningDTO);
		}
		return productionPlanningDTOs;
	}

	@Override
	public List<ProductionPlanningDTO> getProductionPlanList() throws Exception {
		
		List<ProductionPlanningDTO> productionPlanningDTOs =  new ArrayList<ProductionPlanningDTO>();
		List<Productionplanning> productionplannings = productionplanningDao.getList(Productionplanning.class);
		if(productionplannings.isEmpty()){
			return null;
		}
		for (Productionplanning productionplanning : productionplannings) {
			ProductionPlanningDTO productionPlanningDTO = ProductionPlanningRequestResponseFactory.setProductionPlanningDTO(productionplanning);
			productionPlanningDTOs.add(productionPlanningDTO);
		}
		return productionPlanningDTOs;
	}

	@Override
	public ProductionPlanningDTO getProductionPlanById(long id)
			throws Exception {
		Productionplanning productionplanning = productionplanningDao.getById(Productionplanning.class, id);
		if(productionplanning==null){
			return null;
		}
		ProductionPlanningDTO productionPlanningDTO = ProductionPlanningRequestResponseFactory.setProductionPlanningDTO(productionplanning);
		return productionPlanningDTO;
	}

	@Override
	public ProductionPlanningDTO deleteProduction(long id) throws Exception {
		Productionplanning productionplanning = productionplanningDao.getById(Productionplanning.class, id);
		if(productionplanning==null){
			return null;
		}
		productionplanning.setIsactive(false);
		productionplanningDao.update(productionplanning);
		ProductionPlanningDTO productionPlanningDTO = ProductionPlanningRequestResponseFactory.setProductionPlanningDTO(productionplanning);
		return productionPlanningDTO;
	}
}
