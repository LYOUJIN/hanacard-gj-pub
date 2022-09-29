package com.exflyer.oddi.user.aop;


import com.exflyer.oddi.user.annotaions.DecryptField;
import com.exflyer.oddi.user.annotaions.EncryptField;
import com.exflyer.oddi.user.share.AesEncryptor;
import com.exflyer.oddi.user.share.dto.ApiResponseDto;
import com.exflyer.oddi.user.share.dto.PagingResult;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class EncryptAop {
  @Autowired
  private AesEncryptor aesEncryptor;

  @Around("@annotation(com.exflyer.oddi.user.annotaions.OddiEncrypt)")
  public Object encrypt(ProceedingJoinPoint joinPoint) throws Throwable {
    encryptFiled(joinPoint);

    Object result = joinPoint.proceed();
    // 메소드 실행 후
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    if (signature.getReturnType() == ApiResponseDto.class) {
      ApiResponseDto apiResponseDto = (ApiResponseDto) result;
      Object responseData = apiResponseDto.getData();
      if (responseData == null) {
        return result;
      }
      if (PagingResult.class == responseData.getClass()) {
        PagingResult pagingResult = (PagingResult) responseData;
        for (Object pagingData : pagingResult.getData()) {
          decryptFields(pagingData, pagingData.getClass());
        }
      } else {
        Object singleData = apiResponseDto.getData();
        decryptFields(singleData, singleData.getClass());
      }
    } else {
      decryptFields(result, result.getClass());
    }

    return result;
  }

  private void encryptFiled(ProceedingJoinPoint joinPoint) throws IllegalAccessException {
    Object[] args = joinPoint.getArgs();
    for (Object arg : args) {
      for (Field field : arg.getClass().getDeclaredFields()) {
        field.setAccessible(true);
        for (Annotation declaredAnnotation : field.getDeclaredAnnotations()) {
          if (declaredAnnotation.annotationType() == EncryptField.class) {
            String encryptTargetValue = (String) field.get(arg);
            String encryptedValue = aesEncryptor.encrypt(encryptTargetValue);
            field.set(arg, encryptedValue);
          }
        }
      }
    }
  }

  private void decryptFields(Object arg, Class<?> argClass)
    throws IllegalAccessException {
    for (Field field : argClass.getDeclaredFields()) {
      field.setAccessible(true);
      for (Annotation declaredAnnotation : field.getDeclaredAnnotations()) {
        if (declaredAnnotation.annotationType() == DecryptField.class) {
          String decryptTargetValue = (String) field.get(arg);
          String decryptedValue = aesEncryptor.decrypt(decryptTargetValue);
          field.set(arg, decryptedValue);
        }
      }
    }
  }
}
