package com.exflyer.oddi.user.api.my.service;

import com.exflyer.oddi.user.api.my.dao.PaymentMngMapper;
import com.exflyer.oddi.user.api.my.dto.PaymentMng;
import com.exflyer.oddi.user.api.my.dto.PaymentMngResult;
import com.exflyer.oddi.user.share.dto.PagingResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentMngService {

    @Autowired
    private PaymentMngMapper paymentMngMapper;

    public PagingResult findList(PaymentMng paymentMng) {

        PageHelper.startPage(paymentMng.getPageNo(), paymentMng.getPageSize());
        Page<PaymentMngResult> resultList = paymentMngMapper.findList(paymentMng);
        int totalPrice = 0;
        resultList.forEach(data -> {
            //묶음상품이 아니라면
            if(data.getProductSeq() == null) {
                data.setPartnerList(paymentMngMapper.findPartnerList(data.getAdvSeq(), paymentMng.getMemberId()));
            }else {
                data.setPartnerList(paymentMngMapper.findProductPartnerList(data.getAdvSeq(), paymentMng.getMemberId(), data.getProductSeq()));
            }
        });

        return PagingResult.createResultDto(resultList);
    }
}

