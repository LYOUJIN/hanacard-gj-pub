package com.exflyer.oddi.user.api.adv.subway;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.exflyer.oddi.user.api.adv.adv.dto.AdvAddReq;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvPartnerReq;
import com.exflyer.oddi.user.api.adv.subway.dto.SubwayPartnerSearchReq;
import com.exflyer.oddi.user.api.payment.dto.PaymentReq;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.models.AdvFile;
import com.exflyer.oddi.user.share.TokenGenerator;
import com.google.gson.Gson;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * 지하철 광고 등록 테스트 시나리오
 * 1. 지하철 파트너 리스트 조회(지하철 또는 지하철 묶음조회)
 * 2. 파일 등록(디자인 또는 동영상)
 * 3. 지하철광고 등록
 * 4. 쿠폰등록
 * 5. 결제
 * 6. 결제취소
 */
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class SubwayApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Autowired
    private TokenGenerator tokenGenerator;

    @DisplayName("지하철 파트너 리스트 API")
    @Test
    public void findListTest() throws Exception {

        SubwayPartnerSearchReq req = new SubwayPartnerSearchReq();
        //묶음상품조회일 경우 주석 해제
        //req.setProduct(true);

        MvcResult mvcResult = mockMvc
            .perform(
                MockMvcRequestBuilders.get("/subway/partners")
                    .header(TokenGenerator.Header, tokenGenerator.getTestToken())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(req))
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
        Map result = gson.fromJson(mvcResult.getResponse().getContentAsString(), Map.class);
        String responseCode = result.get("code").toString();
        AssertionsForInterfaceTypes.assertThat(responseCode).isEqualTo(ApiResponseCodes.SUCCESS.getCode());
    }


    @DisplayName("디자인 파일 등록 API(type:AFT001)")
    @Test
    public void uploadAdvTest() throws Exception  {

        String path = "D:\\abcd.png"; // 로컬 파일 경로

        MvcResult mvcResult = mockMvc
            .perform(MockMvcRequestBuilders.multipart("/file/advSingle")
                .file(new MockMultipartFile("file", "abcd.png", MediaType.APPLICATION_JSON_VALUE, IOUtils.toByteArray(new FileInputStream(path))))
                .header(TokenGenerator.Header, tokenGenerator.getTestToken())
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn()
            ;

        Map result = gson.fromJson(mvcResult.getResponse().getContentAsString(), Map.class);
        String responseCode = result.get("code").toString();
        AssertionsForInterfaceTypes.assertThat(responseCode).isEqualTo(ApiResponseCodes.SUCCESS.getCode());
    }

    @DisplayName("동영상 파일 등록 API(type:AFT002)")
    @Test
    public void uploadVideoTest() throws Exception  {

        String path = "D:\\video.mp4"; // 로컬 파일 경로

        MvcResult mvcResult = mockMvc
            .perform(MockMvcRequestBuilders.multipart("/file/video")
                .file(new MockMultipartFile("file", "video.mp4", MediaType.APPLICATION_JSON_VALUE, IOUtils.toByteArray(new FileInputStream(path))))
                .header(TokenGenerator.Header, tokenGenerator.getTestToken())
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn()
            ;

        Map result = gson.fromJson(mvcResult.getResponse().getContentAsString(), Map.class);
        String responseCode = result.get("code").toString();
        AssertionsForInterfaceTypes.assertThat(responseCode).isEqualTo(ApiResponseCodes.SUCCESS.getCode());
    }

    @DisplayName("지하철광고 등록 API")
    @Test
    public void saveTest() throws Exception {

        Long partnerSeq = 15L; //지하철 파트너 리스트 API 조회값
        Integer price = 1000;//지하철 파트너 리스트 API 조회값
        Long fileSeq = 1L;//파일 등록 API에서 등록한 seq
        String type = "AFT001"; //파일 등록 API(AFT001,AFT002)
        String startDate = "20220805";
        String endDate = "20221030";
        String title = "지하철로컬테스트";

        AdvAddReq req = new AdvAddReq();

        req.setChannelType("PTT002");
        req.setStartDate(startDate);
        req.setEndDate(endDate);
        req.setTitle(title);
        req.setBusinessTypeCode("BST001");
        req.setCode("BCT003"); //개인사업자
        req.setDesignRequest(false);
        req.setOddiAdvCancelDate(7);
        req.setPrice(price);
        req.setTotalSlot(1);

        //지하철광고 슬롯
        List<AdvPartnerReq> partnerList = new ArrayList<>();
        AdvPartnerReq advPartnerReq = new AdvPartnerReq();
        advPartnerReq.setPartnerSeq(partnerSeq);
        advPartnerReq.setRequestSlot(1);
        advPartnerReq.setStartDate(startDate);
        advPartnerReq.setEndDate(endDate);
        partnerList.add(advPartnerReq);
        req.setPartnerList(partnerList);

        //이미지 파일 리스트
        List<AdvFile> advFileList = new ArrayList<>();
        AdvFile advFile = new AdvFile();
        advFile.setFileSeq(fileSeq);
        advFile.setType(type);
        advFileList.add(advFile);

        req.setAdvFileList(advFileList);

        MvcResult mvcResult = mockMvc
            .perform(
                MockMvcRequestBuilders.post("/subway")
                    .header(TokenGenerator.Header, tokenGenerator.getTestToken())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(req))
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
        Map result = gson.fromJson(mvcResult.getResponse().getContentAsString(), Map.class);
        String responseCode = result.get("code").toString();
        AssertionsForInterfaceTypes.assertThat(responseCode).isEqualTo(ApiResponseCodes.SUCCESS.getCode());
    }

    @DisplayName("쿠폰 등록 API")
    @Test
    public void saveCouponTest() throws Exception {

        String channelType = "PCT003";
        String couponCode = "공통쿠폰 03.22";

        MvcResult mvcResult = mockMvc
            .perform(
                MockMvcRequestBuilders.get("/promotion/"+channelType+"/"+couponCode)
                    .header(TokenGenerator.Header, tokenGenerator.getTestToken())
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
        Map result = gson.fromJson(mvcResult.getResponse().getContentAsString(), Map.class);
        String responseCode = result.get("code").toString();
        AssertionsForInterfaceTypes.assertThat(responseCode).isEqualTo(ApiResponseCodes.SUCCESS.getCode());
    }

    @DisplayName("결제 등록 API")
    @Test
    public void savePayment() throws Exception {

        Long advSeq = 734L;//지하철광고 등록 API에서 리턴받은 advSeq
        Integer price = 1000;//지하철 파트너 리스트 API 조회값
        String startDate = "20220805";
        String endDate = "20221030";
        String title = "지하철로컬테스트";
        Long couponMappingSeq = 121L;//쿠폰 등록 API 리턴값

        PaymentReq req = new PaymentReq();

        req.setChannelType("PTT002");
        req.setPrice(price);
        req.setAdvSeq(advSeq);
        req.setCouponMappingSeq(couponMappingSeq);
        req.setPromotionChannelType("PCT003");
        req.setAdvName(title);
        req.setProductName(title);
        req.setAdvStartDate(startDate);
        req.setAdvEndDate(endDate);

        MvcResult mvcResult = mockMvc
            .perform(
                MockMvcRequestBuilders.post("/payment")
                    .header(TokenGenerator.Header, tokenGenerator.getTestToken())
                    .header("User-Agent","OS")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(req))
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
        Map result = gson.fromJson(mvcResult.getResponse().getContentAsString(), Map.class);
        String responseCode = result.get("code").toString();
        AssertionsForInterfaceTypes.assertThat(responseCode).isEqualTo(ApiResponseCodes.SUCCESS.getCode());
    }

    @DisplayName("결제 취소 API")
    @Test
    public void savePaymentCancel() throws Exception {

        Long paymentSeq = 369L;
        Long advSeq = 734L;

        MvcResult mvcResult = mockMvc
            .perform(
                MockMvcRequestBuilders.put("/payment/"+ paymentSeq + "/" + advSeq)
                    .header(TokenGenerator.Header, tokenGenerator.getTestToken())
                    .header("User-Agent","OS")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
        Map result = gson.fromJson(mvcResult.getResponse().getContentAsString(), Map.class);
        String responseCode = result.get("code").toString();
        AssertionsForInterfaceTypes.assertThat(responseCode).isEqualTo(ApiResponseCodes.SUCCESS.getCode());
    }

}
