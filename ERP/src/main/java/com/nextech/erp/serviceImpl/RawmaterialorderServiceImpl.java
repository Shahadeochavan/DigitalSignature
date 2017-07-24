package com.nextech.erp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dao.RawmaterialorderDao;
import com.nextech.erp.dao.StatusDao;
import com.nextech.erp.dto.RawmaterialOrderDTO;
import com.nextech.erp.factory.RMOrderRequestResponseFactory;
import com.nextech.erp.model.Rawmaterialorder;
import com.nextech.erp.model.Status;
import com.nextech.erp.service.RawmaterialorderService;
@Service
public class RawmaterialorderServiceImpl extends CRUDServiceImpl<Rawmaterialorder> implements RawmaterialorderService{

	@Autowired
	RawmaterialorderDao rawmaterialorderDao;
	
	@Autowired 
	StatusDao StatusDao;
	
	@Autowired
	private MessageSource messageSource;

	@Override
	public Rawmaterialorder getRawmaterialorderByIdName(long id,String rmname) throws Exception {
		return rawmaterialorderDao.getRawmaterialorderByIdName(id,rmname);
	}

	@Override
	public List<Rawmaterialorder> getRawmaterialorderByStatusId(long statusId,long statusId1,long statusId2)
			throws Exception {
		// TODO Auto-generated method stub
		return rawmaterialorderDao.getRawmaterialorderByStatusId(statusId,statusId1,statusId2);
	}

	@Override
	public List<Rawmaterialorder> getRawmaterialorderByQualityCheckStatusId(
			long statusId) throws Exception {
		// TODO Auto-generated method stub
		return rawmaterialorderDao.getRawmaterialorderByQualityCheckStatusId(statusId);
	}

	@Override
	public List<Rawmaterialorder> getRawmaterialorderByVendor(long vendorId)
			throws Exception {
		// TODO Auto-generated method stub
		return rawmaterialorderDao.getRawmaterialorderByVendor(vendorId);
	}

	@Override
	public List<Rawmaterialorder> getRawmaterialByName(String name)
			throws Exception {
		// TODO Auto-generated method stub
		return rawmaterialorderDao.getRawmaterialByName(name);
	}

	@Override
	public List<Rawmaterialorder> getRawmaterialorderByVendorId(long vendorId,
			long statusId1, long statusId2) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RawmaterialOrderDTO saveRMOrder(RawmaterialOrderDTO rawmaterialOrderDTO,HttpServletRequest request, HttpServletResponse response)throws Exception {
		// TODO Auto-generated method stub
		Rawmaterialorder  rawmaterialorder = RMOrderRequestResponseFactory.setRMOrder(rawmaterialOrderDTO);
		rawmaterialorder.setStatus(StatusDao.getById(Status.class,Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_NEW_RM_ORDER, null, null))));
		long id=rawmaterialorderDao.add(rawmaterialorder);
		System.out.println("id is"+id);
		rawmaterialOrderDTO.setId(id);
		rawmaterialOrderDTO.setStatusId(rawmaterialorder.getStatus());
		return rawmaterialOrderDTO;
	}

	@Override
	public void updateRMName(String name,RawmaterialOrderDTO rawmaterialOrderDTO) throws Exception {
		// TODO Auto-generated method stub
		Rawmaterialorder  rawmaterialorder = new Rawmaterialorder();
		rawmaterialorder.setName(name);
		rawmaterialorder.setId(rawmaterialOrderDTO.getId());
		rawmaterialorderDao.update(rawmaterialorder);
}

	@Override
	public List<RawmaterialOrderDTO> getRMOrderList() throws Exception {
		// TODO Auto-generated method stub
		List<RawmaterialOrderDTO> rawmaterialOrderDTOs = new ArrayList<RawmaterialOrderDTO>();
		List<Rawmaterialorder> rawmaterialorders = rawmaterialorderDao.getList(Rawmaterialorder.class);
		for (Rawmaterialorder rawmaterialorder : rawmaterialorders) {
			RawmaterialOrderDTO  rawmaterialOrderDTO = RMOrderRequestResponseFactory.setRMOrderDTO(rawmaterialorder);
			rawmaterialOrderDTOs.add(rawmaterialOrderDTO);
		}
		return rawmaterialOrderDTOs;
	}

	@Override
	public RawmaterialOrderDTO getRMOrderById(long id) throws Exception {
		// TODO Auto-generated method stub
		Rawmaterialorder rawmaterialorder = rawmaterialorderDao.getById(Rawmaterialorder.class, id);
		RawmaterialOrderDTO  rawmaterialOrderDTO = RMOrderRequestResponseFactory.setRMOrderDTO(rawmaterialorder);
		return rawmaterialOrderDTO;
	}

	@Override
	public RawmaterialOrderDTO deleteRMOrder(long id) throws Exception {
		// TODO Auto-generated method stub
		Rawmaterialorder rawmaterialorder = rawmaterialorderDao.getById(Rawmaterialorder.class, id);
		rawmaterialorder.setIsactive(false);
		rawmaterialorderDao.update(rawmaterialorder);
		return null;
	}
}

