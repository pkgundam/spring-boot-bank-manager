package com.bank.manager.security.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public @interface IsUser {
}