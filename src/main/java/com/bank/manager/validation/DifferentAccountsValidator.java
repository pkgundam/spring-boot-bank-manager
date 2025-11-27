package com.bank.manager.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class DifferentAccountsValidator implements ConstraintValidator<DifferentAccounts, Object> {
    
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(DifferentAccounts constraint) {
        firstFieldName = constraint.first();
        secondFieldName = constraint.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        try {
            Object firstObj = getFieldValue(value, firstFieldName);
            Object secondObj = getFieldValue(value, secondFieldName);
            
            // If either field is null, let @NotNull handle it
            if (firstObj == null || secondObj == null) {
                return true;
            }
            
            return !firstObj.equals(secondObj);
        } catch (Exception e) {
            return false;
        }
    }

    private Object getFieldValue(Object object, String fieldName) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }
}
