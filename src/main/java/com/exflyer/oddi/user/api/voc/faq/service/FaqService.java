package com.exflyer.oddi.user.api.voc.faq.service;

import com.exflyer.oddi.user.api.voc.faq.dao.FaqMapper;
import com.exflyer.oddi.user.api.voc.faq.dto.FaqCondition;
import com.exflyer.oddi.user.api.voc.faq.dto.FaqConditionResult;
import com.exflyer.oddi.user.models.Code;
import com.exflyer.oddi.user.repository.CodeRepository;
import com.exflyer.oddi.user.repository.FaqRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FaqService {

  @Autowired
  private FaqMapper faqMapper;

  @Autowired
  private FaqRepository faqRepository;

  @Autowired
  private CodeRepository codeRepository;

  public List<Code> findFaqCode() {
    List<Code> faqTypeOptional =  codeRepository.findByGroupCode("PTT");
    return faqTypeOptional;
  }

  public List<FaqConditionResult> findByCondition(FaqCondition condition) {
    return faqMapper.findByCondition(condition);
  }

  public void updateClickCount(Long seq) {
    faqRepository.updateClickCount(seq);
  }
}
