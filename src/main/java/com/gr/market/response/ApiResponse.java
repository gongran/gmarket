package com.gr.market.response;

import com.gr.market.ApiException;

public class ApiResponse<T> {

  public String status;
  public String errCode;
  public String errMsg;
  public T data;
  public T tick;

  public T checkAndReturn() {
    if ("ok".equals(status)) {
      return data;
    }
    throw new ApiException(errCode, errMsg);
  }
  public T checkAndReturnTick() {
	    if ("ok".equals(status)) {
	      return tick;
	    }
	    throw new ApiException(errCode, errMsg);
	  }
}
