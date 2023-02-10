package com.lakala.pos.bean;

import java.util.Date;
import java.util.List;

public class TranQueryBean {


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

        private List<Records> records;
        private int total;
        private int size;
        private int current;
        private List<String> orders;
        private boolean optimizeCountSql;
        private boolean searchCount;
        private String countId;
        private String maxLimit;
        private int pages;

        public void setRecords(List<Records> records) {
            this.records = records;
        }

        public List<Records> getRecords() {
            return records;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTotal() {
            return total;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
        }

        public void setCurrent(int current) {
            this.current = current;
        }

        public int getCurrent() {
            return current;
        }

        public void setOrders(List<String> orders) {
            this.orders = orders;
        }

        public List<String> getOrders() {
            return orders;
        }

        public void setOptimizeCountSql(boolean optimizeCountSql) {
            this.optimizeCountSql = optimizeCountSql;
        }

        public boolean getOptimizeCountSql() {
            return optimizeCountSql;
        }

        public void setSearchCount(boolean searchCount) {
            this.searchCount = searchCount;
        }

        public boolean getSearchCount() {
            return searchCount;
        }

        public void setCountId(String countId) {
            this.countId = countId;
        }

        public String getCountId() {
            return countId;
        }

        public void setMaxLimit(String maxLimit) {
            this.maxLimit = maxLimit;
        }

        public String getMaxLimit() {
            return maxLimit;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getPages() {
            return pages;
        }

    }


    public class Records {

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
        private Date createTime;
        private Date updateTime;
        private String details;
        private int amount;
        private int pageNum;
        private int pageSize;

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

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setUpdateTime(Date updateTime) {
            this.updateTime = updateTime;
        }

        public Date getUpdateTime() {
            return updateTime;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getDetails() {
            return details;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getAmount() {
            return amount;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageSize() {
            return pageSize;
        }

    }
}
