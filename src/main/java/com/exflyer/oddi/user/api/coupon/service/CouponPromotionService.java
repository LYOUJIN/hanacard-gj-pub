package com.exflyer.oddi.user.api.coupon.service;

import com.exflyer.oddi.user.api.coupon.dao.CouponPromotionMapper;
import com.exflyer.oddi.user.api.payment.dao.PaymentMapper;
import com.exflyer.oddi.user.api.payment.dto.PaymentCouponReq;
import com.exflyer.oddi.user.api.payment.dto.PaymentCouponRes;
import com.exflyer.oddi.user.api.promotion.dto.PromotionCouponReq;
import com.exflyer.oddi.user.api.promotion.dto.PromotionCouponResult;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.models.Coupon;
import com.exflyer.oddi.user.models.CouponMapping;
import com.exflyer.oddi.user.models.CouponPromotion;
import com.exflyer.oddi.user.models.Payment;
import com.exflyer.oddi.user.models.Promotion;
import com.exflyer.oddi.user.models.PromotionCoupon;
import com.exflyer.oddi.user.repository.CouponMappingRepository;
import com.exflyer.oddi.user.repository.CouponPromotionRepository;
import com.exflyer.oddi.user.repository.CouponRepository;
import com.exflyer.oddi.user.repository.PaymentRepository;
import com.exflyer.oddi.user.repository.PromotionCouponRepository;
import com.exflyer.oddi.user.repository.PromotionRepository;
import com.exflyer.oddi.user.share.LocalDateUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


@Component
@Slf4j
public class CouponPromotionService {

    @Autowired
    private CouponPromotionRepository couponPromotionRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponMappingRepository couponMappingRepository;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private PromotionCouponRepository promotionCouponRepository;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private CouponPromotionMapper couponPromotionMapper;

    @Autowired
    private PaymentRepository paymentRepository;

    @PersistenceContext
    EntityManager em;

    private String SIGNUP_COUPON_TYPE = "MCT001";

    //프로모션 쿠폰(구)
    public List<PaymentCouponRes> couponList2(PaymentCouponReq req) {
        req.setCouponUsable(false);
        req.setPromotionUsable(true);
        return paymentMapper.findPaymentCoupon(req);
    }

    public List<PaymentCouponRes> findCouponSearch(String channelType, String memberId) {
        //쿠폰할인 조회
        PaymentCouponReq paymentCouponReq = new PaymentCouponReq(memberId, channelType);
        return couponList(paymentCouponReq);
    }

    //프로모션 쿠폰 new
    public List<PaymentCouponRes> couponList(PaymentCouponReq req) {

        List<Payment> payment = paymentRepository.findByMemberIdAndType(req.getMemberId(), "PGT002");

        if(!CollectionUtils.isEmpty(payment)) {
            req.setSignupCouponType(SIGNUP_COUPON_TYPE);
        }
        return paymentMapper.findCouponPromotion(req);
    }

    //가입쿠폰발급 new
    @Transactional
    public void saveCoupon(String memberId, CouponPromotion promotionList) {
        //1. coupon  테이블에 저장
        //2. coupon_promotion 테이블에 저장
        //2022.01.19 프로모션 쿠폰 숫자 6자리 변경.
        String couponCode = couponConvertUuidNew(memberId);

        Coupon coupon = new Coupon(promotionList, couponCode, 0, memberId);
        couponRepository.save(coupon);
        em.persist(coupon);

        CouponMapping couponMapping = new CouponMapping(coupon,memberId);
        coupon.setCouponCode(couponCode);
        couponMappingRepository.save(couponMapping);
    }

    public String couponConvertUuid() {
        String coupon =  UUID.randomUUID().toString().replaceAll("-", "").replaceAll("[^0-9]","");
        Boolean couponYn = promotionCouponRepository.findByIsValidCoupon(coupon, false) > 0 ? false : true;
        if(!couponYn) { return couponConvertUuid();}
        return coupon;
    }

    public String couponConvertUuidNew(String memberId) {
        String coupon =  UUID.randomUUID().toString().replaceAll("-", "").replaceAll("[^0-9]","");
        int couponYn = couponPromotionMapper.findByIslreadyCoupon(coupon, LocalDateUtils.dateConvertFormatter(), memberId);
        //이미 등록된 쿠폰
        if(couponYn > 0) {
            return couponConvertUuidNew(memberId);
        }
        return coupon;
    }

