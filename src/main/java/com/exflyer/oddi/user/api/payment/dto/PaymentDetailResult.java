package com.exflyer.oddi.user.api.payment.dto;

import com.exflyer.oddi.user.api.adv.adv.dto.AdvFileRes;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvPartnerDetailRes;
import com.exflyer.oddi.user.api.voc.terms.dto.TermsServiceRes;
import com.exflyer.oddi.user.models.PartnerConfig;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@Data
public class PaymentDetailResult {

    @ApiModelProperty(value = "광고순번", hidden = true)
    private Long advSeq;

    @ApiModelProperty(value = "묶음순번", hidden = true)
    private Long productSeq;

    @ApiModelProperty(value = "광고이름", hidden = true)
    private String productName;

    @ApiModelProperty(value = "채널타입코드", position = 1)
    private String channelType;

    @ApiModelProperty(value = "채널타입(오디존/지하철)", position = 1)
    private String channelTypeName;

    @ApiModelProperty(value = "시작 날짜", position = 1)
    private String startDate;

    @ApiModelProperty(value = "종료 날짜", position = 2)
    private String endDate;

    @ApiModelProperty(value = "광고 제목", position = 4)
    private String title;

    @ApiModelProperty(value = "디자인 요청 여부", position = 8)
    private Boolean designRequest;

    @ApiModelProperty(value = "회원구분코드", position = 9)
    private String memberGbn;

    @ApiModelProperty(value = "회원구분(개인,개인사업장,법인)", position = 9)
    private String memberGbnName;

    @ApiModelProperty(value = "법인여부", position = 9)
    private String corporation;

    @ApiModelProperty(value = "사업자이름", position = 9)
    private String name;

    @ApiModelProperty(value = "대표자명", position = 9)
    private String ceo;

    @ApiModelProperty(value = "사업자 등록번호", position = 9)
    private String businessLicenseNumber;

    @ApiModelProperty(value = "파일번호", position = 0)
    private Long fileSeq;

    @ApiModelProperty(value = "사업자파일명", position = 0)
    private String fileName;

    @ApiModelProperty(value = "사업자파일확장자", position = 0)
    private String extension;

    @ApiModelProperty(value = "사업자파일경로", position = 0)
    private String path;

    @ApiModelProperty(value = "사업자파일파일컨텐츠타입", position = 0)
    private String contentType;

    @ApiModelProperty(value = "사업자파일 s3_bucket", position = 0)
    private String s3Bucket;

    @ApiModelProperty(value = "사업자파일 s3_file_key", position = 0)
    private String s3FileKey;

    @ApiModelProperty(value = "총 광고금액", position = 4)
    private Integer totalPrice;

    @ApiModelProperty(value = "매장 광고 슬롯", position = 10)
    private List<AdvPartnerDetailRes> advPartnerList;

    @ApiModelProperty(value = "이미지 파일리스트", position = 11)
    private List<AdvFileRes> advFileList;

    @ApiModelProperty(value = "약관리스트리스트", position = 11)
    private List<TermsServiceRes> memberTerms;

    @ApiModelProperty(value = "쿠폰할인 리스트", position = 11)
    private List<PaymentCouponRes> couponList;

    @ApiModelProperty(value = "파트너설정 리스트", position = 11)
    private List<PartnerConfig> partnerConfig;

}
