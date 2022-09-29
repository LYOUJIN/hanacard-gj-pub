package com.exflyer.oddi.user.api.adv.partner.service;

import com.exflyer.oddi.user.api.adv.partner.dao.PartnerDao;
import com.exflyer.oddi.user.api.adv.partner.dto.PartnerInfo;
import com.exflyer.oddi.user.api.adv.partner.dto.PartnerRes;
import com.exflyer.oddi.user.api.notification.service.KakaoNotificationService;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.enums.ChannelCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.models.Code;
import com.exflyer.oddi.user.models.Member;
import com.exflyer.oddi.user.models.PartnerRequest;
import com.exflyer.oddi.user.repository.AdvPartnerRequestRepository;
import com.exflyer.oddi.user.repository.CodeRepository;
import com.exflyer.oddi.user.repository.jpa.MemberRepository;
import com.exflyer.oddi.user.share.AesEncryptor;
import com.exflyer.oddi.user.share.LocalDateUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Component
@Slf4j
public class PartnerService {

    @Autowired
    private PartnerDao partnerDao;

    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AdvPartnerRequestRepository advPartnerRequestRepository;

    @PersistenceContext
    EntityManager em;

    public List<Code> getBusinessCode() {
        return codeRepository.findByGroupCodeAndUsableOrderByOrderingAsc("BST", true);
    }

    public String getMemberName(String id) throws ApiException{
        Optional<Member> memberOptional = memberRepository.findById(id);
        Member member = memberOptional.orElseThrow(() -> new ApiException(ApiResponseCodes.NOT_FOUND));
        return member.getName();
    }

    @Transactional
    public void save(PartnerRequest req){
        req.setRegDate(LocalDateUtils.krNow());
        advPartnerRequestRepository.save(req);
        em.persist(req);
    }

    public List<PartnerRes> getPartnerByAddr(){
        List<String> guList = partnerDao.findGroupByAddrGu();
        List<PartnerRes> res = new ArrayList<>();

        if(CollectionUtils.isEmpty(guList)){
            return res;
        }

        String channelType =  ChannelCodes.ODDI_ZONE.getCode();
        for(String gu: guList){
            PartnerRes r = new PartnerRes();
            List<PartnerInfo> info = partnerDao.findPartnerMall(gu,channelType);
            r.setGuName(gu);
            r.setPartnerInfos(info);
            res.add(r);
        }
        return res;
    }
}
