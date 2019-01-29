package com.rabobank.validator;

import com.rabobank.model.ErrorRecord;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;
import java.nio.file.*;


import static com.rabobank.util.RecordUtil.*;

/**
 * This Class will act a CSV Validator and used for validationg the CSV Records.
 *
 * */
public class CSVRecValidator implements IValidator {

    String csvFilePath;
    Set<String> refIDSet = new HashSet<>();


    private List<ErrorRecord> errorRecordList = new ArrayList<>();

    public CSVRecValidator(String csvFilePath) {
        super();
        this.csvFilePath = csvFilePath;
    }

    public void validate() {

        try (Stream<String> stream = Files.lines(Paths.get(this.csvFilePath))) {
            stream.skip(1).forEach(this::validateRecord);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method will validate record
     * **/

    private void validateRecord(String recLine) {

        //Remove spaces after records
        String[] rec = Arrays.stream(recLine.split(",")).map(String::trim).toArray(String[]::new);

        String refID = null !=rec[0]?rec[0]: "";

        boolean validationRuleOne = (refID.isEmpty() || !isRefIDNumeric(refID) || !isRefNumberUnique(refID,this.refIDSet));
        // Validation 1 Ref cant be null , numeric , unique
        // Validation: End Balance

        BigDecimal totalSum = strToBigDecimal(rec[4]).add( strToBigDecimal(rec[3]) );
        boolean validataionRuleTwo = !(totalSum.compareTo(strToBigDecimal(rec[5]))== 0) ;

        if (validationRuleOne || validataionRuleTwo) {
            this.errorRecordList.add(new ErrorRecord(rec[0], rec[2]));
        }
    }

    @Override
    public List<ErrorRecord> getErrorList() {
        return this.errorRecordList;
    }




}