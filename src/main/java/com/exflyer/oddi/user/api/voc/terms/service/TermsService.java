package com.exflyer.oddi.user.api.voc.terms.service;


import com.exflyer.oddi.user.api.voc.terms.dao.TermsMapper;
import com.exflyer.oddi.user.api.voc.terms.dto.TermsServiceRes;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TermsService {

    @Autowired
    private TermsMapper termsMapper;

    public List<TermsServiceRes> findTermsService(Long status) throws ApiException {
        List<TermsServiceRes> termsServiceRes = termsMapper.findTermsService(status);
        if(termsServiceRes == null) {
            throw new ApiException(ApiResponseCodes.NOT_FOUND);
        }

        return termsServiceRes;
    }
}
