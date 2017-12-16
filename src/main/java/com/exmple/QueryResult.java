package com.exmple;

import java.math.BigDecimal;
import java.util.Date;

import com.exmple.utils.SimpleResult;

public class QueryResult extends SimpleResult {
	// Apple ID
	private String appleid;
	// 充值前余额
    private BigDecimal original;
    // 充值后余额
    private BigDecimal balance;
    // 礼品卡编号
	private String code;
	// 礼品卡面额
	private BigDecimal price;
	// 兑现时间
	private Date redeemTime;

	public String getAppleid() {
		return appleid;
	}

	public void setAppleid(String appleid) {
		this.appleid = appleid;
	}

	public BigDecimal getOriginal() {
		return original;
	}

	public void setOriginal(BigDecimal original) {
		this.original = original;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Date getRedeemTime() {
		return redeemTime;
	}

	public void setRedeemTime(Date redeemTime) {
		this.redeemTime = redeemTime;
	}
	
}
