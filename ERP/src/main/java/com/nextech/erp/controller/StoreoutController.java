package com.nextech.erp.controller;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dto.StoreOutDTO;
import com.nextech.erp.dto.StoreOutPart;
import com.nextech.erp.model.Product;
import com.nextech.erp.model.Productionplanning;
import com.nextech.erp.model.Rawmaterial;
import com.nextech.erp.model.Rawmaterialinventory;
import com.nextech.erp.model.Status;
import com.nextech.erp.model.Storeout;
import com.nextech.erp.model.Storeoutrm;
import com.nextech.erp.model.Storeoutrmassociation;
import com.nextech.erp.service.ProductService;
import com.nextech.erp.service.ProductionplanningService;
import com.nextech.erp.service.RawmaterialService;
import com.nextech.erp.service.RawmaterialinventoryService;
import com.nextech.erp.service.StatusService;
import com.nextech.erp.service.StoreoutService;
import com.nextech.erp.service.StoreoutrmService;
import com.nextech.erp.service.StoreoutrmassociationService;
import com.nextech.erp.status.UserStatus;

@Controller
@RequestMapping("/storeout")
public class StoreoutController {

	@Autowired
	StoreoutService storeoutService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	ProductionplanningService productionplanningService;

	@Autowired
	ProductService productService;

	@Autowired
	StatusService statusService;

	@Autowired
	RawmaterialService rawmaterialService;

	@Autowired
	StoreoutrmService storeoutrmService;

	@Autowired
	StoreoutrmassociationService storeoutrmassociationService;

	@Autowired
	RawmaterialinventoryService rawmaterialinventoryService;

	@RequestMapping(value = "/createStoreOut", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addStoreout(@Valid @RequestBody StoreOutDTO storeOutDTO,
			BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError().getDefaultMessage());
			}
			Productionplanning productionplanning = productionplanningService.getEntityById(Productionplanning.class,
					storeOutDTO.getProductionPlanId());
			Storeout storeout = new Storeout();
			storeout.setQuantityRequired(storeOutDTO.getQuantityRequired());
			storeout.setDescription(storeOutDTO.getDescription());
			storeout.setProduct(productService.getEntityById(Product.class, storeOutDTO.getProductId()));
			storeout.setProductionplanning(productionplanningService.getEntityById(Productionplanning.class,
					storeOutDTO.getProductionPlanId()));
			storeout.setStatus(statusService.getEntityById(Status.class,
					Long.parseLong(messageSource.getMessage(ERPConstants.ADDED_STORE_OUT, null, null))));
			storeout.setIsactive(true);
			storeout.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			storeout.setSelectedStoreOut(storeOutDTO.isSelectedStoreOut());
			storeoutService.addEntity(storeout);

