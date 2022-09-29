package com.exflyer.oddi.user.api.payment.dto;

import com.exflyer.oddi.user.annotaions.DecryptField;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvPartnerReq;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import lombok.Data;

@Data
public class PaymentReq {

    @ApiModelProperty(value = "결제 순번", position = 0)
    private Long seq;

    @ApiModelProperty(value = "종류(결제, 취소)", position = 0)
    private String type;

    @ApiModelProperty(value = "회원 id", position = 0)
    private String memberId;

    @ApiModelProperty(value = "광고순번", position = 0)
    private Long advSeq;

    @ApiModelProperty(value = "금액", position = 0)
    private Integer price;

    @ApiModelProperty(value = "채널 종류(오디존:PTT001, 지하철:PTT002)", position = 0)
    private String channelType;

    @ApiModelProperty(value = "상품 이름", position = 0)
    private String productName;

    @ApiModelProperty(value = "광고 이름", position = 0)
    private String advName;

    @ApiModelProperty(value = "광고 시작 날짜", position = 0)
    private String advStartDate;

    @ApiModelProperty(value = "광고 종료 날짜", position = 0)
    private String advEndDate;

    @ApiModelProperty(value = "생성 날짜", position = 0)
    private LocalDateTime regDate;

    @ApiModelProperty(value = "응답 코드(PG 연동)", position = 0)
    private String responseCode;

    @ApiModelProperty(value = "응답 메세지(PG 연동)", position = 0)
    private String responseMessage;

    @ApiModelProperty(value = "연동 날짜(PG 연동)", position = 0)
    private String syncDate;

    @ApiModelProperty(value = "성공 여부(PG 연동)", position = 0)
    private Boolean success;

    @ApiModelProperty(value = "상품명", position = 0)
    private String goodName;

    @ApiModelProperty(value = "구매자명", position = 0)
    private String buyerName;

    @ApiModelProperty(value = "구매자 휴대전화번호", position = 0)
    @DecryptField
    private String buyerTel;

    @ApiModelProperty(value = "구매자 이메일", position = 0)
    @DecryptField
    private String buyerEmail;

    @ApiModelProperty(value = "프로모션 쿠폰코드", position = 0)
    private String couponNumber;

    @ApiModelProperty(value = "약관번호", position = 8)
    private List<Long> termsSeq;

    @ApiModelProperty(value = "프로모션 쿠폰매핑순번", position = 0)
    private Long couponMappingSeq;

    @ApiModelProperty(value = "매장 광고 슬롯", hidden = true)
    private List<AdvPartnerReq> partnerList;

    @ApiModelProperty(value = "프로모션채널종류", position = 0)
    private String promotionChannelType;

    @ApiModelProperty(value="결제구분 (웹:0,모바일:1)", hidden = true)
    private Boolean payType;

}
