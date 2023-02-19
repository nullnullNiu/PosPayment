package com.lakala.pos.bean;

public class BindDeviceInfoBean {

    private String deviceCode;
    private String taxNo;
    private String industry;
    private String address;
    private String goodsName;
    private String position;
    private String drawer;
    private String checker;
    private String sellerName;
    private String sellerNo;
    private String bossName;
    private String bossPhone;


    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDrawer() {
        return drawer;
    }

    public void setDrawer(String drawer) {
        this.drawer = drawer;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerNo() {
        return sellerNo;
    }

    public void setSellerNo(String sellerNo) {
        this.sellerNo = sellerNo;
    }

    public String getBossName() {
        return bossName;
    }

    public void setBossName(String bossName) {
        this.bossName = bossName;
    }

    public String getBossPhone() {
        return bossPhone;
    }

    public void setBossPhone(String bossPhone) {
        this.bossPhone = bossPhone;
    }

    @Override
    public String toString() {
        return "BindDeviceInfoBean{" +
                "deviceCode='" + deviceCode + '\'' +
                ", taxNo='" + taxNo + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", address='" + address + '\'' +
                ", position='" + position + '\'' +
                ", drawer='" + drawer + '\'' +
                ", checker='" + checker + '\'' +
                ", sellerName='" + sellerName + '\'' +
                ", sellerNo='" + sellerNo + '\'' +
                ", bossName='" + bossName + '\'' +
                ", bossPhone='" + bossPhone + '\'' +
                '}';
    }
}
