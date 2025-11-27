package com.bank.manager.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DifferentAccountsValidator.class)
@Documented
public @interface DifferentAccounts {
    String message() default "Source and destination accounts must be different";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    String first();
    String second();
    
    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        DifferentAccounts[] value();
    }
}
