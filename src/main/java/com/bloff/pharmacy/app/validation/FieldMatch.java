package com.bloff.pharmacy.app.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.bloff.pharmacy.app.validation.FieldMatchValidator;

@Constraint(validatedBy = FieldMatchValidator.class) // definiujemy tu klase pomocnicza walidacji
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE }) // okreslamy gdzie mozna uzyc adnotacji
@Retention(RetentionPolicy.RUNTIME) // okreslamy jak dlugo zachowac adnotacje
@Documented
public @interface FieldMatch {
	
	// definiuje wiadomosc o bledzie
	String message() default "passwords do not match";
	
	// definiujemy nasze domyslne grupy jako podstawowy zestaw grup. Poniewaz ich nie wykorzystujemy wiec podajemy pusta kolekcje 
	Class<?>[] groups() default {};
	
	// ustawiamy domyslna wartosc gdyz nie uzywamy payload
	// payload - podaje niestandardowe szczegóły dotyczące niepowodzenia walidacji
	Class<? extends Payload>[] payload() default {};
	
	// dodajemy dwa pola dotyczace pol tekstowych dla password- definiowane w klasie CrmUser
	String first();
    String second();
    
}