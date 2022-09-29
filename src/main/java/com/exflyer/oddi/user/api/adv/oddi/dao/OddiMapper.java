package com.exflyer.oddi.user.api.adv.oddi.dao;

import com.exflyer.oddi.user.api.adv.adv.dto.AdvFileRes;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvPartnerDetailRes;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvReadyPartnerSlotReq;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvReadyPartnerSlotRes;
import com.exflyer.oddi.user.api.adv.adv.dto.PartnerName;
import com.exflyer.oddi.user.api.adv.oddi.dto.FileListRes;
import com.exflyer.oddi.user.api.adv.oddi.dto.OddiDetailResult;
import com.exflyer.oddi.user.api.adv.oddi.dto.OddiHistoryPartner;
import com.exflyer.oddi.user.api.adv.oddi.dto.OddiHistoryReq;
import com.exflyer.oddi.user.api.adv.oddi.dto.OddiHistoryResult;
import com.exflyer.oddi.user.api.adv.oddi.dto.OddiProductPartnerRes;
import com.exflyer.oddi.user.api.adv.oddi.dto.OddiProductResult;
import com.exflyer.oddi.user.api.adv.oddi.dto.OddiProductiDetailResult;
import com.exflyer.oddi.user.api.adv.oddi.dto.OddiReq;
import com.exflyer.oddi.user.api.adv.oddi.dto.OddiResult;
import com.exflyer.oddi.user.api.voc.terms.dto.TermsServiceRes;
import com.exflyer.oddi.user.models.Youtube;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface OddiMapper {

    List<OddiResult> findList(OddiReq oddiReq);
    List<FileListRes> findOddiFileList(Long seq);

    List<OddiProductResult> findProductList(OddiReq oddiReq);
    List<OddiProductPartnerRes> findProductPartnerList(OddiReq oddiReq);
    List<FileListRes> findOddiProductFileList(Long seq);



    OddiDetailResult findAdvDetail(Long advSeq);
    List<AdvFileRes> findAdvFileList(Long advSeq);

    List<AdvPartnerDetailRes> findAdvPartnerList(Long advSeq);

    List<TermsServiceRes> findTermsList(String id);

    OddiProductiDetailResult findAdvProductDetail(Long advSeq);
    List<AdvPartnerDetailRes> findAdvPartnerProductList(Long productSeq, Long advSeq);

    //이전광고조회
    List<OddiHistoryResult> findListHistory(OddiHistoryReq req);

    List<OddiHistoryPartner> findHistoryPartner(OddiHistoryReq req);
    List<OddiHistoryPartner> findHistoryProductPartner(OddiHistoryReq req);

    Youtube findVodInfo(Long partnerSeq, Long productSeq, String channelType);

    List<AdvReadyPartnerSlotRes> findReadyPartnerSlotList(AdvReadyPartnerSlotReq req);

    Youtube findTopVodInfo(String channelType);

    List<PartnerName> findOddiNameList(String channelType);
}
