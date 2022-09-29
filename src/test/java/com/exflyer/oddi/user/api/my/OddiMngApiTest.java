package com.exflyer.oddi.user.api.my;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.exflyer.oddi.user.api.my.dto.OddiMng;
import com.exflyer.oddi.user.share.TokenGenerator;
import com.google.gson.Gson;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
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
public class OddiMngApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Autowired
    private TokenGenerator tokenGenerator;

    @DisplayName("오디관리")
    @Test
    public void findMyOddiListTest() throws Exception {

        OddiMng req = new OddiMng();

        MvcResult mvcResult = mockMvc
            .perform(
                MockMvcRequestBuilders.post("/my/oddi")
                    .header(TokenGenerator.Header, tokenGenerator.getTestToken())
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

    @DisplayName("오디관리 알림확인")
    @Test
    public void findOddiAlarmTest() throws Exception {

        Long advSeq = 1L;

        MvcResult mvcResult = mockMvc
            .perform(
                MockMvcRequestBuilders.put("/my/oddi/"+advSeq)
                    .header(TokenGenerator.Header, tokenGenerator.getTestToken())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(String.valueOf(advSeq))
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
        Map result = gson.fromJson(mvcResult.getResponse().getContentAsString(), Map.class);
        String responseCode = result.get("code").toString();
        Assertions.assertThat(responseCode).isEqualTo("000");
    }

    @DisplayName("오디관리/1:1문의 알림")
    @Test
    public void findMyAlarmTest() throws Exception {

        MvcResult mvcResult = mockMvc
            .perform(
                MockMvcRequestBuilders.put("/my/oddi")
                    .header(TokenGenerator.Header, tokenGenerator.getTestToken())
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
        Map result = gson.fromJson(mvcResult.getResponse().getContentAsString(), Map.class);
        String responseCode = result.get("code").toString();
        Assertions.assertThat(responseCode).isEqualTo("000");
    }
}
