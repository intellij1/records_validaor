package com.rabobank.model;


import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@XmlRootElement(name = "record")
@XmlAccessorType (XmlAccessType.FIELD)
public class CustomerRecord {
    @XmlAttribute(name = "reference")
    private String recID;
    @XmlElement(name= "description")
    private String  desc;
    @XmlElement
    private String accountNumber;
    @XmlElement
    private BigDecimal startBalance;
    @XmlElement
    private BigDecimal mutation;
    @XmlElement
    private BigDecimal endBalance;

    public String getRecID() {
        return recID;
    }

    public void setRecID(String recID) {
        this.recID = recID;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getStartBalance() {
        return startBalance;
    }

    public void setStartBalance(BigDecimal startBalance) {
        this.startBalance = startBalance;
    }

    public BigDecimal getMutation() {
        return mutation;
    }

    public void setMutation(BigDecimal mutation) {
        this.mutation = mutation;
    }

    public BigDecimal getEndBalance() {
        return endBalance;
    }

    public void setEndBalance(BigDecimal endBalance) {
        this.endBalance = endBalance;
    }

    @Override
    public String toString() {
        return "CustomerRecord{" + "recID='" + recID + '\'' +
                ", desc='" + desc + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", startBalance=" + startBalance +
                ", mutation=" + mutation +
                ", endBalance=" + endBalance +
                '}';
    }
}