    @Transactional(rollbackFor = {Exception.class, Error.class})
    public Long save(String promotionChannelType, String couponCode, String memberId) throws ApiException {

        /**
         * 1. 프로모션 쿠폰 존재하는지 확인.
         * 2. 프로모션 쿠폰 사용횟수 확인.
         * 3. 만료일자 확인.
         * 4. 프로모션 쿠폰에 매팽이 되어있는지 체크
         * - 없으면 등록가능
         * - 동일한 쿠폰이 존재하면 만료일자가 지났으면 쿠폰 등록 가능.
         */

        Long couponMappingSeq = null;
        PromotionCouponReq req = new PromotionCouponReq(promotionChannelType, couponCode, memberId);
        req.setCouponCode(couponCode);
        req.setMemberId(memberId);
        Coupon isValidCoupon = isValidCoupon(req);

        if(isValidCoupon != null) {
            CouponMapping couponMapping = new CouponMapping(isValidCoupon, memberId);
            couponMappingRepository.save(couponMapping);
            em.persist(couponMapping);
            couponMappingSeq = couponMapping.getSeq();

            Coupon coupon = couponRepository.getOne(couponMapping.getCouponSeq());
            coupon.setCouponCount(coupon.getCouponCount()+1);
            couponRepository.save(coupon);
        }

        return couponMappingSeq;
    }

    //쿠폰 직접입력 유효한지 체크
    public Coupon isValidCoupon(PromotionCouponReq req) throws ApiException {

        //프로모션 쿠폰 존재하는지
        Coupon isValidCoupon = couponRepository.findByIsValidCoupon(req.getCouponCode());

        if(isValidCoupon == null) {
            throw new ApiException(ApiResponseCodes.COUPON_NOT_FOUND);
        }

        //쿠폰 만료일자 프로모션 채널 종류 체크
        findByUsableCount(isValidCoupon, req.getPromotionChannelType());
        //이미 등록된 쿠폰
        findByIslreadyCoupon(req.getCouponCode(),req.getMemberId());

        return isValidCoupon;
    }

    //쿠폰 만료일자 체크
    public Boolean findByUsableCount(Coupon isValidCoupon, String promotionChannelType) throws ApiException {
        List<String> promotionChannelTypeList = new ArrayList<>();

        if (StringUtils.isNotBlank(promotionChannelType)) {
            promotionChannelTypeList.add(promotionChannelType);
        } else {
            promotionChannelTypeList.add("PCT002");
            promotionChannelTypeList.add("PCT003");
        }

        CouponPromotion couponPromotion = couponPromotionRepository.findByUsableCount(isValidCoupon.getPromotionSeq(), promotionChannelTypeList.toArray(new String[]{}));

        //프로모션채널종류가 맞지 않으면
        if(couponPromotion == null) {
            throw new ApiException(ApiResponseCodes.COUPON_TYPE_FOUND);
        }

        //만료일자 넘으면
        if(Integer.parseInt(LocalDateUtils.dateConvertFormatter()) > Integer.parseInt(isValidCoupon.getExpiredDate())) {
            throw new ApiException(ApiResponseCodes.COUPON_EXPIRED_DAY);
        }
        //사용횟수 넘으면
        if(couponPromotion.getUsableCount() < isValidCoupon.getCouponCount()) {
            throw new ApiException(ApiResponseCodes.COUPON_ALREADY_TOTAL);
        }
        return true;
    }

    //이미 등록된 쿠폰
    public Boolean findByIslreadyCoupon(String couponCode, String memberId) throws ApiException {

        int isValidCouponMapping = couponPromotionMapper.findByIslreadyCoupon(couponCode, LocalDateUtils.dateConvertFormatter(), memberId);
        //이미 등록된 쿠폰
        if(isValidCouponMapping > 0) {
            throw new ApiException(ApiResponseCodes.COUPON_ALREADY_USABLE);
        }
        return true;
    }

    /**
     * 프로모션 vaild 체크
     * false 일때 결제 취소 날려야함.
     * @param memberId
     * @param couponMappingSeq
     * @return
     * @throws ApiException
     */
    public Boolean isValidMemberCouponCode(String memberId, Long couponMappingSeq) throws ApiException {
        return couponMappingRepository.findByCouponInfo(memberId, couponMappingSeq) > 0 ? true: false;
    }

}
