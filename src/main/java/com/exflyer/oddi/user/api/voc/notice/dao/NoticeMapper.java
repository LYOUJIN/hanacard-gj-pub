package com.exflyer.oddi.user.api.voc.notice.dao;

import com.exflyer.oddi.user.api.voc.notice.dto.NoticeCondition;
import com.exflyer.oddi.user.api.voc.notice.dto.NoticeConditionRes;
import com.exflyer.oddi.user.api.voc.notice.dto.NoticeDetailRes;
import com.github.pagehelper.Page;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeMapper {

  Page<NoticeConditionRes> findByCondition(NoticeCondition condition);
  List<NoticeDetailRes> findNoticeDetail(Long rownum);
}
