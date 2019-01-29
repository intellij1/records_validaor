package com.rabobank.validator;
import com.rabobank.model.CustomerRecord;
import com.rabobank.model.CustomerRecordList;
import com.rabobank.model.ErrorRecord;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static com.rabobank.util.RecordUtil.isRefIDNumeric;
import static com.rabobank.util.RecordUtil.isRefNumberUnique;

/**
 * This Class will act a XML Validator and used for validationg the XML Records.
 *
 * */
public class XMLValidator implements IValidator {

    String xmlFilePath;
    Set<String> refIDSet = new HashSet<>();

    private List<ErrorRecord> errorRecordList = new ArrayList<>();

    public XMLValidator(String xmlFilePath) {
        super();
        this.xmlFilePath = xmlFilePath;
    }
/* This method will will read file from XML using JAXB and can be moved to utility Class in future */

    private List<CustomerRecord> readXMLFile() throws JAXBException

    {
        //get file content in the form of string
        File xmlFile = new File(this.xmlFilePath);

        //initialize jaxb classes
        JAXBContext context = JAXBContext.newInstance(CustomerRecordList.class);
        Unmarshaller un = context.createUnmarshaller();
        //convert to desired object
        CustomerRecordList customerData = (CustomerRecordList) un.unmarshal(xmlFile);

        return customerData.getCustomerRecordList();

    }

/* This method will will read the records from XML file and iterate it for every Record validationT*/

    public void validate() {
        List<CustomerRecord> recs;
        try {
            recs = readXMLFile();
            recs.forEach(this::validateRec);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

/* This method will validate every record and log the invalid recordT*/

private void validateRec(CustomerRecord rec) {
        String refID = rec.getRecID();
        boolean validateionRuleOne = (refID.isEmpty() || !isRefIDNumeric(refID) || !isRefNumberUnique(refID, this.refIDSet));
        // Validation: End Balance
        BigDecimal totalSum = rec.getStartBalance().add(rec.getMutation());

        boolean validateionRuleTwo = !(totalSum.compareTo(rec.getEndBalance()) == 0);

        if (validateionRuleOne || validateionRuleTwo) {
            this.errorRecordList.add(new ErrorRecord(refID, rec.getDesc()));
        }
    }

    @Override
    public List<ErrorRecord> getErrorList() {
        return this.errorRecordList = errorRecordList;
    }


}

