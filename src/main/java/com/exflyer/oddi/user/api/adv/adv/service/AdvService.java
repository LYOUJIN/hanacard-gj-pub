package com.exflyer.oddi.user.api.adv.adv.service;

import com.exflyer.oddi.user.api.adv.adv.dao.AdvMapper;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvAddReq;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvPartnerReq;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvPartnerRes;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvProductPartnerRes;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvProductSearchResult;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvReadyPartnerSlotReq;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvReadyPartnerSlotRes;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvSearchReq;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvSearchResult;
import com.exflyer.oddi.user.api.adv.adv.dto.MemberCompanyRes;
import com.exflyer.oddi.user.api.adv.adv.dto.PartnerConfigReq;
import com.exflyer.oddi.user.api.adv.adv.dto.PartnerFiles;
import com.exflyer.oddi.user.api.adv.oddi.service.OddiService;
import com.exflyer.oddi.user.api.adv.subway.service.SubwayService;
import com.exflyer.oddi.user.api.files.service.FileService;
import com.exflyer.oddi.user.api.notification.service.KakaoNotificationService;
import com.exflyer.oddi.user.api.user.account.dto.TermsReq;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.models.Adv;
import com.exflyer.oddi.user.models.AdvFile;
import com.exflyer.oddi.user.models.AdvPartner;
import com.exflyer.oddi.user.models.AdvPartnerPk;
import com.exflyer.oddi.user.models.AdvProduct;
import com.exflyer.oddi.user.models.AdvProductPk;
import com.exflyer.oddi.user.models.Code;
import com.exflyer.oddi.user.models.Member;
import com.exflyer.oddi.user.models.MemberCompany;
import com.exflyer.oddi.user.models.MemberTerms;
import com.exflyer.oddi.user.models.Partner;
import com.exflyer.oddi.user.models.Product;
import com.exflyer.oddi.user.repository.AdvFileRepository;
import com.exflyer.oddi.user.repository.AdvPartnerRepository;
import com.exflyer.oddi.user.repository.AdvProductRepository;
import com.exflyer.oddi.user.repository.AdvRepository;
import com.exflyer.oddi.user.repository.CodeRepository;
import com.exflyer.oddi.user.repository.MemberCompanyRepository;
import com.exflyer.oddi.user.repository.PartnerRepository;
import com.exflyer.oddi.user.repository.ProductRepository;
import com.exflyer.oddi.user.repository.jpa.MemberRepository;
import com.exflyer.oddi.user.repository.jpa.MemberTermsRepository;
import com.exflyer.oddi.user.share.LocalDateUtils;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
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
public class AdvService {

    @PersistenceContext
    EntityManager em;

    @Autowired
    private AdvMapper advMapper;

    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    private AdvRepository advRepository;

    @Autowired
    private AdvPartnerRepository advPartnerRepository;

    @Autowired
    private MemberCompanyRepository memberCompanyRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AdvFileRepository advFileRepository;

    @Autowired
    private MemberTermsRepository memberTermsRepository;

    @Autowired
    private AdvProductRepository advProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private OddiService oddiService;

    @Autowired
    private SubwayService subwayService;

    public AdvSearchResult findAdvPartnerComm(AdvSearchReq req) {

        List<AdvPartnerRes> advPartnerResList =  advMapper.findAdvPartnerCode(req);
        List<Code> advTypeCodeOptional =  codeRepository.findByGroupCodeAndUsableOrderByOrderingAsc("BST", true);

        List<PartnerConfigReq> partnerConfig =  advMapper.findPartnerConfig(req.getChannelType());
        List<PartnerFiles> fileList = advMapper.findDefaultAdvFiles(req.getChannelType());
        MemberCompanyRes memberCompanyRes = advMapper.findMemberCompany(req);

        return new AdvSearchResult(advPartnerResList,advTypeCodeOptional, partnerConfig, fileList, memberCompanyRes);
    }

    public AdvProductSearchResult findAdvPartnerProductComm(AdvSearchReq req) {

        List<AdvProductPartnerRes> advPartnerResList =  advMapper.findAdvPartnerProductCode(req);
        //????????? ?????? ????????? 1??? ??????
        advPartnerResList.forEach(datas -> {
            datas.setTotalSlot(1);
        });

        List<Code> advTypeCodeOptional =  codeRepository.findByGroupCodeAndUsableOrderByOrderingAsc("BST", true);
        List<PartnerConfigReq> partnerConfig =  advMapper.findPartnerConfig(req.getChannelType());
        List<PartnerFiles> fileList = advMapper.findDefaultAdvFiles(req.getChannelType());

        return new AdvProductSearchResult(advPartnerResList,advTypeCodeOptional, partnerConfig, fileList);
    }

