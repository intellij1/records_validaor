package com.rabobank.validator;

import com.rabobank.model.ErrorRecord;

import java.util.List;
/*
* Common interface and contract for Validator
*
* **/

public interface IValidator {

     void validate();
     List<ErrorRecord> getErrorList() ;

}
