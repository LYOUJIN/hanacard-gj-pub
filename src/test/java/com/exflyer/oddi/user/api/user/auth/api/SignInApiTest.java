package com.exflyer.oddi.user.api.user.auth.api;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.exflyer.oddi.user.api.user.auth.dto.SignInReq;
import com.google.gson.Gson;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
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

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class SignInApiTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private Gson gson;

  private String BASE_CONTEXT = "/signin";

  @DisplayName("사용자 로그인")
  @Test
  public void signManager() throws Exception {
    SignInReq signInReq = new SignInReq();
    signInReq.setEmail("yj.lee@nexsoft.kr");
    signInReq.setPassword("12345678");

    // when
    MvcResult mvcResult =
      mockMvc
        .perform(
          MockMvcRequestBuilders.post(BASE_CONTEXT)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(gson.toJson(signInReq))
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

  @DisplayName("사용자 로그인 실패")
  @Test
  public void signFail() throws Exception {
    SignInReq signInReq = new SignInReq();
    signInReq.setEmail("jisoon0923@gmail.com");
    signInReq.setPassword("잘못된비밀번호");

    // when
    MvcResult mvcResult =
      mockMvc
        .perform(
          MockMvcRequestBuilders.post(BASE_CONTEXT)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(gson.toJson(signInReq))
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
