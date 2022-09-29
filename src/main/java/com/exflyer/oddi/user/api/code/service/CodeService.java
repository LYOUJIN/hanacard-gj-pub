package com.exflyer.oddi.user.api.code.service;

import com.exflyer.oddi.user.models.Code;
import com.exflyer.oddi.user.models.CodeGroup;
import com.exflyer.oddi.user.repository.CodeGroupRepository;
import com.exflyer.oddi.user.repository.CodeRepository;
import io.swagger.annotations.Api;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.Cacheable;


@Api(tags = "코드", protocols = "http")
@Slf4j
@RestController
public class CodeService {

    @Autowired
    private CodeGroupRepository codeGroupRepository;

    @Autowired
    private CodeRepository codeRepository;

    @Cacheable(value = "groupCode")
    public List<CodeGroup> findGroupCodeList() {
        return codeGroupRepository.findAllByUsable(true);
    }

    @Cacheable(value = "code")
    public List<Code> findCodeList(String groupCode) {
        return codeRepository.findByGroupCodeAndUsableOrderByOrderingAsc(groupCode, true);
    }
}
