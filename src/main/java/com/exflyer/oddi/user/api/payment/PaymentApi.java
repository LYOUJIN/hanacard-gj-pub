package com.exflyer.oddi.user.api.payment;

import com.exflyer.oddi.user.annotaions.LoginNeedApi;
import com.exflyer.oddi.user.api.payment.dto.InicisCancelRes;
import com.exflyer.oddi.user.api.payment.dto.PaymentDetailResult;
import com.exflyer.oddi.user.api.payment.dto.PaymentReq;
import com.exflyer.oddi.user.api.payment.service.PaymentService;
import com.exflyer.oddi.user.api.user.auth.dto.MemberAuth;
import com.exflyer.oddi.user.enums.AdvProcessCodes;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.models.Payment;
import com.exflyer.oddi.user.repository.PaymentRepository;
import com.exflyer.oddi.user.share.LocalDateUtils;
import com.exflyer.oddi.user.share.dto.ApiResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "결제진행", protocols = "http")
@Slf4j
@RestController
public class PaymentApi {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    public static final String IS_MOBILE = "MOBILE";

    @ApiOperation(value = "결제 진행내역조회 API", notes = "결제 진행내역조회 API 입니다. ")
    @LoginNeedApi
    @PostMapping(path = "/payment/{advSeq}")
    public ApiResponseDto<PaymentDetailResult> detail(@PathVariable Long advSeq, MemberAuth memberAuth) throws ApiException {
        PaymentDetailResult paymentDetailResult = paymentService.findList(advSeq, memberAuth.getId());
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, paymentDetailResult);
    }

    @ApiOperation(value = "결제 등록 API", notes = "결제 등록 API 입니다. ")
    @LoginNeedApi
    @PostMapping(path = "/payment")
    public ApiResponseDto save(@Validated @RequestBody PaymentReq req, HttpServletRequest request,MemberAuth memberAuth) throws ApiException {
        String userAgent = request.getHeader("User-Agent").toUpperCase();

        log.info("==========================userAgent==========================");
        log.info("userAgent :" + userAgent);
        log.info("==========================userAgent//========================");
        Boolean isMobile = false;

        if (userAgent.indexOf(IS_MOBILE) > -1) {
            isMobile = true;
        }
        log.info("isMobile ===> " + isMobile);

        Long paymentSeq = req.getSeq();

        if (null == paymentSeq) {
            req.setRegDate(LocalDateUtils.krNow());
            req.setType(AdvProcessCodes.PROGRESS_RECEIPT.getCode());
            req.setMemberId(memberAuth.getId());
            req.setPayType(isMobile);
        } else {
            Payment payment = paymentRepository.findPaymentInfo(req.getSeq());

            req.setRegDate(payment.getRegDate());
            req.setType(payment.getType());
            req.setMemberId(payment.getMemberId());
            req.setPayType(isMobile);
        }

        paymentSeq = paymentService.save(req);

        return new ApiResponseDto(ApiResponseCodes.SUCCESS,paymentSeq);
    }

    @ApiOperation(value = "결제 취소 API", notes = "결제 취소 API 입니다. ")
    @LoginNeedApi
    @PutMapping(path = "/payment/{paymentSeq}/{advSeq}")
    public ApiResponseDto<InicisCancelRes> cancel(@PathVariable Long paymentSeq,@PathVariable Long advSeq, HttpServletRequest request,MemberAuth memberAuth) throws ApiException, Exception {
        String userAgent = request.getHeader("User-Agent").toUpperCase();

        log.debug("==========================userAgent=-===");
        log.debug("userAgent :" + userAgent);
        log.debug("==========================userAgent/////===");
        Boolean isMobile = false;
        if (userAgent.indexOf(IS_MOBILE) > -1) {
            isMobile = true;
        }

        InicisCancelRes result = paymentService.paymentCancel(paymentSeq,advSeq, isMobile, memberAuth.getId());
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, result);
    }

    @ApiOperation(value = "결제결과 조회 API", notes = "결제결과 조회 API 입니다. ")
    @LoginNeedApi
    @PostMapping(path = "/payment/result/{paymentSeq}")
    public ApiResponseDto<Payment> findPaymentInfo(@PathVariable Long paymentSeq, MemberAuth memberAuth) {
        Payment payment = paymentService.findPaymentList(paymentSeq, memberAuth.getId());
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, payment);
    }
}

