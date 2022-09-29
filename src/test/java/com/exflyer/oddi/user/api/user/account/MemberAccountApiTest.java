package com.exflyer.oddi.user.api.user.account;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.exflyer.oddi.user.api.user.account.dto.MemberAddReq;
import com.exflyer.oddi.user.api.user.account.dto.MemberIdDuplicationReq;
import com.exflyer.oddi.user.api.user.account.dto.MemberMyAccountModReq;
import com.exflyer.oddi.user.api.user.account.dto.TermsReq;
import com.exflyer.oddi.user.api.user.account.dto.VerificationNumberReq;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.share.TokenGenerator;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
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

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class MemberAccountApiTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private Gson gson;

  @Autowired
  private TokenGenerator tokenGenerator;

  @DisplayName("중복된 사용자 ID")
  @Test
  public void invalidId() throws Exception {

    MemberIdDuplicationReq req = new MemberIdDuplicationReq("yj.lee@nextnow.kr","oddi");

    MvcResult mvcResult = mockMvc
      .perform(
        MockMvcRequestBuilders.post("/account/duplication")
          .contentType(MediaType.APPLICATION_JSON)
          .content(gson.toJson(req))
      )
      .andDo(print())
      .andExpect(status().isOk())
      .andReturn();
    Map result = gson.fromJson(mvcResult.getResponse().getContentAsString(), Map.class);
    String responseCode = result.get("code").toString();
    Assertions.assertThat(responseCode).isEqualTo("007");
  }

  @DisplayName("인증 번호 발송")
  @Test
  public void sendVerificationNumber() throws Exception {

    MvcResult mvcResult = mockMvc
      .perform(
        MockMvcRequestBuilders.post("/verification-number/01047432141")
      )
      .andDo(print())
      .andExpect(status().isOk())
      .andReturn();
    Map result = gson.fromJson(mvcResult.getResponse().getContentAsString(), Map.class);
    String responseCode = result.get("code").toString();
    Assertions.assertThat(responseCode).isEqualTo("000");
  }

  @DisplayName("인증 번호 확인")
  @Test
  public void validVerificationNumber() throws Exception {
    VerificationNumberReq verificationNumberReq = new VerificationNumberReq("01051227638", "944700");
    MvcResult mvcResult = mockMvc
      .perform(
        MockMvcRequestBuilders.post("/verification-number/check")
          .contentType(MediaType.APPLICATION_JSON)
          .content(gson.toJson(verificationNumberReq))
      )
      .andDo(print())
      .andExpect(status().isOk())
      .andReturn();
    Map result = gson.fromJson(mvcResult.getResponse().getContentAsString(), Map.class);
    String responseCode = result.get("code").toString();
    Assertions.assertThat(responseCode).isEqualTo("000");
  }

  @DisplayName("중복되지 않은 사용자 ID")
  @Test
  public void validId() throws Exception {

    MemberIdDuplicationReq req = new MemberIdDuplicationReq("jisoon0923@test.com","oddi");

    MvcResult mvcResult = mockMvc
      .perform(
        MockMvcRequestBuilders.post("/account/duplication")
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

  @DisplayName("사용자 계정 추가")
  @Test
  public void addMember() throws Exception {

    MemberAddReq memberAddReq = new MemberAddReq();
    memberAddReq.setName("이유진");
    memberAddReq.setEmail("yj.lee@nextnow.kr");
    memberAddReq.setPassword("Jisoon0926!");
    memberAddReq.setPhoneNumber("01051227638");

    TermsReq termsReq = new TermsReq();
    termsReq.setTermsSeq(1L);
    termsReq.setTermsAgree(true);
    List<TermsReq> termsReqList = new ArrayList<>();
    termsReqList.add(termsReq);
    memberAddReq.setTerms(termsReqList);

    MvcResult mvcResult = mockMvc
      .perform(
        MockMvcRequestBuilders.post("/account")
          .contentType(MediaType.APPLICATION_JSON)
          .content(gson.toJson(memberAddReq))
      )
      .andDo(print())
      .andExpect(status().isOk())
      .andReturn();

    Map result = gson.fromJson(mvcResult.getResponse().getContentAsString(), Map.class);
    String responseCode = result.get("code").toString();
    Assertions.assertThat(responseCode).isEqualTo("000");
  }


  @DisplayName("사용자 내 계정 정보 조회")
  @Test
  public void findMyInfo() throws Exception {
    mockMvc
      .perform(
        MockMvcRequestBuilders.get("/account/my")
          .header(TokenGenerator.Header, tokenGenerator.getTestToken())

      )
      .andDo(print())
      .andExpect(status().isOk())
      .andReturn();
  }


  @DisplayName("내 계정 정보 수정")
  @Test
  public void modifyMyInfo() throws Exception {
//
//$2a$10$XPfZQn4Zul7aEfdINkw8kuE0VCJVjZd7/.xuzlfmtwenaC94GMW7K
    MemberMyAccountModReq modReq = new MemberMyAccountModReq();
    modReq.setPhoneNumber("01012345678");
    modReq.setCCompanyName("(주) 법인 최지순");
    modReq.setCCeo("최지순");
    modReq.setCBusinessLicenseNumber("123-46-67890");
    modReq.setCBusinessLicenseFileSeq(2L);

    modReq.setPCompanyName("최지순 회사");
    modReq.setPCeo("최지순");
    modReq.setPBusinessLicenseNumber("098-76-54321");
    modReq.setPBusinessLicenseFileSeq(1L);

    MvcResult mvcResult = mockMvc
      .perform(
        MockMvcRequestBuilders.put("/account/my")
          .header(TokenGenerator.Header, tokenGenerator.getTestToken())
        .contentType(MediaType.APPLICATION_JSON)
        .content(gson.toJson(modReq))
      )
      .andDo(print())
      .andExpect(status().isOk())
      .andReturn();
    Map result = gson.fromJson(mvcResult.getResponse().getContentAsString(), Map.class);
    String responseCode = result.get("code").toString();
    AssertionsForInterfaceTypes.assertThat(responseCode).isEqualTo(ApiResponseCodes.SUCCESS.getCode());
  }
}
