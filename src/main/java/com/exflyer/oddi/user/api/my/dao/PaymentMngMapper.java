package com.exflyer.oddi.user.api.my.dao;

import com.exflyer.oddi.user.api.my.dto.OddiMngPartner;
import com.exflyer.oddi.user.api.my.dto.PaymentMng;
import com.exflyer.oddi.user.api.my.dto.PaymentMngResult;
import com.github.pagehelper.Page;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMngMapper {

    Page<PaymentMngResult> findList(PaymentMng paymentMng);
    List<OddiMngPartner> findPartnerList(Long advSeq,String memberId);
    List<OddiMngPartner> findProductPartnerList(Long advSeq, String memberId, Long productSeq);
}
