package com.nextech.erp.dao;

import java.util.List;

import com.nextech.erp.model.Rawmaterialorder;

public interface RawmaterialorderDao extends SuperDao<Rawmaterialorder> {
	public Rawmaterialorder getRawmaterialorderByIdName(long id, String rmname)
			throws Exception;

	public List<Rawmaterialorder> getRawmaterialorderByStatusId(long statusId,
			long statusId1, long statusId2) throws Exception;

	public List<Rawmaterialorder> getRawmaterialorderByQualityCheckStatusId(
			long statusId) throws Exception;

	public List<Rawmaterialorder> getRawmaterialorderByVendor(long vendorId)
			throws Exception;

	public List<Rawmaterialorder> getRawmaterialByName(String  name)throws Exception;
	
	public List<Rawmaterialorder> getRawmaterialorderByVendorId(long vendorId,long statusId1, long statusId2) throws Exception;

}
