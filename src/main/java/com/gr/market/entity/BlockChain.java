package com.gr.market.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "block_chain")
public class BlockChain {
	@Id
	@Column(name = "bc_name", nullable = false)
	private String bcName;
	@Column(name = "bc_cn_name", nullable = false)
	private String bcCnName;
	@Column(name = "descr", nullable = true)
	private String descr;
	@Column(name = "order_sn", nullable = false)
	private String orderSn;
	// 交易方式：0usdt、1btc、2eth
	@Column(name = "deal_mode", nullable = false)
	private int dealMode;
	// 风险类型：0主区、1创新、3分叉
	@Column(name = "risk", nullable = false)
	private int risk;

	public String getBcName() {
		return bcName;
	}

	public void setBcName(String bcName) {
		this.bcName = bcName;
	}

	public String getBcCnName() {
		return bcCnName;
	}

	public void setBcCnName(String bcCnName) {
		this.bcCnName = bcCnName;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public int getDealMode() {
		return dealMode;
	}

	public void setDealMode(int dealMode) {
		this.dealMode = dealMode;
	}

	public int getRisk() {
		return risk;
	}

	public void setRisk(int risk) {
		this.risk = risk;
	}

}
