package com.nextech.erp.status;

import java.util.List;

import com.nextech.erp.dto.DispatchProductDTO;




public class Response {
	private int code;
	private String message;
	private Object data;
	private List<DispatchProductDTO> dispatchProductDTOs;
	public Response() {

	}
  public Response(List<DispatchProductDTO> dispatchProductDTOs){
	  this.dispatchProductDTOs=dispatchProductDTOs;
  }
	
	public Response(int code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
	public Response(int code, Object data) {
		this.code = code;
		this.data = data;
	}
	public Response(int code, String message) {
		this.code = code;
		this.message = message;
	}
	public Response(Object data){
		this.data=data;
	}


	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public List<DispatchProductDTO> getDispatchProductDTOs() {
		return dispatchProductDTOs;
	}

	public void setDispatchProductDTOs(List<DispatchProductDTO> dispatchProductDTOs) {
		this.dispatchProductDTOs = dispatchProductDTOs;
	}

	

}
