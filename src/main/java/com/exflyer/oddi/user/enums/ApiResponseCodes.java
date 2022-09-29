package com.exflyer.oddi.user.enums;

import lombok.Getter;

public enum ApiResponseCodes {

  SUCCESS(200, "000", "success"),
  NOT_DUPLICATE(200, "000", "중복 되지 않은 정보 입니다."),
  BAD_REQUEST(400, "001", "잘못된 요청 입니다."),
  NOT_FOUND(404, "002", "정보를 찾을 수 없습니다."),
  TOKEN_EXPIRED(401, "003", "토큰이 만료 되었습니다."),
  AUTHENTIFICATION(401, "004", "인증 정보가 잘못 되었습니다."),
  MISS_MATCH(400, "005", "정보가 일치 하지 않습니다."),
  DUPLICATE(400, "007", "중복된 정보 입니다."),
  FORBIDDEN(403, "008", "접근 권한이 없습니다."),
  INIT_PASSWORD(400, "009", "비밀번호 재설정이 필요 합니다."),
  NEED_TO_PASSWORD_CHANGED(200, "010", "비밀번호 변경이 필요 합니다."),
//  PASSWORD_CHANGE_DAY_OVER(200, "011", "비밀번호 변경 주기가 지났습니다."),
  PASSWORD_CHANGE_DAY_OVER(200, "011", "오랜 기간 비밀번호를 변경하지 않으셨습니다. \n"
    + "안전한 사용을 위하여, 비밀번호를 변경해주세요. \n"),
  EXPIRED_REQ_TIME(400, "012", "요청 시간이 초과 되었습니다."),
  SMS_SEND_FAIL(500, "013", "문자 발송에 실패 하였습니다."),
  AUDIT_NOT_FOUND(400, "014", "광고승인건으로 수정불가 합니다."),

  PASSWORD_CHANGE(400, "015", "패스워드가 일치하지 않습니다."),
  PASSWORD_NEW_CHANGE(400, "016", "변경하실 패스워드가 불일치 합니다."),

  // 인증번호 관련
  INVALID_VERIFICATION_NUMBER(403, "100", "인증번호가 불일치 합니다."),
  EXPIRED_VERIFICATION_NUMBER(403, "101", "인증번호 시간이 만료 되었습니다."),
  NOT_VERIFICATION(403, "102", "전화번호 인증을 하지 않았습니다."),

  // 파일관련
  INVALID_EXTENSION(400, "800", "허용 되지 않는 파일 입니다."),

  AWS_S3_FAIL(500, "900", "AWS S3 오류"),
  INTERNAL(500, "999", "관리자에게 문의 하세요"),

  USER_CTS002(400,"CTS002", "정지 회원입니다."),
  USER_CTS003(400,"CTS003", "비밀번호오류 입니다."),
  USER_CTS004(400,"CTS004", "휴면 회원입니다."),

  KAKAO_NOTI_ERROR(500, "500", "카카오 알림톡 전송이 실패하였습니다."),

  COUPON_NOT_FOUND(400, "400", "프로모션 쿠폰이 존재 하지 않습니다."),
  COUPON_ALREADY_TOTAL(401, "401", "쿠폰사용가능 횟수가 초과 되었습니다."),
  COUPON_ALREADY_USABLE(402, "402", "이미 등록된 쿠폰 입니다."),
  COUPON_EXPIRED_DAY(403, "403", "만료일자가 지난 쿠폰 입니다."),
  COUPON_TYPE_FOUND(404, "400", "프로모션 쿠폰이 채널종류와 맞지 않습니다."),
  
  ADV_TIME_CACNE(404, "002", "광고신청 시간이 경과 하였습니다."),

  ADV_PARTNER_TOTAL_SLOT(404, "400", "광고신청 슬롯 수가 초과되었습니다."),

  ERROR_MUSTAD_API(400, "100", "머스타드 CONNECT ERROR"),
  ERROR_MUSTAD_HEADER_API(400, "101", "머스타드 헤더 토큰정보가 없습니다."),
  ERROR_MUSTAD_DATA(400, "102", "머스타드 DATA ERROR"),
  ERROR_MUSTAD_STOREPROFILES_DATA(400, "103", "머스타드 스토어프로파일 DATA ERROR"),
  ERROR_MUSTAD_USERPROFILE_DATA(400, "104", "머스타드 사용자정보조회 DATA ERROR"),

  ERROR_MUSTAD_STOREPROFILES_API(400, "103", "머스타드 스토어프로파일 StatusCode ERROR"),
  ERROR_MUSTAD_USERPROFILE_API(400, "104", "머스타드 사용자정보조회 StatusCode ERROR"),

  ERROR_MUSTAD_NO_USER(400, "105", "머스타드 사용자 정보가 존재하지 않습니다."),
  ERROR_MUSTAD_NOT_FOUND(404, "106", "머스타드 가입정보가 존재하지 않습니다."),
  ERROR_MUSTAD_SMS_NOT_TEL(404, "107", "전화번호 미입력 오류입니다."),
  ERROR_MUSTAD_MEMBER_SIGNIN(404, "108", "머스타드회원 자동가입 오류."),
  ERROR_MUSTAD_PROVIDER_NOT_FOUND(404, "109", "Provider not found"),
  ERROR_MUSTAD_EMAIL_NOT_FOUND(404, "110", "Email not found"),
  ERROR_MUSTAD_TEL_NOT_FOUND(404, "111", "PhoneNumber not found"),

  ADV_OPERATION(400,"200","광고 운영중인 사용자 입니다."),
  ADV_READY(400,"201","광고 준비중인 사용자 입니다."),

  ;


  @Getter
  private final int status;

  @Getter
  private final String code;

  @Getter
  private final String message;


  ApiResponseCodes(int status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;

  }
}
