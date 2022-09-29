package com.exflyer.oddi.user.api.adv.oddi.service;

import com.exflyer.oddi.user.api.adv.adv.dto.AdvFileRes;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvPartnerDetailRes;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvReadyPartnerSlotReq;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvReadyPartnerSlotRes;
import com.exflyer.oddi.user.api.adv.adv.dto.PartnerName;
import com.exflyer.oddi.user.api.adv.oddi.dao.OddiMapper;
import com.exflyer.oddi.user.api.adv.oddi.dto.FileListRes;
import com.exflyer.oddi.user.api.adv.oddi.dto.OddiDetailResult;
import com.exflyer.oddi.user.api.adv.oddi.dto.OddiHistoryReq;
import com.exflyer.oddi.user.api.adv.oddi.dto.OddiHistoryResult;
import com.exflyer.oddi.user.api.adv.oddi.dto.OddiProductPartnerRes;
import com.exflyer.oddi.user.api.adv.oddi.dto.OddiProductResult;
import com.exflyer.oddi.user.api.adv.oddi.dto.OddiProductiDetailResult;
import com.exflyer.oddi.user.api.adv.oddi.dto.OddiReq;
import com.exflyer.oddi.user.api.adv.oddi.dto.OddiResult;
import com.exflyer.oddi.user.api.voc.terms.dto.TermsServiceRes;
import com.exflyer.oddi.user.enums.ChannelCodes;
import com.exflyer.oddi.user.models.PartnerConfig;
import com.exflyer.oddi.user.models.Youtube;
import com.exflyer.oddi.user.repository.AdvRepository;
import com.exflyer.oddi.user.repository.PatnerConfigRepository;
import com.exflyer.oddi.user.share.LocalDateUtils;
import java.text.ParseException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@Slf4j
public class OddiService {

    @Autowired
    private OddiMapper oddiMapper;

    @Autowired
    private AdvRepository advRepository;

    @Autowired
    private PatnerConfigRepository patnerConfigRepository;

    public List<OddiResult> findList(OddiReq oddiReq) {

        List<OddiResult> result = oddiMapper.findList(oddiReq);
        result.forEach(datas -> {
            Youtube youtube = findVodInfo(datas.getSeq(), null, oddiReq.getChannelType());
            datas.setYoutube(youtube);

            if(datas.getFileSeq() != null) {
                List<FileListRes> findOddiFileList = oddiMapper.findOddiFileList(datas.getSeq());
                datas.setFileList(findOddiFileList);
            }
        });
        return result;
    }

    public List<OddiProductResult> findProductList(OddiReq oddiReq) {

        List<OddiProductResult> findPartnerList = oddiMapper.findProductList(oddiReq);

        findPartnerList.forEach(datas -> {
            Youtube youtube = findVodInfo(null, datas.getProductSeq(), oddiReq.getChannelType());
            datas.setYoutube(youtube);

            oddiReq.setSearchText(String.valueOf(datas.getProductSeq()));
            List<OddiProductPartnerRes> findProductPartnerList = oddiMapper.findProductPartnerList(oddiReq);
            datas.setPartnerList(findProductPartnerList);

            if(datas.getFileSeq() != null) {
                List<FileListRes> findOddiFileList = oddiMapper.findOddiProductFileList(datas.getProductSeq());
                datas.setFileList(findOddiFileList);
            }
        });

        return findPartnerList;
    }

    public OddiDetailResult findOddiDetailResult(Long advSeq, String id) {

        OddiDetailResult oddiDetailResult = oddiMapper.findAdvDetail(advSeq);
        List<AdvFileRes> findAdvFileList = oddiMapper.findAdvFileList(advSeq);
        List<AdvPartnerDetailRes> advPartnerResList = oddiMapper.findAdvPartnerList(advSeq);
        List<TermsServiceRes> termsList = oddiMapper.findTermsList(id);
        List<PartnerConfig> partnerConfig = patnerConfigRepository.findAll();

        if(!CollectionUtils.isEmpty(findAdvFileList)) {
            oddiDetailResult.setAdvFileList(findAdvFileList);
        }
        oddiDetailResult.setAdvPartnerList(advPartnerResList);
        oddiDetailResult.setMemberTerms(termsList);
        oddiDetailResult.setPartnerConfigList(partnerConfig);

        //알람확인
        advRepository.updateByUserCheck(advSeq, id, true);

        return oddiDetailResult;
    }

    public OddiProductiDetailResult findAdvProductDetail(Long advSeq, String id) {

        OddiProductiDetailResult oddiDetailResult = oddiMapper.findAdvProductDetail(advSeq);
        List<AdvFileRes> findAdvFileList = oddiMapper.findAdvFileList(advSeq);
        List<AdvPartnerDetailRes> advPartnerResList = oddiMapper.findAdvPartnerProductList(oddiDetailResult.getProductSeq(), oddiDetailResult.getAdvSeq());

        //묶음일 경우 슬롯수 1개 통일
        advPartnerResList.forEach(datas -> {
            datas.setTotalSlot(1);
        });

        List<TermsServiceRes> termsList = oddiMapper.findTermsList(id);
        List<PartnerConfig> partnerConfig = patnerConfigRepository.findAll();

        oddiDetailResult.setAdvFileList(findAdvFileList);
        oddiDetailResult.setAdvPartnerList(advPartnerResList);
        oddiDetailResult.setMemberTerms(termsList);
        oddiDetailResult.setPartnerConfigList(partnerConfig);

        //알람확인
        advRepository.updateByUserCheck(advSeq, id, true);

        return oddiDetailResult;
    }


    //광고 승인된거 결제취소된 것만 조회
    public List<OddiHistoryResult> findListHistory(String memberId) {

        OddiHistoryReq req = new OddiHistoryReq();
        req.setMemberId(memberId);

        req.setToDay(LocalDateUtils.krNowByFormatter("yyyyMMdd"));
        List<OddiHistoryResult> resultList = oddiMapper.findListHistory(req);
        resultList.forEach(data -> {

            req.setAdvSeq(data.getAdvSeq());

            //묶음상품이 아니라면
            if(data.getProductSeq() == 0) {
                data.setPartnerList(oddiMapper.findHistoryPartner(req));
            }else {
                req.setProductSeq(data.getProductSeq());
                data.setPartnerList(oddiMapper.findHistoryProductPartner(req));
            }
        });

        return resultList;
    }

    public Youtube findVodInfo(Long partnerSeq, Long productSeq, String channelType){
        return oddiMapper.findVodInfo(partnerSeq, productSeq, channelType);
    }


    public Youtube findTopVodInfo(String channelType){
        return oddiMapper.findTopVodInfo(channelType);
    }

    /**
     * 지정된 기간내의 사용가능 슬롯 수 조회
     * @param req
     * @return
     */
    public List<AdvReadyPartnerSlotRes> findReadyPartnerSlotList(AdvReadyPartnerSlotReq req) throws ParseException{
        return oddiMapper.findReadyPartnerSlotList(req.countEndDate());
    }

    /**
     * 매장명 리스트
     * @return
     */
    public List<PartnerName> findOddiNameList() {
        return oddiMapper.findOddiNameList(ChannelCodes.ODDI_ZONE.getCode());
    }

}
