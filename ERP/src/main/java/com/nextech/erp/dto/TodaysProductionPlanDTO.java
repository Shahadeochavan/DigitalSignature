package com.nextech.erp.dto;

import java.sql.Date;
import java.util.List;

public class TodaysProductionPlanDTO {
	private Date createDate;
	private List<DailyProductionPlanDTO> dailyProductionPlanDTOs;
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public List<DailyProductionPlanDTO> getDailyProductionPlanDTOs() {
		return dailyProductionPlanDTOs;
	}
	public void setDailyProductionPlanDTOs(
			List<DailyProductionPlanDTO> dailyProductionPlanDTOs) {
		this.dailyProductionPlanDTOs = dailyProductionPlanDTOs;
	}
	
	
}
