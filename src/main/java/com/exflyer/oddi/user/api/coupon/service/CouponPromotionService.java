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

    //???????????? ??????(???)
    public List<PaymentCouponRes> couponList2(PaymentCouponReq req) {
        req.setCouponUsable(false);
        req.setPromotionUsable(true);
        return paymentMapper.findPaymentCoupon(req);
    }

    public List<PaymentCouponRes> findCouponSearch(String channelType, String memberId) {
        //???????????? ??????
        PaymentCouponReq paymentCouponReq = new PaymentCouponReq(memberId, channelType);
        return couponList(paymentCouponReq);
    }

    //???????????? ?????? new
    public List<PaymentCouponRes> couponList(PaymentCouponReq req) {

        List<Payment> payment = paymentRepository.findByMemberIdAndType(req.getMemberId(), "PGT002");

        if(!CollectionUtils.isEmpty(payment)) {
            req.setSignupCouponType(SIGNUP_COUPON_TYPE);
        }
        return paymentMapper.findCouponPromotion(req);
    }

    //?????????????????? new
    @Transactional
    public void saveCoupon(String memberId, CouponPromotion promotionList) {
        //1. coupon  ???????????? ??????
        //2. coupon_promotion ???????????? ??????
        //2022.01.19 ???????????? ?????? ?????? 6?????? ??????.
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
        //?????? ????????? ??????
        if(couponYn > 0) {
            return couponConvertUuidNew(memberId);
        }
        return coupon;
    }

    @Transactional(rollbackFor = {Exception.class, Error.class})
    public Long save(String promotionChannelType, String couponCode, String memberId) throws ApiException {

        /**
         * 1. ???????????? ?????? ??????????????? ??????.
         * 2. ???????????? ?????? ???????????? ??????.
         * 3. ???????????? ??????.
         * 4. ???????????? ????????? ????????? ??????????????? ??????
         * - ????????? ????????????
         * - ????????? ????????? ???????????? ??????????????? ???????????? ?????? ?????? ??????.
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

    //?????? ???????????? ???????????? ??????
    public Coupon isValidCoupon(PromotionCouponReq req) throws ApiException {

        //???????????? ?????? ???????????????
        Coupon isValidCoupon = couponRepository.findByIsValidCoupon(req.getCouponCode());

        if(isValidCoupon == null) {
            throw new ApiException(ApiResponseCodes.COUPON_NOT_FOUND);
        }

        //?????? ???????????? ???????????? ?????? ?????? ??????
        findByUsableCount(isValidCoupon, req.getPromotionChannelType());
        //?????? ????????? ??????
        findByIslreadyCoupon(req.getCouponCode(),req.getMemberId());

        return isValidCoupon;
    }

    //?????? ???????????? ??????
    public Boolean findByUsableCount(Coupon isValidCoupon, String promotionChannelType) throws ApiException {
        List<String> promotionChannelTypeList = new ArrayList<>();

        if (StringUtils.isNotBlank(promotionChannelType)) {
            promotionChannelTypeList.add(promotionChannelType);
        } else {
            promotionChannelTypeList.add("PCT002");
            promotionChannelTypeList.add("PCT003");
        }

        CouponPromotion couponPromotion = couponPromotionRepository.findByUsableCount(isValidCoupon.getPromotionSeq(), promotionChannelTypeList.toArray(new String[]{}));

        //??????????????????????????? ?????? ?????????
        if(couponPromotion == null) {
            throw new ApiException(ApiResponseCodes.COUPON_TYPE_FOUND);
        }

        //???????????? ?????????
        if(Integer.parseInt(LocalDateUtils.dateConvertFormatter()) > Integer.parseInt(isValidCoupon.getExpiredDate())) {
            throw new ApiException(ApiResponseCodes.COUPON_EXPIRED_DAY);
        }
        //???????????? ?????????
        if(couponPromotion.getUsableCount() < isValidCoupon.getCouponCount()) {
            throw new ApiException(ApiResponseCodes.COUPON_ALREADY_TOTAL);
        }
        return true;
    }

    //?????? ????????? ??????
    public Boolean findByIslreadyCoupon(String couponCode, String memberId) throws ApiException {

        int isValidCouponMapping = couponPromotionMapper.findByIslreadyCoupon(couponCode, LocalDateUtils.dateConvertFormatter(), memberId);
        //?????? ????????? ??????
        if(isValidCouponMapping > 0) {
            throw new ApiException(ApiResponseCodes.COUPON_ALREADY_USABLE);
        }
        return true;
    }

    /**
     * ???????????? vaild ??????
     * false ?????? ?????? ?????? ????????????.
     * @param memberId
     * @param couponMappingSeq
     * @return
     * @throws ApiException
     */
    public Boolean isValidMemberCouponCode(String memberId, Long couponMappingSeq) throws ApiException {
        return couponMappingRepository.findByCouponInfo(memberId, couponMappingSeq) > 0 ? true: false;
    }

}
