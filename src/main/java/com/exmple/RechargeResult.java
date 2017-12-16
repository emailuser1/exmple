package com.exmple;

import com.exmple.utils.SimpleResult;

public class RechargeResult extends SimpleResult {
	// 调用充值接口返回ID
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
