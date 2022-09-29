package com.exflyer.oddi.user.api.my;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.exflyer.oddi.user.api.my.dto.MyCouponReq;
import com.exflyer.oddi.user.api.my.dto.PaymentMng;
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
public class MyCouponMngApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Autowired
    private TokenGenerator tokenGenerator;

    @DisplayName("사용자 쿠폰 조회")
    @Test
    public void findMyCouponListTest() throws Exception {

        MvcResult mvcResult = mockMvc
            .perform(
                MockMvcRequestBuilders.get("/my/coupon")
                    .header(TokenGenerator.Header, tokenGenerator.getTestToken())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(new MyCouponReq()))
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
        Map result = gson.fromJson(mvcResult.getResponse().getContentAsString(), Map.class);
        String responseCode = result.get("code").toString();
        Assertions.assertThat(responseCode).isEqualTo("000");
    }

    @DisplayName("사용자 쿠폰 등록")
    @Test
    public void saveMyCouponTest() throws Exception {

        String couponCode = "공통쿠폰 03.22";

        MvcResult mvcResult = mockMvc
            .perform(
                MockMvcRequestBuilders.post("/my/coupon/"+couponCode)
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
