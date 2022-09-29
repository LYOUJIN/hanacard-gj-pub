package com.exflyer.oddi.user.api.voc.faq.dao;

import com.exflyer.oddi.user.api.voc.faq.dto.FaqCondition;
import com.exflyer.oddi.user.api.voc.faq.dto.FaqConditionResult;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface FaqMapper {

  List<FaqConditionResult> findByCondition(FaqCondition condition);

}
