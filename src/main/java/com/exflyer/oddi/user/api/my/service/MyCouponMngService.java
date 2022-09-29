package com.exflyer.oddi.user.api.my.service;

import com.exflyer.oddi.user.api.coupon.dao.CouponPromotionMapper;
import com.exflyer.oddi.user.api.coupon.service.CouponPromotionService;
import com.exflyer.oddi.user.api.my.dto.MyCouponReq;
import com.exflyer.oddi.user.api.my.dto.MyPromotionResult;
import com.exflyer.oddi.user.models.CouponMapping;
import com.exflyer.oddi.user.models.Payment;
import com.exflyer.oddi.user.repository.CouponMappingRepository;
import com.exflyer.oddi.user.repository.PaymentRepository;
import com.exflyer.oddi.user.share.LocalDateUtils;
import com.exflyer.oddi.user.share.dto.PagingResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@Slf4j
public class MyCouponMngService {

    @Autowired
    private CouponPromotionMapper couponPromotionMapper;

    @Autowired
    private PaymentRepository paymentRepository;

    private String SIGNUP_COUPON_TYPE = "MCT001";

    public Map findList(MyCouponReq myCouponReq) {

        PageHelper.startPage(myCouponReq.getPageNo(), myCouponReq.getPageSize());
        myCouponReq.setToday(LocalDateUtils.dateConvertFormatter());
        myCouponReq.setOrderBy(StringUtils.defaultIfBlank(myCouponReq.getOrderBy(), "v.usable_yn desc, v.reg_date desc"));
        PageHelper.orderBy(myCouponReq.getOrderBy());

        List<Payment> payment = paymentRepository.findByMemberIdAndType(myCouponReq.getMemberId(), "PGT002");
        if(CollectionUtils.isEmpty(payment)) {
            myCouponReq.setSignupCouponType(SIGNUP_COUPON_TYPE);
        }

        Page<MyPromotionResult> resultList = couponPromotionMapper.findList(myCouponReq);

        int usableCnt = couponPromotionMapper.findListUsableCnt(myCouponReq);

        Map result = new HashMap();
        result.put("usableCnt", usableCnt);
        result.put("list",PagingResult.createResultDto(resultList));
        return result;
    }

}
