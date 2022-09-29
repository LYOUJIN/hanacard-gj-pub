package com.exflyer.oddi.user.api.mustad.dto;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class FederatedAuth {

    private String clientId;

    private Map<String, Object> logins;

    private Map<String, String> providerUserProfile;

    public FederatedAuth(Map<String, Object> map, String clientId) {
        this.clientId = clientId;
        this.logins = map;
        this.providerUserProfile = new HashMap<>();
        this.providerUserProfile.put("firstName", " ");
        this.providerUserProfile.put("lastName", " ");
    }
}
