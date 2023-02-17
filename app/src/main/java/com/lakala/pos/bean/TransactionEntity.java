package com.lakala.pos.bean;


import java.io.Serializable;


public class TransactionEntity implements Serializable {

	private static final long serialVersionUID = 5390682994083594853L;

	private String merid;//商户号
	private String termid;//终端号
	private String batchno;//批次号
	private String systraceno;//凭证号
	private String authcode;//授权号
	private String orderid_scan;//扫码订单号

	public String getMerid() {
		return merid;
	}
	public void setMerid(String merid) {
		this.merid = merid;
	}
	public String getTermid() {
		return termid;
	}
	public void setTermid(String termid) {
		this.termid = termid;
	}
	public String getBatchno() {
		return batchno;
	}
	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	public String getSystraceno() {
		return systraceno;
	}
	public void setSystraceno(String systraceno) {
		this.systraceno = systraceno;
	}
	public String getAuthcode() {
		return authcode;
	}
	public void setAuthcode(String authcode) {
		this.authcode = authcode;
	}
	public String getOrderid_scan() {
		return orderid_scan;
	}
	public void setOrderid_scan(String orderid_scan) {
		this.orderid_scan = orderid_scan;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "========================\n\r 商户号=" + merid + "\n\r 终端号=" + termid
				+ "\n\r 批次号=" + batchno + "\n\r 凭证号=" + systraceno
				+ "\n\r 授权号=" + authcode + "\n\r 扫码订单号=" + orderid_scan;

	}
}
