package com.exflyer.oddi.user.api.adv.oddi.dto;

import com.exflyer.oddi.user.annotaions.DecryptField;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvFileRes;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvPartnerDetailRes;
import com.exflyer.oddi.user.api.voc.terms.dto.TermsServiceRes;
import com.exflyer.oddi.user.models.PartnerConfig;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@Data
public class OddiDetailResult {

    @ApiModelProperty(value = "광고순번", hidden = true)
    private Long advSeq;

    @DecryptField
    @ApiModelProperty(value = "이메일", position = 1)
    private String email;

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

    @ApiModelProperty(value = "업종 종류 코드", position = 5)
    private String businessTypeCode;

    @ApiModelProperty(value = "업종 종류 코드명", position = 6)
    private String businessTypeName;

    @ApiModelProperty(value = "기타 업종명", position = 7)
    private String etcBusinessName;

    @ApiModelProperty(value = "디자인 요청 여부", position = 8)
    private Boolean designRequest;

    @ApiModelProperty(value = "심사코드", position = 8)
    private String auditCode;

    @ApiModelProperty(value = "심사코드명", position = 8)
    private String auditCodeName;

    @ApiModelProperty(value = "진행코드", position = 8)
    private String progressCode;

    @ApiModelProperty(value = "진행코드명(심사중, 결제전, 결제후 등)", position = 8)
    private String progressCodeName;

    @ApiModelProperty(value = "광고신청일자", position = 8)
    private String regDate;

    @ApiModelProperty(value = "거절날짜", position = 8)
    private String rejectionDate;

    @ApiModelProperty(value = "거절타입", position = 8)
    private String rejectionCode;

    @ApiModelProperty(value = "거절타입명", position = 8)
    private String rejectionCodeName;

    @ApiModelProperty(value = "거절내용", position = 8)
    private String rejectionReason;

    @ApiModelProperty(value = "회원구분코드", position = 9)
    private String memberGbn;

    @ApiModelProperty(value = "회원구분(개인,개인사업장,법인)", position = 9)
    private String memberGbnName;

    @ApiModelProperty(value = "법인여부", position = 9)
    private String corporation;

    @ApiModelProperty(value = "회원회사순번", position = 9)
    private Long memberCompanySeq;

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

    @ApiModelProperty(value = "매핑완료여부", position = 0)
    private String mappingDone;

    @ApiModelProperty(value = "묶은상품금액", position = 0)
    private String price;

    @ApiModelProperty(value = "결제순번", position = 0)
    private Long paymentSeq;

    @ApiModelProperty(value = "광고 신청취소 가능일", position = 9)
    private int oddiAdvCancelDate;

    @ApiModelProperty(value = "매장 광고 슬롯", position = 10)
    private List<AdvPartnerDetailRes> advPartnerList;

    @ApiModelProperty(value = "이미지 파일리스트", position = 11)
    private List<AdvFileRes> advFileList;

    @ApiModelProperty(value = "약관리스트리스트", position = 11)
    private List<TermsServiceRes> memberTerms;

    @ApiModelProperty(value = "파트너설정", position = 12)
    private List<PartnerConfig> partnerConfigList;
}