    @Transactional(rollbackFor = {Exception.class, Error.class})
    public Long save(AdvAddReq advAddReq) throws ApiException, ParseException {
        return saveAd(advAddReq, "I");
    }

    @Transactional(rollbackFor = {Exception.class, Error.class})
    public Long modify(AdvAddReq advAddReq) throws ApiException {

        Optional<Adv>  advOptional = advRepository.findById(advAddReq.getAdvSeq());
        Adv adv = advOptional.orElseThrow(() -> new ApiException(ApiResponseCodes.NOT_FOUND));
        advAddReq.setProgressCode(adv.getProgressCode());

        //ADT001 ??????, ADT002 ??????, ADT003 ??????
        if (adv == null) {throw new ApiException(ApiResponseCodes.NOT_FOUND);}
        if(!"ADT001".equals(adv.getAuditCode()) && !"ADT003".equals(adv.getAuditCode())) {
            throw new ApiException(ApiResponseCodes.AUDIT_NOT_FOUND);
        }
        return saveAd(advAddReq, "U");
    }

    private Long saveAd(AdvAddReq advAddReq, String saveType) throws ApiException {

        /**
         * TODO
         * 1. (????????? ??????/????????? ??????) slotPrice + ?????????
         * 2. ???????????? ?????? - price
         * 3. ?????? ?????? ??????
         */
        String startDate = advAddReq.getStartDate();
        String endDate = advAddReq.getEndDate();

        Integer diffMonth = LocalDateUtils.diffMonth(startDate,endDate);

        if(advAddReq.getProductSeq() != null) {
            Product product = productRepository.getOne(advAddReq.getProductSeq());
            advAddReq.setPrice(product.getPrice()*diffMonth);
            advAddReq.setProductPrice(product.getPrice());
        }else {
            advAddReq = partnerSlotPrice(advAddReq);
            advAddReq.setPrice(advAddReq.getPrice()*diffMonth);
        }

        Adv adv = new Adv(advAddReq);

        //?????? ?????? ????????? ??????
        if(StringUtils.isBlank(adv.getAuditCode()) || "ADT003".equals(adv.getAuditCode())) {
            adv.setAuditCode("ADT001");
            adv.setSendCode("ADM001");//????????????
        }

        List<MemberTerms> memberTerms = new ArrayList<>();
        if(!CollectionUtils.isEmpty(memberTerms)) {
            for(TermsReq term: advAddReq.getTerms()) {
                memberTerms.add(new MemberTerms(advAddReq.getRegId(), term.getTermsSeq(), term.getTermsAgree()));
            }
            memberTermsRepository.saveAll(memberTerms);
        }

        Optional<Member> memberOptional = memberRepository.findById(advAddReq.getRegId());
        Member member = memberOptional.orElseThrow(() -> new ApiException(ApiResponseCodes.NOT_FOUND));

        //???????????????
        if("BCT003".equals(advAddReq.getCode())) {
            member.setMemberGbn(true);
        }else {
            member.setMemberGbn(false);
            MemberCompany memberCompany = new MemberCompany(advAddReq.getMemberInfo(), advAddReq.getRegId(), advAddReq.getCode());
            memberCompanyRepository.save(memberCompany);
            if(advAddReq.getMemberInfo().getSeq() == null){ em.persist(memberCompany);}
            adv.setCompanySeq(memberCompany.getSeq());
        }

        memberRepository.save(member);

        //??????????????????
        if("U".equals(saveType)){
            adv.setManagerSendDone(false);
            adv.setSeq(advAddReq.getAdvSeq());
            adv.setProgressCode(advAddReq.getProgressCode());
        }

        //????????? ??????????????????
        adv.setUserCheck(false);
        advRepository.save(adv);
        if(!"U".equals(saveType)){em.persist(adv);}

        //??????????????? ??????
        Integer productPrice = 0;
        if(advAddReq.getProductSeq() != null) {

            AdvProduct advProduct = new AdvProduct(1,advAddReq.getProductPrice(),diffMonth);//????????????????????? 1??????
            AdvProductPk advProductPk = new AdvProductPk();
            advProductPk.setAdvSeq(adv.getSeq());
            advProductPk.setProductSeq(advAddReq.getProductSeq());
            advProduct.setAdvProductPk(advProductPk);
            advProductRepository.save(advProduct);

            if(advAddReq.getProductPrice() !=0) {
                productPrice = Math.round(advAddReq.getProductPrice()/advAddReq.getPartnerList().size());
            }

        }

        Long productSeq = advAddReq.getProductSeq();
        //?????? ?????? ??????
        Integer finalProductPrice = productPrice;
        advAddReq.getPartnerList().forEach(datas -> {

            if(productSeq != null) {datas.setPrice(finalProductPrice);}
            AdvPartner advPartner = new AdvPartner(datas.getRequestSlot(), datas.getPrice(),diffMonth);
            AdvPartnerPk advPartnerPk = new AdvPartnerPk(adv.getSeq(),datas.getPartnerSeq());
            advPartner.setAdvPartnerPk(advPartnerPk);

            advPartnerRepository.save(advPartner);
        });

        // ?????? ?????? ?????? ??????
        List<AdvFile> beforeFileItems = advFileRepository.findByAdvSeq(adv.getSeq());

        List<AdvFile> advFileList = advAddReq.getAdvFileList();

        if(!CollectionUtils.isEmpty(advFileList)){
            List<Long> fileSeqIs = new ArrayList<>();
            LocalDateTime advRegDate = advAddReq.getRegDate();
            advFileList.forEach(datas -> {
                datas.setAdvSeq(adv.getSeq());
                datas.setRegDate(advRegDate);
                fileSeqIs.add(datas.getFileSeq());
            });

            if(!CollectionUtils.isEmpty(beforeFileItems)){

                // ????????? fileSeq??????
                List<Long> isNotInFileSeq = advFileRepository.isNotInFileSeq(adv.getSeq(), fileSeqIs);

                // adv_file ??????
                advFileRepository.deleteAll(beforeFileItems);

                // S3, files ??????
                if(isNotInFileSeq != null){
                    fileService.delete(isNotInFileSeq);
                }
            }

            advFileRepository.saveAll(advFileList);     // ?????? ??????

            // ???????????? ??????
            fileService.updateMappingDone(fileSeqIs);
        }

        return adv.getSeq();
    }

