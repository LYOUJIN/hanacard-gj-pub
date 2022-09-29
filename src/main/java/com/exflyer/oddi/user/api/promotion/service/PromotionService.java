package com.exflyer.oddi.user.api.promotion.service;

import com.exflyer.oddi.user.api.promotion.dao.PromotionMapper;
import com.exflyer.oddi.user.api.promotion.dto.PromotionCouponReq;
import com.exflyer.oddi.user.api.promotion.dto.PromotionCouponResult;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.models.PromotionCoupon;
import com.exflyer.oddi.user.repository.PromotionCouponRepository;
import com.exflyer.oddi.user.share.LocalDateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class PromotionService {

    @Autowired
    private PromotionCouponRepository promotionCouponRepository;

    @Autowired
    private PromotionMapper promotionMapper;

    /**
     * 프로모션 vaild 체크
     * false 일때 결제 취소 날려야함.
     * @param memberId
     * @param couponCode
     * @return
     * @throws ApiException
     */
    public Boolean isValidMemberCouponCode(String memberId, String couponCode) throws ApiException {

        Boolean b_returnCode = false;

        int isValidCoupon = promotionCouponRepository.findByIsValidCoupon(couponCode, false);
        int isMemberValidCoupon = promotionCouponRepository.findByIsMemberValidCoupon(memberId, couponCode, false);
        if (isValidCoupon > 0 && isMemberValidCoupon > 0) {
            b_returnCode = true;

            promotionCouponRepository.saveByMemberCouponUsing(true,LocalDateUtils.krNow(), memberId, couponCode);
        }

        return b_returnCode;
    }

    public PromotionCouponResult findPromotionCoupon(PromotionCouponReq req) {

        int isValidCoupon = promotionCouponRepository.findByIsValidCoupon(req.getCouponCode(), false);
        int isMemberValidCoupon = promotionCouponRepository.findByIsMemberValidCoupon(req.getMemberId(), req.getCouponCode(), false);
        if (isValidCoupon > 0 && isMemberValidCoupon < 1) {
            promotionCouponRepository.saveByMemberCoupon(req.getMemberId(), req.getCouponCode());
        }

        return promotionMapper.findPromotionCoupon(req);
    }
}
