package com.exflyer.oddi.user.api.adv.adv.dto;

import com.exflyer.oddi.user.api.user.account.dto.TermsReq;
import com.exflyer.oddi.user.models.AdvFile;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class AdvAddReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(hidden = true)
    private String regId;

    @ApiModelProperty(hidden = true)
    private LocalDateTime regDate;

    @ApiModelProperty(hidden = true)
    private String sendCode;

    @ApiModelProperty(value = "광고프로세스코드", hidden = true)
    private String progressCode;

    @ApiModelProperty(value="사용자 알림 확인여부", hidden = true)
    private Boolean userCheck;

    @ApiModelProperty(value = "채널타입(PTT001오디존, PTT002지하철)", position = 0)
    private String channelType;

    @ApiModelProperty(value = "묶음상품번호", position = 0)
    private Long productSeq;

    @ApiModelProperty(value = "광고번호", position = 0)
    private Long advSeq;

    @ApiModelProperty(value = "시작 날짜", position = 1)
    private String startDate;

    @ApiModelProperty(value = "종료 날짜", position = 2)
    private String endDate;

    @ApiModelProperty(value = "광고 제목", position = 4)
    private String title;

    @ApiModelProperty(value = "업종 종류 코드", position = 5)
    private String businessTypeCode;

    @ApiModelProperty(value = "기타 업종명", position = 6)
    private String etcBusinessName;

    @ApiModelProperty(value = "디자인 요청 여부", position = 7)
    private Boolean designRequest;

    @ApiModelProperty(value = "회원구분(개인,개인사업장,법인)", position = 9)
    private String code;

    @ApiModelProperty(value = "광고 진행상태 코드", position = 9)
    private String rejectionCode;

    @ApiModelProperty(value = "광고 신청취소 가능일", position = 9)
    private int oddiAdvCancelDate;

    @ApiModelProperty(value = "금액", position = 9)
    private Integer price;

    @ApiModelProperty(value = "신청 슬롯수", position = 9)
    private Integer totalSlot;

    @ApiModelProperty(value = "법인정보", position = 10)
    private AddMemeberReq memberInfo;

    @ApiModelProperty(value = "매장 광고 슬롯", position = 3)
    private List<AdvPartnerReq> partnerList;

    @ApiModelProperty(value = "이미지 파일리스트", position = 8)
    private List<AdvFile> advFileList;

    @ApiModelProperty(value = "약관 목록", position = 8)
    private List<TermsReq> terms;

    @ApiModelProperty(value = "묶음상품금액", hidden = true)
    private Integer productPrice;
}
