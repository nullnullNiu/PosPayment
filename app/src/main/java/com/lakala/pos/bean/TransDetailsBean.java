package com.lakala.pos.bean;

import java.util.List;


public class TransDetailsBean {


    private int code;
    private String message;
    private Data data;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }


    public class Data {

        private String orderNo;
        private String orderSort;
        private String payee;
        private String drawer;
        private String checker;
        private int invoiceMark;
        private int status;
        private String batchNo;
        private String voucherNo;
        private String tradeType;
        private String scanUrl;
        private double amount;
        private double payAmount;
        private String tradeTime;
        private String createTime;
        private String updateTime;

        private List<Details> details;

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderSort(String orderSort) {
            this.orderSort = orderSort;
        }

        public String getOrderSort() {
            return orderSort;
        }

        public void setPayee(String payee) {
            this.payee = payee;
        }

        public String getPayee() {
            return payee;
        }

        public void setDrawer(String drawer) {
            this.drawer = drawer;
        }

        public String getDrawer() {
            return drawer;
        }

        public void setChecker(String checker) {
            this.checker = checker;
        }

        public String getChecker() {
            return checker;
        }

        public void setInvoiceMark(int invoiceMark) {
            this.invoiceMark = invoiceMark;
        }

        public int getInvoiceMark() {
            return invoiceMark;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }

        public void setBatchNo(String batchNo) {
            this.batchNo = batchNo;
        }

        public String getBatchNo() {
            return batchNo;
        }

        public void setVoucherNo(String voucherNo) {
            this.voucherNo = voucherNo;
        }

        public String getVoucherNo() {
            return voucherNo;
        }

        public void setTradeType(String tradeType) {
            this.tradeType = tradeType;
        }

        public String getTradeType() {
            return tradeType;
        }

        public void setScanUrl(String scanUrl) {
            this.scanUrl = scanUrl;
        }

        public String getScanUrl() {
            return scanUrl;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public double getAmount() {
            return amount;
        }

        public void setPayAmount(double payAmount) {
            this.payAmount = payAmount;
        }

        public double getPayAmount() {
            return payAmount;
        }

        public void setTradeTime(String tradeTime) {
            this.tradeTime = tradeTime;
        }

        public String getTradeTime() {
            return tradeTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setDetails(List<Details> details) {
            this.details = details;
        }

        public List<Details> getDetails() {
            return details;
        }


        public class Details {

            private String orderNo;
            private String goodsName;
            private String goodsCode;
            private String goodsPersonalCode;
            private String goodsSpecification;
            private String goodsUnit;
            private int goodsQuantity;
            private int goodsPrice;
            private int goodsTotalPrice;
            private double goodsTotalTax;
            private int goodsTaxRate;
            private String vatSpecialManagement;
            private String freeTaxMark;
            private String preferentialMarkFlag;
            private String createTime;
            private String updateTime;

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public String getOrderNo() {
                return orderNo;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsCode(String goodsCode) {
                this.goodsCode = goodsCode;
            }

            public String getGoodsCode() {
                return goodsCode;
            }

            public void setGoodsPersonalCode(String goodsPersonalCode) {
                this.goodsPersonalCode = goodsPersonalCode;
            }

            public String getGoodsPersonalCode() {
                return goodsPersonalCode;
            }

            public void setGoodsSpecification(String goodsSpecification) {
                this.goodsSpecification = goodsSpecification;
            }

            public String getGoodsSpecification() {
                return goodsSpecification;
            }

            public void setGoodsUnit(String goodsUnit) {
                this.goodsUnit = goodsUnit;
            }

            public String getGoodsUnit() {
                return goodsUnit;
            }

            public void setGoodsQuantity(int goodsQuantity) {
                this.goodsQuantity = goodsQuantity;
            }

            public int getGoodsQuantity() {
                return goodsQuantity;
            }

            public void setGoodsPrice(int goodsPrice) {
                this.goodsPrice = goodsPrice;
            }

            public int getGoodsPrice() {
                return goodsPrice;
            }

            public void setGoodsTotalPrice(int goodsTotalPrice) {
                this.goodsTotalPrice = goodsTotalPrice;
            }

            public int getGoodsTotalPrice() {
                return goodsTotalPrice;
            }

            public void setGoodsTotalTax(double goodsTotalTax) {
                this.goodsTotalTax = goodsTotalTax;
            }

            public double getGoodsTotalTax() {
                return goodsTotalTax;
            }

            public void setGoodsTaxRate(int goodsTaxRate) {
                this.goodsTaxRate = goodsTaxRate;
            }

            public int getGoodsTaxRate() {
                return goodsTaxRate;
            }

            public void setVatSpecialManagement(String vatSpecialManagement) {
                this.vatSpecialManagement = vatSpecialManagement;
            }

            public String getVatSpecialManagement() {
                return vatSpecialManagement;
            }

            public void setFreeTaxMark(String freeTaxMark) {
                this.freeTaxMark = freeTaxMark;
            }

            public String getFreeTaxMark() {
                return freeTaxMark;
            }

            public void setPreferentialMarkFlag(String preferentialMarkFlag) {
                this.preferentialMarkFlag = preferentialMarkFlag;
            }

            public String getPreferentialMarkFlag() {
                return preferentialMarkFlag;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getCreateTime() {

                return createTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getUpdateTime() {

                return updateTime;
            }
        }
    }

}