    /**
     * ????????? ???????????? ???????????? ?????? ??? ??????
     * @param advAddReq
     * @return
     */
    public AdvAddReq partnerSlotPrice(AdvAddReq advAddReq) {

        AdvReadyPartnerSlotReq req = new AdvReadyPartnerSlotReq();
        req.setStartDate(advAddReq.getStartDate());
        req.setEndDate(advAddReq.getEndDate());

        AtomicReference<Integer> slotPrice = new AtomicReference<>(0);

        String channelType = advAddReq.getChannelType();

        advAddReq.getPartnerList().forEach(r->{
            List<Long> partnerSeq = Collections.singletonList(r.getPartnerSeq());
            req.setPartnerSeqList(partnerSeq);
            List<AdvReadyPartnerSlotRes> slotValidation = null;

            try{
                if("PTT001".equals(channelType)) {
                    slotValidation = oddiService.findReadyPartnerSlotList(req);
                }else {
                    slotValidation = subwayService.findReadyPartnerSlotList(req);
                }

                if(CollectionUtils.isEmpty(slotValidation)) {
                    throw new ApiException(ApiResponseCodes.ADV_PARTNER_TOTAL_SLOT);
                }

                //???????????? / ???????????? ?????? ???
                if(r.getRequestSlot() > slotValidation.get(0).getReadySlot()) {
                    throw new ApiException(ApiResponseCodes.ADV_PARTNER_TOTAL_SLOT);
                }

                Partner partner = partnerRepository.getOne(r.getPartnerSeq());
                slotPrice.updateAndGet(v -> v + partner.getSlotPrice() * r.getRequestSlot());
                r.setPrice(partner.getSlotPrice());

            }catch(Exception e){
                e.printStackTrace();
            }
        });

        advAddReq.setPrice(slotPrice.get());
        return advAddReq;
    }
}
