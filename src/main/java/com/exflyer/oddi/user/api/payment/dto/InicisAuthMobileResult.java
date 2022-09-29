package com.exflyer.oddi.user.api.payment.dto;

import java.util.Map;
import lombok.Data;

@Data
public class InicisAuthMobileResult {

  /**
   * 결과코드 ["00": 정상, 이외 실패]
   */
  private String resultCode;

  /**
   * 결과메세지
   */
  private String resultMsg;

  /**
   * 인증거래번호
   */
  private String P_TID;

  /**
   * 지불수단
   */
  private String P_TYPE;

  /**
   * 승인일자[YYYYMMDDhhmmss]
   */
  private String P_AUTH_DT;

  /**
   * 상점아이디
   */
  private String P_MID;

  /**
   * 주문번호
   */
  private String P_OID;

  /**
   * 거래금액
   */
  private Integer P_AMT;

  /**
   * 구매자명
   */
  private String P_UNAME;

  /**
   * 가맹점명
   */
  private String P_MNAME;

  /**
   * 가맹점 임의 데이터
   */
  private String P_NOTI;

  /**
   * 가맹점 전달 P_NOTI_URL
   */
  private String P_NOTEURL;

  /**
   * 가맹점 전달 P_NEXT_URL
   */
  private String P_NEXT_URL;


  /**
   * 발급사(은행) 코드
   */
  private String P_CARD_ISSUER_CODE;


  /**
   * 가맹점번호
   */
  private String P_CARD_MEMBER_NUM;


  /**
   * 매입사코드
   */
  private String P_CARD_PURCHASE_CODE;


  /**
   */
  private String P_CARD_PRTC_CODE;


  /**
   */
  private String P_CARD_INTEREST;


  /**
   */
  private String CARD_CorpFlag;


  /**
   */
  private String P_CARD_CHECKFLAG;


  /**
   */
  private String P_RMESG2;


  /**
   */
  private String P_FN_CD1;


  /**
   */
  private String P_FN_NM;


  /**
   */
  private String P_CARD_NUM;


  /**
   */
  private String P_AUTH_NO;


  /**
   */
  private String P_ISP_CARDCODE;


  /**
   */
  private String P_SRC_CODE;


  private Long paymentSeq;

  public void setInicisAuthMobileResult(Map map) {
    this.P_TID = (String) map.get("P_TID");
    this.P_TYPE = (String) map.get("P_TYPE");
    this.P_AUTH_DT = (String) map.get("P_AUTH_DT");
    this.P_MID = (String) map.get("P_MID");
    this.P_OID = (String) map.get("P_OID");
    this.P_AMT = Integer.parseInt((String)map.get("P_AMT"));
    this.P_UNAME = (String) map.get("P_UNAME");
    this.resultCode = (String) map.get("P_STATUS");
    this.resultMsg = (String) map.get("P_RMESG1");
    this.P_AUTH_NO = (String) map.get("P_AUTH_NO");
    this.P_CARD_NUM = (String) map.get("P_CARD_NUM");
    this.P_CARD_INTEREST = (String) map.get("P_CARD_INTEREST");
    this.P_RMESG2 = (String) map.get("P_RMESG2");
    this.P_FN_CD1 = (String) map.get("P_FN_CD1");
    this.CARD_CorpFlag = (String) map.get("CARD_CorpFlag");
    this.P_CARD_CHECKFLAG = (String) map.get("P_CARD_CHECKFLAG");
    this.P_SRC_CODE = (String) map.get("P_SRC_CODE");
  }

}
