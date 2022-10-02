package com.lakala.pos.bean;

import java.util.List;

public class EnterpriseInfoBean {


    private int code;
    private String message;
    private List<Data> data;

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

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }


    public class Data {

        private String success;
        private String requestId;
        private String method;
        private String code;
        private String message;
        private String addressAndPhone;
        private String bankAndAccount;
        private String bank;
        private String bankAccount;
        private String city;
        private String county;
        private String fixedPhone;
        private int frequency;
        private String location;
        private String mobilePhone;
        private String name;
        private String province;
        private String score;
        private String taxId;
        private String companyIndex;
        private String accLevel;
        private String taxAuthorityCode;
        private String taxAuthorityName;

        public void setSuccess(String success) {
            this.success = success;
        }

        public String getSuccess() {
            return success;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

        public String getRequestId() {
            return requestId;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getMethod() {
            return method;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setAddressAndPhone(String addressAndPhone) {
            this.addressAndPhone = addressAndPhone;
        }

        public String getAddressAndPhone() {
            return addressAndPhone;
        }

        public void setBankAndAccount(String bankAndAccount) {
            this.bankAndAccount = bankAndAccount;
        }

        public String getBankAndAccount() {
            return bankAndAccount;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getBank() {
            return bank;
        }

        public void setBankAccount(String bankAccount) {
            this.bankAccount = bankAccount;
        }

        public String getBankAccount() {
            return bankAccount;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCity() {
            return city;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getCounty() {
            return county;
        }

        public void setFixedPhone(String fixedPhone) {
            this.fixedPhone = fixedPhone;
        }

        public String getFixedPhone() {
            return fixedPhone;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

        public int getFrequency() {
            return frequency;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getLocation() {
            return location;
        }

        public void setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
        }

        public String getMobilePhone() {
            return mobilePhone;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getProvince() {
            return province;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getScore() {
            return score;
        }

        public void setTaxId(String taxId) {
            this.taxId = taxId;
        }

        public String getTaxId() {
            return taxId;
        }

        public void setCompanyIndex(String companyIndex) {
            this.companyIndex = companyIndex;
        }

        public String getCompanyIndex() {
            return companyIndex;
        }

        public void setAccLevel(String accLevel) {
            this.accLevel = accLevel;
        }

        public String getAccLevel() {
            return accLevel;
        }

        public void setTaxAuthorityCode(String taxAuthorityCode) {
            this.taxAuthorityCode = taxAuthorityCode;
        }

        public String getTaxAuthorityCode() {
            return taxAuthorityCode;
        }

        public void setTaxAuthorityName(String taxAuthorityName) {
            this.taxAuthorityName = taxAuthorityName;
        }

        public String getTaxAuthorityName() {
            return taxAuthorityName;
        }

    }

}
