package com.rabobank.model;


public class ErrorRecord {
    private String  refid  ;
    private String  desc  ;

    public ErrorRecord( String refid , String desc) {

        super();
        this.refid  = refid;
        this.desc = desc ;
    }

    public String getRefid() {
        return refid;
    }

    public void setRefid(String refid) {
        this.refid = refid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "ErrorRecord{" +
                "refid='" + refid + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
