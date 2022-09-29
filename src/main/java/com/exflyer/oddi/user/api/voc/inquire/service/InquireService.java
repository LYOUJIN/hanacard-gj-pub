package com.exflyer.oddi.user.api.voc.inquire.service;

import com.exflyer.oddi.user.api.files.service.FileService;
import com.exflyer.oddi.user.api.notification.service.KakaoNotificationService;
import com.exflyer.oddi.user.api.voc.inquire.dao.InquireMapper;
import com.exflyer.oddi.user.api.voc.inquire.dto.InquireDetailRes;
import com.exflyer.oddi.user.api.voc.inquire.dto.InquireImage;
import com.exflyer.oddi.user.api.voc.inquire.dto.InquireListReq;
import com.exflyer.oddi.user.api.voc.inquire.dto.InquireListRes;
import com.exflyer.oddi.user.api.voc.inquire.dto.InquireMemberReq;
import com.exflyer.oddi.user.api.voc.inquire.dto.InquireNoMemberReq;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.models.Code;
import com.exflyer.oddi.user.models.Member;
import com.exflyer.oddi.user.models.Voc;
import com.exflyer.oddi.user.models.VocFile;
import com.exflyer.oddi.user.repository.CodeRepository;
import com.exflyer.oddi.user.repository.FilesRepository;
import com.exflyer.oddi.user.repository.InquireFileRepository;
import com.exflyer.oddi.user.repository.InquireRepository;
import com.exflyer.oddi.user.repository.jpa.MemberRepository;
import com.exflyer.oddi.user.share.AesEncryptor;
import com.exflyer.oddi.user.share.dto.PagingResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
public class InquireService {

    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    private InquireRepository inquireRepository;

    @Autowired
    private InquireFileRepository inquireFileRepository;

    @Autowired
    private FilesRepository filesRepository;

    @Autowired
    private AesEncryptor aesEncryptor;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private InquireMapper inquireMapper;

    @Autowired
    private FileService fileService;

    @PersistenceContext
    EntityManager em;

    public List<Code> getTypeCode() {
        return codeRepository.findByGroupCodeAndUsableOrderByOrderingAsc("IQT", true);
    }

    public void saveNoMemberReq(InquireNoMemberReq req){
        Voc inquireReq = new Voc();

        inquireReq.setNoMemberReq(req);
        save(inquireReq, req.getFileSeq());
    }

    @Transactional
    public void saveMemberReq(InquireMemberReq req) throws ApiException{
        Voc inquireReq = new Voc();
        Optional<Member> memberOptional = memberRepository.findById(req.getMemberId());
        Member member = memberOptional.orElseThrow(() -> new ApiException(ApiResponseCodes.NOT_FOUND));
        req.setEmail(member.getEmail());
        req.setPhoneNumber(member.getPhoneNumber());
        req.setName(member.getName());
        req.setUserCheck(false);
        inquireReq.setMemberReq(req);

        //수정할때 체크
        if(req.getSeq() != null){
            inquireReq.setSeq(req.getSeq());
            Optional<Voc> vocOptional = inquireRepository.findBySeqAndMemberId(req.getSeq(), req.getMemberId());
            Voc voc = vocOptional.orElseThrow(()->new ApiException(ApiResponseCodes.NOT_FOUND));
            if(StringUtils.isNotBlank(voc.getAnswer())){
                throw new ApiException(ApiResponseCodes.BAD_REQUEST);
            }
            List<Long> fileList = inquireFileRepository.findFileSeqBySeqNotFileSeq(req.getSeq(), req.getFileSeq());
            this.fileService.delete(fileList);
            inquireFileRepository.deleteAllByVocFilePk_VocSeq(req.getSeq());
        }
        save(inquireReq, req.getFileSeq());
    }

    public PagingResult findByMemberId(InquireListReq req){
        req.setOrderBy("seq desc");
        PageHelper.startPage(req.getPageNo(), req.getPageSize());
        Page<InquireListRes> result = inquireMapper.findByMemberId(req);
        return PagingResult.createResultDto(result);
    }


    @Transactional
    public void save(Voc inquireReq, Long fileSeq){
        String match = "[^a-zA-Z0-9]";
        inquireReq.setUniqCode(UUID.randomUUID().toString().replaceAll(match,""));
        inquireReq = inquireRepository.saveAndFlush(inquireReq);
        if(fileSeq != null && fileSeq > 0){
            VocFile vocFile = new VocFile(inquireReq.getSeq(), fileSeq);
            inquireFileRepository.saveAndFlush(vocFile);
            List<Long> seqList =new ArrayList<>();
            seqList.add(fileSeq);
            filesRepository.updateUseAble(seqList);
        }
    }

    public InquireDetailRes get(Long seq, String memberId, String uniqCode) throws ApiException{
        InquireDetailRes res = inquireMapper.findDetailBySeq(seq, memberId, uniqCode);
        if(res == null){
            throw new ApiException(ApiResponseCodes.NOT_FOUND);
        }
        List<InquireImage> images = inquireMapper.findImageByVocSeq(seq);
        if(!CollectionUtils.isEmpty(images)){
            res.setImageList(images);
        }

        if(seq != 0L) {
            inquireRepository.updateByUserCheck(seq, memberId, true);
        }
        return res;
    }
}
