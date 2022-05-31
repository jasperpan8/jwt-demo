package com.example.jwtdemo.validator;

import com.example.jwtdemo.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        User user = (User) obj;
        if(user.getPassword().length() < 5){
            errors.rejectValue("password","Length","Password must be at least 5 characters");
        }
        if(!user.getPassword().equals(user.getConfirmPassword())){
            errors.rejectValue("password","Match","Password must match");
        }
    }
}
