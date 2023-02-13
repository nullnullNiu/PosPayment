package com.lakala.pos.bean;

import java.util.List;

public class SummaryBean {

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
        private List<REFUND> REFUND;
        private List<SUCCESS> SUCCESS;

        public void setREFUND(List<REFUND> REFUND) {
            this.REFUND = REFUND;
        }

        public List<REFUND> getREFUND() {
            return REFUND;
        }

        public void setSUCCESS(List<SUCCESS> SUCCESS) {
            this.SUCCESS = SUCCESS;
        }

        public List<SUCCESS> getSUCCESS() {
            return SUCCESS;
        }


        public class REFUND {

            private String status;
            private String num;
            private String amount;
            private String type;

            public void setStatus(String status) {
                this.status = status;
            }

            public String getStatus() {
                return status;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getNum() {
                return num;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getAmount() {
                return amount;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getType() {
                return type;
            }

        }


        public class SUCCESS {

            private String status;
            private String num;
            private String amount;
            private String type;

            public void setStatus(String status) {
                this.status = status;
            }

            public String getStatus() {
                return status;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getNum() {
                return num;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getAmount() {
                return amount;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getType() {
                return type;
            }

        }

    }
}
