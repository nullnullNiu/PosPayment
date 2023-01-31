package com.lakala.pos.erp.demo.constants;


/**
 * 交易状态枚举
 *
 * @author ouqf
 * @date 2020-07-14
 */

public enum TransStatusEnums {

	/** 订单状态枚举 */

	WAIT_PAY(1, "等待支付"),
	SUCCESSED(2, "支付成功"),
	FAILED(3, "支付失败"),
	UNKNON(4, "交易结果未知"),
	CANCEL(5, "交易取消");

	private int code;
	private String desc;

	TransStatusEnums(int i, String 支付成功) {
	}
}
