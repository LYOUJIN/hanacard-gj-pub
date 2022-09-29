package com.exflyer.oddi.user.api.voc.terms.dao;


import com.exflyer.oddi.user.api.voc.terms.dto.TermsServiceRes;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface TermsMapper {

    List<TermsServiceRes> findTermsService(Long status);
}
