package com.exflyer.oddi.user.api.payment;

import com.exflyer.oddi.user.annotaions.LoginNeedApi;
import com.exflyer.oddi.user.annotaions.OddiEncrypt;
import com.exflyer.oddi.user.api.payment.dto.InicisMobileReqResult;
import com.exflyer.oddi.user.api.payment.dto.InicisReqResult;
import com.exflyer.oddi.user.api.payment.service.InicisService;
import com.exflyer.oddi.user.api.user.auth.dto.MemberAuth;
import io.swagger.annotations.ApiOperation;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 이니시스 결제 API
 */
@Slf4j
@Controller
public class InicisApi {

  @Resource
  private InicisService inicisService;

  public static final String IS_MOBILE = "MOBILE";

  private final String PC_VIEW_NAME = "payment/auth";
  private final String MOBILE_VIEW_NAME = "payment/mobile_auth";

  @InitBinder
  public void InitBinder(WebDataBinder dataBinder) {
    StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
    dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
  }

  /**
   * 이니시스 결제 페이지 로드용
   *
   * @param paymentSeq 결제정보 시퀀스
   * @param couponMappingSeq 쿠폰코드
   * @param modelMap   view 데이타 연동 데이타
   * @param request    요청정보
   * @return String
   * @throws NoSuchAlgorithmException
   */
  @GetMapping(path = "/payment/inicis/auth/{paymentSeq}/{couponMappingSeq}")
  public ModelAndView goAuth(@PathVariable Long paymentSeq, @PathVariable String couponMappingSeq,
                       ModelMap modelMap, HttpServletRequest request) throws Exception {

    ModelAndView mv = new ModelAndView();

    log.info("========== goAuth");
    log.info("paymentSeq: {} ", paymentSeq);
    log.info("couponMappingSeq: {} ", couponMappingSeq);

    String userAgent = request.getHeader("User-Agent").toUpperCase();

    log.info("==========================userAgent==========================");
    log.info("userAgent :" + userAgent);
    log.info("==========================userAgent//========================");
    Boolean isMobile = false;

    if (userAgent.indexOf(IS_MOBILE) > -1) {
        isMobile = true;
    }
    log.info("isMobile ===> " + isMobile);

    if(isMobile) {
      modelMap.putAll(inicisService.reqAuthMobileCondition(paymentSeq, couponMappingSeq));
      mv.setViewName(MOBILE_VIEW_NAME);
    }else {
      modelMap.putAll(inicisService.reqAuthCondition(paymentSeq, couponMappingSeq));
      mv.setViewName(PC_VIEW_NAME);
    }

    return mv;
  }

  /**
   * 이니시스 결제 결과 처리용
   *
   * @param paymentSeq 결제정보 시퀀스
   * @param couponMappingSeq 쿠폰코드
   * @param request    요청정보
   * @return String
   * @throws Exception
   */
  @ApiOperation(value = "이니시스 결제 처리용 API", notes = "이니시스 결제 처리용 API 입니다. ")
  @PostMapping(path = "/payment/inicis/result/{paymentSeq}/{couponMappingSeq}")
  public String paymentResult(@PathVariable Long paymentSeq, @PathVariable Long couponMappingSeq,
                              ModelMap modelMap, HttpServletRequest request) throws Exception {
    log.info("========== paymentResult");

    Enumeration elems = request.getParameterNames();
    InicisReqResult inicisReqResult = new InicisReqResult();

    Field field;
    String name;

    while(elems.hasMoreElements())
    {
      name = (String) elems.nextElement();
      field = ReflectionUtils.findField(inicisReqResult.getClass(), name);
      if (null != field) {
        field.setAccessible(true);
        field.set(inicisReqResult, request.getParameter(name));
      }
    }
    modelMap.put("result", this.inicisService.paymentProcessing(inicisReqResult, paymentSeq, couponMappingSeq));

    return "payment/result";
  }

  /**
   * 이니시스 결제 중지 처리용
   *
   * @param paymentSeq 결제정보 시퀀스
   * @param couponMappingSeq 쿠폰시퀀스
   * @return String
   */
  @ApiOperation(value = "이니시스 결제 중지 API", notes = "이니시스 결제 중지 API 입니다. ")
  @GetMapping(path = "/payment/inicis/close/{paymentSeq}/{couponMappingSeq}")
  public String paymentClose(@PathVariable Long paymentSeq, @PathVariable Long couponMappingSeq, MemberAuth memberAuth) {
    log.info("========== paymentClose");
    log.info("paymentSeq: {},couponSeq: {} ", paymentSeq, couponMappingSeq);

    this.inicisService.paymentClose(paymentSeq, couponMappingSeq, memberAuth.getId());

    return "payment/close";
  }


  /**
   * 모바일 이니시스 결제 결과 처리용
   *
   * @param paymentSeq 결제정보 시퀀스
   * @param couponMappingSeq 쿠폰시퀀스
   * @param request    요청정보
   * @return String
   * @throws Exception
   */
  @ApiOperation(value = "모바일 이니시스 결제 처리용 API", notes = "이니시스 결제 처리용 API 입니다. ")
  @PostMapping(path = "/payment/inicis/mobile/result/{paymentSeq}/{couponMappingSeq}")
  public String paymentMobileResult(@PathVariable Long paymentSeq, @PathVariable Long couponMappingSeq,
      ModelMap modelMap, HttpServletRequest request) throws Exception {
    log.info("========== paymentMobileResult");

    request.setCharacterEncoding("euc-kr");
    Enumeration elems = request.getParameterNames();
    InicisMobileReqResult inicisMobileReqResult = new InicisMobileReqResult();

    Field field;
    String name;

    while(elems.hasMoreElements())
    {
      name = (String) elems.nextElement();
      field = ReflectionUtils.findField(inicisMobileReqResult.getClass(), name);

      if (null != field) {
        field.setAccessible(true);
        field.set(inicisMobileReqResult, request.getParameter(name));
      }
    }

    log.info("==========================MOBILE 이니시스 결제 처리용==========================");
    log.info("inicisMobileReqResult : " + inicisMobileReqResult);
    log.info("==========================MOBILE 이니시스 결제 처리용//========================");

    modelMap.put("result", this.inicisService.paymentMobileProcessing(inicisMobileReqResult, paymentSeq, couponMappingSeq));

    return "payment/mobile_result";
  }
}
