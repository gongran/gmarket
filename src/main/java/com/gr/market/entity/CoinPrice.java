package com.gr.market.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "coin_price")
public class CoinPrice {
	@Id
	@Column(name = "cp_name", nullable = false)
	private String cpName;
	@Column(name = "cp_key", nullable = false)
	private String cpKey;
	@Column(name = "cp_type", nullable = false)
	private String cpType;
	@Column(name = "cp_value", nullable = false)
	private String cpValue;
	@Column(name = "cp_open", nullable = true)
	private String cpOpen;
	@Column(name = "cp_close", nullable = true)
	private String cpClose;
	@Column(name = "cp_height", nullable = true)
	private String cpHeight;
	@Column(name = "cp_low", nullable = true)
	private String cpLow;
	@Column(name = "last_ud", nullable = true)
	private Date lastUd;

	public String getCpName() {
		return cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public String getCpKey() {
		return cpKey;
	}

	public void setCpKey(String cpKey) {
		this.cpKey = cpKey;
	}

	public String getCpType() {
		return cpType;
	}

	public void setCpType(String cpType) {
		this.cpType = cpType;
	}

	public String getCpValue() {
		return cpValue;
	}

	public void setCpValue(String cpValue) {
		this.cpValue = cpValue;
	}

	public String getCpOpen() {
		return cpOpen;
	}

	public void setCpOpen(String cpOpen) {
		this.cpOpen = cpOpen;
	}

	public String getCpClose() {
		return cpClose;
	}

	public void setCpClose(String cpClose) {
		this.cpClose = cpClose;
	}

	public String getCpHeight() {
		return cpHeight;
	}

	public void setCpHeight(String cpHeight) {
		this.cpHeight = cpHeight;
	}

	public String getCpLow() {
		return cpLow;
	}

	public void setCpLow(String cpLow) {
		this.cpLow = cpLow;
	}

	public Date getLastUd() {
		return lastUd;
	}

	public void setLastUd(Date lastUd) {
		this.lastUd = lastUd;
	}

}
