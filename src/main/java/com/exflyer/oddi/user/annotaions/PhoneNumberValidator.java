package com.exflyer.oddi.user.annotaions;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

  @Override
  public void initialize(PhoneNumber phoneNumber) {

  }

  @Override
  public boolean isValid(String field, ConstraintValidatorContext context) {
    return field != null && field.matches("[0-9]+")
      && (field.length() > 8) && (field.length() < 14);
  }
}
