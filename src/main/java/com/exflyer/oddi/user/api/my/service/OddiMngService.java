package com.exflyer.oddi.user.api.my.service;


import com.exflyer.oddi.user.api.my.dao.OddiMngMapper;
import com.exflyer.oddi.user.api.my.dto.MyAlarmResult;
import com.exflyer.oddi.user.api.my.dto.OddiMng;
import com.exflyer.oddi.user.api.my.dto.OddiMngResult;
import com.exflyer.oddi.user.api.voc.notice.dto.NoticeConditionRes;
import com.exflyer.oddi.user.repository.AdvRepository;
import com.exflyer.oddi.user.repository.InquireRepository;
import com.exflyer.oddi.user.share.dto.PagingResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class OddiMngService {

    @Autowired
    private OddiMngMapper oddiMngMapper;

    @Autowired
    private AdvRepository advRepository;

    @Autowired
    private InquireRepository inquireRepository;


    public PagingResult findList(OddiMng oddiMng) {

        PageHelper.startPage(oddiMng.getPageNo(), oddiMng.getPageSize());
        Page<OddiMngResult> resultList = oddiMngMapper.findList(oddiMng);

        resultList.forEach(data -> {

            oddiMng.setAdvSeq(data.getAdvSeq());

            //묶음상품이 아니라면
            if(data.getProductSeq() == 0) {
                data.setPartnerList(oddiMngMapper.findPartnerList(oddiMng));
            }else {
                oddiMng.setProductSeq(data.getProductSeq());
                data.setPartnerList(oddiMngMapper.findProductPartnerList(oddiMng));
            }
        });

        return PagingResult.createResultDto(resultList);
    }

    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void findOddiAlarm(Long advSeq, String memberId, Boolean userCheck) throws Exception {
        advRepository.updateByUserCheck(advSeq, memberId, userCheck);
    }

    public MyAlarmResult findMyAlarm(String memberId) {
        MyAlarmResult res = new MyAlarmResult();

        //오디관리 알람 존재
        Boolean oddiCnt = oddiMngMapper.findByMemberId(memberId)  < 1 ? false : true;
        //1:1문의하기 알람 존재
        Boolean InquireCnt = inquireRepository.findByMemberId(memberId) < 1 ? false : true;

        if(oddiCnt || InquireCnt) {
            res.setMypageAlarm(true);
        }

        res.setOddiMngAlarm(oddiCnt);
        res.setInquireAlarm(InquireCnt);

        return res;
    }
}
