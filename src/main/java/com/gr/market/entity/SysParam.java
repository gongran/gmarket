package com.gr.market.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "sys_param")
public class SysParam {
	@Id
	@Column(name = "param_name", nullable = false)
	private String paramName;
	@Column(name = "param_cn_name", nullable = false)
	private String paramCnName;
	private String descr;
	@Column(name = "param_value", nullable = false)
	private String paramValue;
	@Column(name = "order_sn", nullable = false)
	private int orderSn;

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamCnName() {
		return paramCnName;
	}

	public void setParamCnName(String paramCnName) {
		this.paramCnName = paramCnName;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public int getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(int orderSn) {
		this.orderSn = orderSn;
	}

}
