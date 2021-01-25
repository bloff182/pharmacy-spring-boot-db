package com.bloff.pharmacy.app.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
	
	private String firstPassword;
    private String secondPassword;
    private String message;

    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
    	firstPassword = constraintAnnotation.first();
    	secondPassword = constraintAnnotation.second();
	    message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
       
    	boolean valid = true;

    	// pobieramy wartosci z pola tekstowego dla password 1 i 2
        final Object firstObj = new BeanWrapperImpl(value).getPropertyValue(firstPassword);
        final Object secondObj = new BeanWrapperImpl(value).getPropertyValue(secondPassword);

        valid =  firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);

        // wyswietlenie komunikatu o bledzie - message ograniczen 
        if (!valid){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstPassword).addConstraintViolation();//.disableDefaultConstraintViolation();
        }

        return valid;
    }
	
}