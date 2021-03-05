package ru.tehsystem.demo.model.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = IsEmailRegisteredValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsEmailRegistered {
    String message() default "Электронная почта уже зарегистрирована";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
