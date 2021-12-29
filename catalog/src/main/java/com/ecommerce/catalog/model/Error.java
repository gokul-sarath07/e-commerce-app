package com.ecommerce.catalog.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Error {

    private String field;
    private String defaultMessage;

    public List<Error> getErrorDetails(List<ObjectError> errors) {
        List<Error> errorList = new ArrayList<>();
        errors.forEach(
                e -> {
                    Error error = new Error();
                    String code = e.getCodes()[0];
                    String fieldName = code.substring(code.lastIndexOf(".") + 1);
                    error.setField(fieldName);
                    error.setDefaultMessage(e.getDefaultMessage());
                    errorList.add(error);
                }
        );
        return errorList;
    }
}
