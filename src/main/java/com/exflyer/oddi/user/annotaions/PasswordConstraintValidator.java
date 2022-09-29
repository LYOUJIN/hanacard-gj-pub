package com.exflyer.oddi.user.annotaions;

import com.google.common.base.Joiner;
import java.util.Arrays;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.passay.DigitCharacterRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.SpecialCharacterRule;
import org.passay.WhitespaceRule;

public class PasswordConstraintValidator implements ConstraintValidator<Password, String> {

  @Override
  public void initialize(Password arg0) {
  }

  @SneakyThrows
  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {
    // 숫자 특수문자 조합
    PasswordValidator validator1 = new PasswordValidator(Arrays.asList(
      new LengthRule(8, 20),
      new DigitCharacterRule(1),
      new SpecialCharacterRule(1),
      new WhitespaceRule()));

    // 문자 숫자 조합
    PasswordValidator validator2 = new PasswordValidator(Arrays.asList(
      new LengthRule(8, 20),
      new DigitCharacterRule(1),
      new WhitespaceRule()));

    // 문자 특수 문자 조합
    PasswordValidator validator3 = new PasswordValidator(Arrays.asList(
      new LengthRule(8, 20),
      new SpecialCharacterRule(1),
      new WhitespaceRule()));

    if (StringUtils.isBlank(password)) {
      return true;
    }
    RuleResult result1 = validator1.validate(new PasswordData(password));
    if (result1.isValid()) {
      return true;
    } else {
      setContext(context, validator1, result1);
    }

    RuleResult result2 = validator2.validate(new PasswordData(password));
    if (result2.isValid()) {
      return true;
    } else {
      setContext(context, validator2, result2);
    }

    RuleResult result3 = validator3.validate(new PasswordData(password));
    if (result3.isValid()) {
      return true;
    } else {
      setContext(context, validator3, result3);
    }
    return false;
  }

  private void setContext(ConstraintValidatorContext context, PasswordValidator validator, RuleResult result) {
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(
      Joiner.on(",").join(validator.getMessages(result)))
      .addConstraintViolation();
  }

}
