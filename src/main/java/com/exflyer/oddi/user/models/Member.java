package com.exflyer.oddi.user.models;

import com.exflyer.oddi.user.annotaions.DecryptField;
import com.exflyer.oddi.user.api.user.account.dto.MemberAddReq;
import com.exflyer.oddi.user.api.user.account.dto.MemberMyAccountModReq;
import com.exflyer.oddi.user.api.user.account.service.MemberPasswordEncoder;
import com.exflyer.oddi.user.share.LocalDateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 회원(광고주) 정보
 */
@Data
@Entity
@Table(name = "member")
@ApiModel("회원(광고주) 정보")
@NoArgsConstructor
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * id
    */
    @Id
    @ApiModelProperty("id")
    @Column(name = "id", nullable = false)
    private String id;

    /**
    * 이름
    */
    @ApiModelProperty("이름")
    @Column(name = "name", nullable = false)
    private String name;

    /**
    * 비밀번호
    */
    @ApiModelProperty("비밀번호")
    @Column(name = "password", nullable = false)
    private String password;

    /**
    * 전화번호
    */
    @ApiModelProperty("전화번호")
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
    * 수신 동의
    */
    @ApiModelProperty("수신 동의")
    @Column(name = "receive_consent", nullable = false)
    private boolean receiveConsent = false;

    /**
    * 인증 코드
    */
    @ApiModelProperty("인증 코드")
    @Column(name = "auth_code")
    private String authCode;

    /**
    * 가입 날짜
    */
    @ApiModelProperty("가입 날짜")
    @Column(name = "signup_date", nullable = false)
    private LocalDateTime signupDate;

    /**
    * 변경 날짜
    */
    @ApiModelProperty("변경 날짜")
    @Column(name = "mod_date", nullable = false)
    private LocalDateTime modDate;

    @ApiModelProperty("비밀번호 오류 카운트")
    @Column(name = "password_error_count")
    private Integer passwordErrorCount;

    @ApiModelProperty("비밀번호 변경 날짜")
    @Column(name = "password_mod_date")
    private LocalDateTime passwordModDate;


    @ApiModelProperty("로그인 날짜")
    @Column(name = "login_date")
    private LocalDateTime loginDate;

    @ApiModelProperty("상태코드 (CTS001 : 정상 CTS002 : 정지 CTS003 : 비밀번호오류)")
    @Column(name = "state_code")
    private String stateCode;


    /**
    * 개인구분
    */
    @Column(name = "member_gbn")
    @ApiModelProperty("개인구분")
    private Boolean memberGbn;

    /**
     * 이메일
     */
    @ApiModelProperty("email")
    @Column(name = "email")
    @DecryptField
    private String email;

    /**
     * 메모
     */
    @ApiModelProperty("메모")
    @Column(name = "memo")
    @DecryptField
    private String memo;

    /**
     * 광고 이메일수신 동의여부
     */
    @ApiModelProperty("emailReceiveConsent")
    @Column(name = "email_receive_consent")
    private boolean emailReceiveConsent;

    /**
     * 비밀번호 초기화여부
     */
    @ApiModelProperty("password_reset")
    @Column(name = "password_reset")
    private Boolean passwordReset;

    /**
     * 웹:0/모바일:1
     */
    @ApiModelProperty("웹:0/모바일:1")
    @Column(name = "login_type")
    private Boolean loginType;

    /**
     * 모바일 디바이스 종류
     */
    @ApiModelProperty("모바일 디바이스 종류")
    @Column(name = "login_mobile_type")
    private String loginMobileType;

    /**
     * 가입구분
     * 오디,토크니토,카카오 등등
     */
    @ApiModelProperty("오디,토크니토,카카오 등등")
    @Column(name = "provider")
    private String provider;


    /**
     * 머스타드 id
     */
    @ApiModelProperty("머스타드 id")
    @Column(name = "mustad_id")
    private String mustadId;

    @ApiModelProperty("머스타드 매핑여부")
    @Column(name = "mustad_mapping_done")
    private Boolean mustadMappingDone;


    public Member(MemberAddReq memberAddReq) {
        this.id = memberAddReq.getId();
        this.password = memberAddReq.getPassword();
        this.name = memberAddReq.getName();
        this.phoneNumber = memberAddReq.getPhoneNumber();
        this.signupDate = LocalDateUtils.krNow();
        this.passwordErrorCount = 0;
        this.passwordModDate = LocalDateUtils.krNow();
        this.stateCode = "CTS001";
        this.receiveConsent = memberAddReq.isReceiveConsent();
        this.email = memberAddReq.getEmail();
        this.emailReceiveConsent = memberAddReq.isEmailReceiveConsent();
        this.passwordReset = true;
        this.memberGbn = true;
        this.provider = memberAddReq.getProvider();
        this.mustadId = memberAddReq.getMustadId();
        this.mustadMappingDone = false;
    }

    public void setInfoByMyAccountModReq(MemberMyAccountModReq memberMyAccountModReq, MemberPasswordEncoder memberPasswordEncoder) {
        this.name = memberMyAccountModReq.getName();
        this.phoneNumber = memberMyAccountModReq.getPhoneNumber();
        this.email = memberMyAccountModReq.getEmail();
        this.modDate = LocalDateUtils.krNow();
        this.receiveConsent = memberMyAccountModReq.isReceiveConsent();
        this.emailReceiveConsent = memberMyAccountModReq.isEmailReceiveConsent();
    }
}
