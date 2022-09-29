package com.exflyer.oddi.user.api.mustad.dto;

import java.util.Map;
import lombok.Data;

@Data
public class FederatedAuthRes {

    private String token;

    private String userId;

    private String action;

    private Boolean isTerms;

    private String dmtDtm;

    private String resultCode;

    private String resultMessage;

    public void setFederatedAuthRes(Map res) {
        this.token = (String)res.get("token");
        this.userId = (String)res.get("userId");
        this.action = (String)res.get("action");
        this.isTerms = (Boolean)res.get("isTerms");
        this.dmtDtm = (String)res.get("dmtDtm");
        this.resultCode = (String)res.get("resultCode");
        this.resultMessage = (String) res.get("resultMessage");
    }

}
