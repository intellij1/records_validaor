package com.rabobank.model;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "records")
@XmlAccessorType (XmlAccessType.FIELD)

public class CustomerRecordList {

    public List<CustomerRecord> getCustomerRecordList() {
        return customerRecordList;
    }

    public void setCustomerRecordList(List<CustomerRecord> customerRecordList) {
        this.customerRecordList = customerRecordList;
    }

    @XmlElement(name = "record")
    private List<CustomerRecord> customerRecordList = null;


}
