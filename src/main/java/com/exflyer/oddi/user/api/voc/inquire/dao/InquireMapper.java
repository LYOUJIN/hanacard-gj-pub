package com.exflyer.oddi.user.api.voc.inquire.dao;

import com.exflyer.oddi.user.api.voc.inquire.dto.InquireDetailRes;
import com.exflyer.oddi.user.api.voc.inquire.dto.InquireImage;
import com.exflyer.oddi.user.api.voc.inquire.dto.InquireListReq;
import com.exflyer.oddi.user.api.voc.inquire.dto.InquireListRes;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquireMapper {

  Page<InquireListRes> findByMemberId(InquireListReq req);
  InquireDetailRes findDetailBySeq(long seq, String memberId, String uniqCode);
  List<InquireImage> findImageByVocSeq(long seq);
}
