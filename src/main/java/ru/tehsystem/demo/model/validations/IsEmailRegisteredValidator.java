package ru.tehsystem.demo.model.validations;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tehsystem.demo.services.impl.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;
import java.util.Set;

public class IsEmailRegisteredValidator implements ConstraintValidator<IsEmailRegistered,String> , Validator {
    @Autowired
    private UserService userService;

    @Override
    public void initialize(IsEmailRegistered isEmailRegister) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !this.userService.isEmailTaken(s);
    }

    @Override public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
        return null;
    }

    @Override
    public <T> Set<ConstraintViolation<T>> validateProperty(T object, String propertyName, Class<?>... groups) {
        return null;
    }

    @Override public <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType, String propertyName, Object value,
                                                                   Class<?>... groups) {
        return null;
    }

    @Override public BeanDescriptor getConstraintsForClass(Class<?> clazz) {
        return null;
    }

    @Override public <T> T unwrap(Class<T> type) {
        return null;
    }

    @Override public ExecutableValidator forExecutables() {
        return null;
    }
}
