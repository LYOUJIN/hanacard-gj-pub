package com.exflyer.oddi.user.api.voc.notice.service;

import com.exflyer.oddi.user.api.files.service.FileService;
import com.exflyer.oddi.user.api.voc.notice.dao.NoticeMapper;
import com.exflyer.oddi.user.api.voc.notice.dto.NoticeCondition;
import com.exflyer.oddi.user.api.voc.notice.dto.NoticeConditionRes;
import com.exflyer.oddi.user.api.voc.notice.dto.NoticeDetailRes;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.share.dto.PagingResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private FileService fileService;

    public PagingResult findByCondition(NoticeCondition condition) {
        PageHelper.startPage(condition.getPageNo(), condition.getPageSize());
        Page<NoticeConditionRes> result = noticeMapper.findByCondition(condition);
        return PagingResult.createResultDto(result);
    }

    public NoticeDetailRes findNoticeDetail(Long rownum) throws ApiException {
        List<NoticeDetailRes> noticeDetailList = noticeMapper.findNoticeDetail(rownum);
        if (noticeDetailList == null) {
            throw new ApiException(ApiResponseCodes.NOT_FOUND);
        }

        NoticeDetailRes noticeDetailRes = new NoticeDetailRes();
        for(NoticeDetailRes res: noticeDetailList) {

            if ("aon".equals(res.getSeqType())) {
                noticeDetailRes = res;
                if(res.getFileSeq() != null) {
                    res.setFilePath(fileService.getFilePath(res.getFileSeq()));
                }
            }

            if("pre".equals(res.getSeqType())) {
                noticeDetailRes.setPreRownum(res.getRownum());
                noticeDetailRes.setPreTitle(res.getTitle());
                noticeDetailRes.setPreRegDate(res.getRegDate());
            }
            if("next".equals(res.getSeqType())) {
                noticeDetailRes.setNextRownum(res.getRownum());
                noticeDetailRes.setNextTitle(res.getTitle());
                noticeDetailRes.setNextRegDate(res.getRegDate());
            }

        }
        return noticeDetailRes;
    }

}
