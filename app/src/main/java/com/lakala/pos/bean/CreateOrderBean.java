package com.lakala.pos.bean;

public class CreateOrderBean {

        private String termId;
        private String busiType;
        public void setTermId(String termId) {
            this.termId = termId;
        }
        public String getTermId() {
            return termId;
        }

        public void setBusiType(String busiType) {
            this.busiType = busiType;
        }
        public String getBusiType() {
            return busiType;
        }

}
