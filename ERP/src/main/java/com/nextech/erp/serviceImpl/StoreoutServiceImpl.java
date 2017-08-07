package com.nextech.erp.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dao.ProductionplanningDao;
import com.nextech.erp.dao.RawmaterialDao;
import com.nextech.erp.dao.RawmaterialinventoryDao;
import com.nextech.erp.dao.StatusDao;
import com.nextech.erp.dao.StoreoutDao;
import com.nextech.erp.dao.StoreoutrmDao;
import com.nextech.erp.dao.StoreoutrmassociationDao;
import com.nextech.erp.dto.StoreOutDTO;
import com.nextech.erp.dto.StoreOutPart;
import com.nextech.erp.factory.StoreoutRequestResponseFactory;
import com.nextech.erp.model.Productionplanning;
import com.nextech.erp.model.Rawmaterial;
import com.nextech.erp.model.Rawmaterialinventory;
import com.nextech.erp.model.Status;
import com.nextech.erp.model.Storeout;
import com.nextech.erp.model.Storeoutrm;
import com.nextech.erp.model.Storeoutrmassociation;
import com.nextech.erp.service.StoreoutService;
import com.nextech.erp.status.UserStatus;
@Service
public class StoreoutServiceImpl extends CRUDServiceImpl<Storeout> implements StoreoutService{

	@Autowired
	StoreoutDao storeoutDao;
	
	@Autowired
	ProductionplanningDao productionplanningDao;
	
	@Autowired
	StatusDao statusDao;
	
	@Autowired
	RawmaterialDao rawmaterialDao;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	RawmaterialinventoryDao rawmaterialinventoryDao;
	
	@Autowired
	StoreoutrmDao storeoutrmDao;
	
	@Autowired
	StoreoutrmassociationDao storeoutrmassociationDao;
	
	@Override
	public UserStatus addStoreOutRM(StoreOutDTO storeOutDTO, HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		
		Productionplanning productionplanning = productionplanningDao.getById(Productionplanning.class,
				storeOutDTO.getProductionPlanId());
		Storeout storeout = StoreoutRequestResponseFactory.setStoreOut(storeOutDTO, request);
		storeout.setStatus(statusDao.getById(Status.class,Long.parseLong(messageSource.getMessage(ERPConstants.ADDED_STORE_OUT, null, null))));
		storeoutDao.add(storeout);

		for (StoreOutPart storeOutPart : storeOutDTO.getStoreOutParts()) {
			Storeoutrm storeoutrm = setStoreParts(storeOutPart);
			Rawmaterialinventory rawmaterialinventory = rawmaterialinventoryDao.getByRMId(storeoutrm.getRawmaterial().getId());
			if (rawmaterialinventory.getRawmaterial().getId() == storeoutrm.getRawmaterial().getId()) {
				// to check RM inventory quantity and storeout quantity
				if (rawmaterialinventory.getQuantityAvailable() >= storeoutrm.getQuantityDispatched()) {
					storeoutrm.setDescription(storeOutDTO.getDescription());
					storeoutrm.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
					if(storeOutPart.getQuantityRequired()==storeOutPart.getQuantityDispatched())
						storeoutrm.setStatus(statusDao.getById(Status.class,Long.parseLong(messageSource.getMessage(ERPConstants.STORE_OUT_COMPLETE, null, null))));
					else if(storeOutPart.getQuantityRequired()<storeOutPart.getQuantityDispatched())
						storeoutrm.setStatus(statusDao.getById(Status.class,Long.parseLong(messageSource.getMessage(ERPConstants.STORE_OUT_PARTIAL, null, null))));
				} else {
					return new UserStatus(0,messageSource.getMessage(ERPConstants.TO_CHECK_QUANTITY_IN_RMINVENTORY, null, null));
				}

				Storeoutrmassociation storeoutrmassociation = new Storeoutrmassociation();
				storeoutrmassociation.setStoreout(storeout);
				storeoutrmassociation.setStoreoutrm(storeoutrm);
				storeoutrmassociation.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
				storeoutrmassociation.setIsactive(true);
				rawmaterialinventory.setQuantityAvailable(rawmaterialinventory.getQuantityAvailable() - storeoutrm.getQuantityDispatched());
				storeoutrmDao.add(storeoutrm);
				storeoutrmassociationDao.add(storeoutrmassociation);
				rawmaterialinventoryDao.update(rawmaterialinventory);
			} else {
				// Please add RM to Inventory
			}
		}
		productionplanning.setStatus(statusDao.getById(Status.class,
				Long.parseLong(messageSource.getMessage(ERPConstants.PRODUCTION_PLAN_READY_TO_START, null, null))));
		if(!storeOutDTO.isSelectedStoreOut()){
			productionplanning.setStoreOut_quantity(productionplanning.getStoreOut_quantity() + storeOutDTO.getQuantityRequired());
		}
		productionplanningDao.update(productionplanning);
		return new UserStatus(1, "Storeout added Successfully !");
		
	}
	private Storeoutrm setStoreParts(StoreOutPart storeOutPart) throws Exception {
		Storeoutrm storeoutrm = new Storeoutrm();
		storeoutrm.setRawmaterial(rawmaterialDao.getById(Rawmaterial.class, storeOutPart.getRawmaterial()));
		storeoutrm.setQuantityRequired(storeOutPart.getQuantityRequired());
		storeoutrm.setQuantityDispatched(storeOutPart.getQuantityDispatched());
		storeoutrm.setIsactive(true);
		return storeoutrm;
	}
	@Override
	public List<StoreOutDTO> getStoreOutlist() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public StoreOutDTO getStoreOutById(long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void deleteStoreOutById(long id) throws Exception {
		// TODO Auto-generated method stub
		Storeout storeout = storeoutDao.getById(Storeout.class, id);
		storeout.setIsactive(false);
		storeoutDao.update(storeout);
		
	}
}
