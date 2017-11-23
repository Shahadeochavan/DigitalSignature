package com.nextech.dscrm.dto;

import java.util.List;

public class MultipleRMOrderDTO {

	private List<RawmaterialOrderDTO> rawmaterialOrderDTOs;

	public List<RawmaterialOrderDTO> getRawmaterialOrderDTOs() {
		return rawmaterialOrderDTOs;
	}

	public void setRawmaterialOrderDTOs(
			List<RawmaterialOrderDTO> rawmaterialOrderDTOs) {
		this.rawmaterialOrderDTOs = rawmaterialOrderDTOs;
	}
	
}
