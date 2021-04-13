package ru.tehsystem.demo.model.validations;


import ru.tehsystem.demo.model.UserEditBindingModel;
import ru.tehsystem.demo.model.UserRegisterBindingModel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsPasswordMatchingValidator implements ConstraintValidator<IsPasswordMatching, Object> {
    @Override
    public void initialize(IsPasswordMatching isPasswordMatching) {

    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        if (object instanceof UserRegisterBindingModel) {
            UserRegisterBindingModel userModel = (UserRegisterBindingModel) object;
            try {
                return userModel.getPassword().equals(userModel.getConfirmPassword());
            }catch (NullPointerException npe){
                return false;
            }

        }
        if (object instanceof UserEditBindingModel) {
            UserEditBindingModel userModel = (UserEditBindingModel) object;
            try {
                if (userModel.isPassordof())
                    return true;
                return userModel.getPassword().equals(userModel.getConfirmPassword());
            }catch (NullPointerException npe){
                return false;
            }

        }
        return false;
    }
}