			for (StoreOutPart storeOutPart : storeOutDTO.getStoreOutParts()) {

				Storeoutrm storeoutrm = setStoreParts(storeOutPart);
				Rawmaterialinventory rawmaterialinventory = rawmaterialinventoryService.getByRMId(storeoutrm.getRawmaterial().getId());
				if (rawmaterialinventory.getRawmaterial().getId() == storeoutrm.getRawmaterial().getId()) {

					// to check RM inventory quantity and storeout quantity
					if (rawmaterialinventory.getQuantityAvailable() >= storeoutrm.getQuantityDispatched()) {
						storeoutrm.setDescription(storeOutDTO.getDescription());
						storeoutrm.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
						if(storeOutPart.getQuantityRequired()==storeOutPart.getQuantityDispatched())
							storeoutrm.setStatus(statusService.getEntityById(Status.class,Long.parseLong(messageSource.getMessage(ERPConstants.STORE_OUT_COMPLETE, null, null))));
						else if(storeOutPart.getQuantityRequired()<storeOutPart.getQuantityDispatched())
							storeoutrm.setStatus(statusService.getEntityById(Status.class,Long.parseLong(messageSource.getMessage(ERPConstants.STORE_OUT_PARTIAL, null, null))));
					} else {
						return new UserStatus(0,messageSource.getMessage(ERPConstants.TO_CHECK_QUANTITY_IN_RMINVENTORY, null, null));
					}

					Storeoutrmassociation storeoutrmassociation = new Storeoutrmassociation();
					storeoutrmassociation.setStoreout(storeout);
					storeoutrmassociation.setStoreoutrm(storeoutrm);
					storeoutrmassociation.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
					storeoutrmassociation.setIsactive(true);
					rawmaterialinventory.setQuantityAvailable(rawmaterialinventory.getQuantityAvailable() - storeoutrm.getQuantityDispatched());
					storeoutrmService.addEntity(storeoutrm);
					storeoutrmassociationService.addEntity(storeoutrmassociation);
					rawmaterialinventoryService.updateEntity(rawmaterialinventory);
				} else {
					// Please add RM to Inventory
				}
			}
			productionplanning.setStatus(statusService.getEntityById(Status.class,
					Long.parseLong(messageSource.getMessage(ERPConstants.PRODUCTION_PLAN_READY_TO_START, null, null))));
			if(!storeOutDTO.isSelectedStoreOut()){
				productionplanning.setStoreOut_quantity(productionplanning.getStoreOut_quantity() + storeOutDTO.getQuantityRequired());
			}
			productionplanningService.updateEntity(productionplanning);
			return new UserStatus(1, "Storeout added Successfully !");
		} catch (ConstraintViolationException cve) {
			System.out.println("Inside ConstraintViolationException");
			cve.printStackTrace();
			return new UserStatus(0, cve.getCause().getMessage());
		} catch (PersistenceException pe) {
			System.out.println("Inside PersistenceException");
			pe.printStackTrace();
			return new UserStatus(0, pe.getCause().getMessage());
		} catch (Exception e) {
			System.out.println("Inside Exception");
			e.printStackTrace();
			return new UserStatus(0, e.getCause().getMessage());
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Storeout getStoreout(@PathVariable("id") long id) {
		Storeout Storeout = null;
		try {
			Storeout = storeoutService.getEntityById(Storeout.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Storeout;
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateStoreout(@RequestBody Storeout Storeout, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Storeout.setIsactive(true);
			Storeout.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			storeoutService.updateEntity(Storeout);
			return new UserStatus(1, "Storeout update Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Storeout> getStoreout() {

		List<Storeout> userList = null;
		try {
			userList = storeoutService.getEntityList(Storeout.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return userList;
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteStoreout(@PathVariable("id") long id) {

		try {
			Storeout Storeout = storeoutService.getEntityById(Storeout.class, id);
			Storeout.setIsactive(false);
			storeoutService.updateEntity(Storeout);
			return new UserStatus(1, "Storeout deleted Successfully !");
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}

	}

	private Storeoutrm setStoreParts(StoreOutPart storeOutPart) throws Exception {
		Storeoutrm storeoutrm = new Storeoutrm();
		storeoutrm.setRawmaterial(rawmaterialService.getEntityById(Rawmaterial.class, storeOutPart.getRawmaterial()));
		storeoutrm.setQuantityRequired(storeOutPart.getQuantityRequired());
		storeoutrm.setQuantityDispatched(storeOutPart.getQuantityDispatched());
		storeoutrm.setIsactive(true);
		return storeoutrm;
	}

	private void saveStoreOut(StoreOutDTO storeOutDTO, HttpServletRequest request) throws Exception {
		Productionplanning productionplanning = productionplanningService.getEntityById(Productionplanning.class,
				storeOutDTO.getProductId());
		Storeout storeout = new Storeout();
		storeout.setQuantityRequired(storeOutDTO.getQuantityRequired());
		storeout.setDescription(storeOutDTO.getDescription());
		storeout.setProduct(productService.getEntityById(Product.class, productionplanning.getProduct().getId()));
		storeout.setProductionplanning(productionplanning);
		storeout.setStatus(statusService.getEntityById(Status.class,
				Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_NEW_PRODUCT_ORDER, null, null))));
		storeout.setIsactive(true);
		storeout.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		storeoutService.addEntity(storeout);
	}
}
