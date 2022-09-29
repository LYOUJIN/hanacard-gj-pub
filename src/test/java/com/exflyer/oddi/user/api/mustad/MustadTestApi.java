package com.exflyer.oddi.user.api.mustad;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.exflyer.oddi.user.api.mustad.dto.MustadMessageReq;
import com.exflyer.oddi.user.api.mustad.dto.MustadToken;
import com.exflyer.oddi.user.api.my.dto.MyCouponReq;
import com.exflyer.oddi.user.share.TokenGenerator;
import com.google.gson.Gson;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * @user : 2022-07-07
 * @test
 */

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class MustadTestApi {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Autowired
    private TokenGenerator tokenGenerator;

    @DisplayName("머스타드 자동 로그인")
    @Test
    public void mustadSigninTest() throws Exception {

        MustadToken mustadToken = new MustadToken();
        mustadToken.setToken("6b8a1cc6-2263-4bd3-9b54-5c49f3e06649");
        MvcResult mvcResult =
            mockMvc
                .perform(
                    MockMvcRequestBuilders.post("/mustad/signin")
                        .header("User-Agent","OS")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(mustadToken))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Map result = gson.fromJson(mvcResult.getResponse().getContentAsString(), Map.class);
        String responseCode = result.get("code").toString();
        String responseMessage = result.get("message").toString();
        log.info("responseCode : {}", responseCode);
        log.info("responseMessage : {}", responseMessage);
        AssertionsForInterfaceTypes.assertThat(responseCode).isEqualTo("000");
    }

    @DisplayName("머스타드 사용자 변경")
    @Test
    public void modifyMustadMemberTest() throws Exception {

        String token = "6b8a1cc6-2263-4bd3-9b54-5c49f3e06649";

        MvcResult mvcResult =
            mockMvc
                .perform(
                    MockMvcRequestBuilders.post("/mustad/modify/user")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("token", token)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Map result = gson.fromJson(mvcResult.getResponse().getContentAsString(), Map.class);
        String responseCode = result.get("code").toString();
        String responseMessage = result.get("message").toString();
        log.info("responseCode : {}", responseCode);
        log.info("responseMessage : {}", responseMessage);
        AssertionsForInterfaceTypes.assertThat(responseCode).isEqualTo("000");
    }

    @DisplayName("머스타드 대기/광고 상태 조회")
    @Test
    public void mustadAdvStateTest() throws Exception {

        String mustadId = "yj.lee@nextnow.com";

        MvcResult mvcResult =
            mockMvc
                .perform(
                    MockMvcRequestBuilders.post("/mustad/adv-state")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("mustadId", mustadId)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Map result = gson.fromJson(mvcResult.getResponse().getContentAsString(), Map.class);
        String responseCode = result.get("code").toString();
        String responseMessage = result.get("message").toString();
        log.info("responseCode : {}", responseCode);
        log.info("responseMessage : {}", responseMessage);
        AssertionsForInterfaceTypes.assertThat(responseCode).isEqualTo("000");
    }

    @DisplayName("머스타드 회원탈퇴")
    @Test
    public void modifyMustadResignTest() throws Exception {

        String mustadId = "yj.lee@nextnow.com";

        MvcResult mvcResult =
            mockMvc
                .perform(
                    MockMvcRequestBuilders.post("/mustad/resign")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("mustadId", mustadId)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Map result = gson.fromJson(mvcResult.getResponse().getContentAsString(), Map.class);
        String responseCode = result.get("code").toString();
        String responseMessage = result.get("message").toString();
        log.info("responseCode : {}", responseCode);
        log.info("responseMessage : {}", responseMessage);
        AssertionsForInterfaceTypes.assertThat(responseCode).isEqualTo("000");
    }


    @DisplayName("문자 발송")
    @Test
    public void sendMessageTest() throws Exception {

        MustadMessageReq req = new MustadMessageReq();
        req.setPhoneNumber("01047432141");
        req.setText("테스트케이스");

        MvcResult mvcResult = mockMvc
            .perform(
                MockMvcRequestBuilders.post("/mustad/message")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(req))
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
        Map result = gson.fromJson(mvcResult.getResponse().getContentAsString(), Map.class);
        String responseCode = result.get("code").toString();
        Assertions.assertThat(responseCode).isEqualTo("000");
    }

    @DisplayName("머스타드 리스트 조회")
    @Test
    public void goMustadContentListTest() throws Exception {

        MvcResult mvcResult =
            mockMvc
                .perform(
                    MockMvcRequestBuilders.put("/mustad/content/2282d9dc-f43e-48ef-8777-7a76fe950bd6")
                        .header(TokenGenerator.Header, tokenGenerator.getTestToken())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Map result = gson.fromJson(mvcResult.getResponse().getContentAsString(), Map.class);
        String responseCode = result.get("code").toString();
        String responseMessage = result.get("message").toString();
        log.info("responseCode : {}", responseCode);
        log.info("responseMessage : {}", responseMessage);
        AssertionsForInterfaceTypes.assertThat(responseCode).isEqualTo("000");
    }

    @DisplayName("머스타드 사용자 회원탈퇴")
    @Test
    public void modifyMustadResignUsertTest() throws Exception {

        MvcResult mvcResult =
            mockMvc
                .perform(
                    MockMvcRequestBuilders.put("/mustad/resign-user")
                        .header("mustadId", "us-west-2:2d702077-bd54-4a48-b58a-32cc28d2eb8a")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Map result = gson.fromJson(mvcResult.getResponse().getContentAsString(), Map.class);
        String responseCode = result.get("code").toString();
        String responseMessage = result.get("message").toString();
        log.info("responseCode : {}", responseCode);
        log.info("responseMessage : {}", responseMessage);
        AssertionsForInterfaceTypes.assertThat(responseCode).isEqualTo("000");
    }

}
