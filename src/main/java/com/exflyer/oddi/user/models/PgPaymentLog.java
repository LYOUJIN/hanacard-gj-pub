package com.exflyer.oddi.user.models;

import com.exflyer.oddi.user.annotaions.EncryptField;
import com.exflyer.oddi.user.api.payment.dto.InicisAuthMobileResult;
import com.exflyer.oddi.user.api.payment.dto.InicisAuthResult;
import com.exflyer.oddi.user.api.payment.dto.InicisCancelRes;
import com.exflyer.oddi.user.api.payment.dto.PaymentCancelRes;
import com.exflyer.oddi.user.share.LocalDateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@Entity
@ApiModel("PG결제 로그")
@Table(name="pg_payment_log")
@NoArgsConstructor
public class PgPaymentLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 결제 순번
     */
    @Id
    @ApiModelProperty("결제 순번")
    @Column(name = "seq", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    /**
     * 이름
     */
    @ApiModelProperty("거래번호")
    @Column(name = "tid", nullable = false)
    private String tid;

    /**
     * 주문번호 * 결제 요청시 oid 필드에 설정된 값
     */
    @ApiModelProperty("주문번호 * 결제 요청시 oid 필드에 설정된 값")
    @Column(name = "moid", nullable = false)
    private String moid;

    /**
     * 결제금액
     */
    @ApiModelProperty("결제금액")
    @Column(name = "price", nullable = false)
    private Integer price;

    /**
     * 지불수단
     */
    @ApiModelProperty("지불수단")
    @Column(name = "pay_method", nullable = false)
    private String payMethod;

    /**
     * 구매자명
     */
    @ApiModelProperty("구매자명")
    @Column(name = "buyer_name")
    private String buyerName;

    /**
     * 구매자 휴대폰번호
     */
    @ApiModelProperty("구매자 휴대폰번호")
    @EncryptField
    @Column(name = "buyer_tel")
    private String buyerTel;

    /**
     * 구매자 이메일주소
     */
    @ApiModelProperty("구매자 이메일주소")
    @EncryptField
    @Column(name = "buyer_email")
    private String buyerEmail;

    /**
     * 최종 이메일주소
     */
    @ApiModelProperty("최종 이메일주소")
    @EncryptField
    @Column(name = "cust_email")
    private String custEmail;

    /**
     * 승인번호( 지불수단에 따라 미전송)
     */
    @ApiModelProperty("승인번호( 지불수단에 따라 미전송)")
    @Column(name = "appl_num")
    private String applNum;

    /**
     * 승인일자 [YYYYMMDD]
     */
    @ApiModelProperty("승인일자 [YYYYMMDD]")
    @Column(name = "appl_date")
    private String applDate;

    /**
     * 승인시간 [hh24miss]
     */
    @ApiModelProperty("승인시간 [hh24miss]")
    @Column(name = "appl_time")
    private String applTime;

    /**
     * 이름
     */
    @ApiModelProperty("이름")
    @Column(name = "card_num")
    private String cardNum;

    /**
     *  상점부담 무이자 할부여부 ["1":상점부담 무이자]
     */
    @ApiModelProperty(" 상점부담 무이자 할부여부 [\"1\":상점부담 무이자]")
    @Column(name = "card_interest")
    private String cardInterest;

    /**
     * 카드 할부기간
     */
    @ApiModelProperty("카드 할부기간")
    @Column(name = "card_quota")
    private String cardQuota;

    /**
     * 카드사코드
     */
    @ApiModelProperty("카드사코드")
    @Column(name = "card_code")
    private String cardCode;

    /**
     * 카드구분 ["0":개인카드, "1":법인카드, "9":구분불가] 승인실패 시 빈값 전달
     */
    @ApiModelProperty("카드구분 [\"0\":개인카드, \"1\":법인카드, \"9\":구분불가] 승인실패 시 빈값 전달")
    @Column(name = "card_corp_flag")
    private String cardCorpFlag;

    /**
     * 카드종류 ["0":신용카드, "1":체크카드, "2":기프트카드]
     */
    @ApiModelProperty("카드종류 [\"0\":신용카드, \"1\":체크카드, \"2\":기프트카드]")
    @Column(name = "card_check_flag")
    private String cardCheckFlag;

    /**
     * 카드발급사(은행) 코드
     */
    @ApiModelProperty("카드발급사(은행) 코드")
    @Column(name = "card_bank_code")
    private String cardBankCode;

    /**
     * 간편(앱)결제구분
     */
    @ApiModelProperty("간편(앱)결제구분")
    @Column(name = "card_src_code")
    private String cardSrcCode;

    /**
     * 실시간계좌 은행코드
     */
    @ApiModelProperty("실시간계좌 은행코드")
    @Column(name = "acc_bank_code")
    private String accBankCode;

    /**
     * 현금영수증 발행 정상여부 ["220000": 정상]
     */
    @ApiModelProperty("현금영수증 발행 정상여부 [\"220000\": 정상]")
    @Column(name = "cshr_result_code")
    private String cshrResultCode;

    /**
     * 현금영수증구분 ["0":소득공제, "1":지출증빙]
     */
    @ApiModelProperty("현금영수증구분 [\"0\":소득공제, \"1\":지출증빙]")
    @Column(name = "cshr_type")
    private String cshrType;

    /**
     * 계좌주명
     */
    @ApiModelProperty("계좌주명")
    @Column(name = "acct_name")
    private String acctName;

    /**
     * 가상계좌번호
     */
    @ApiModelProperty("가상계좌번호")
    @Column(name = "vact_num")
    private String vactNum;

    /**
     * 가상계좌 입금은행코드
     */
    @ApiModelProperty("가상계좌 입금은행코드")
    @Column(name = "vact_bank_code")
    private String vactBankCode;

    /**
     * 가상계좌 입금은행명
     */
    @ApiModelProperty("가상계좌 입금은행명")
    @Column(name = "vact_bank_name")
    private String vactBankName;

    /**
     * 예금주명
     */
    @ApiModelProperty("예금주명")
    @Column(name = "vact_name")
    private String vactName;

    /**
     * 송금자명
     */
    @ApiModelProperty("송금자명")
    @Column(name = "vact_input_name")
    private String vactInputName;

    /**
     * 입금기한일자 [YYYYMMDD]
     */
    @ApiModelProperty("입금기한일자 [YYYYMMDD]")
    @Column(name = "vact_date")
    private String vactDate;

    /**
     * 입금기한시각 [hhmmss]
     */
    @ApiModelProperty("입금기한시각 [hhmmss]")
    @Column(name = "vact_time")
    private String vactTime;

    /**
     * 휴대폰번호(통신사결제시)
     */
    @ApiModelProperty("휴대폰번호(통신사결제시)")
    @Column(name = "hpp_num")
    private String hppNum;

    /**
     * 이름
     */
    @ApiModelProperty("휴대폰 결제장치")
    @Column(name = "pay_device")
    private String payDevice;

    /**
     * 휴대폰 빌링키(빌키발급 요청시에만 반환)
     */
    @ApiModelProperty("휴대폰 빌링키(빌키발급 요청시에만 반환)")
    @Column(name = "hpp_billkey")
    private String hppBillkey;

    /**
     * 결과코드 ["0000":성공, 이외 실패 (실패코드 6byte)]
     */
    @ApiModelProperty("결과코드 [\"0000\":성공, 이외 실패 (실패코드 6byte)]")
    @Column(name = "result_code")
    private String resultCode;

    /**
     * 결과메세지
     */
    @ApiModelProperty("결과메세지")
    @Column(name = "result_msg")
    private String resultMsg;

    @ApiModelProperty("결제실패시 상세코드")
    @Column(name = "result_detail_code")
    private String resultDetailCode;

    @ApiModelProperty("결제순번")
    @Column(name = "payment_seq")
    private Long paymentSeq;

    @ApiModelProperty("결제타입(0결제, 1취소)")
    @Column(name = "pg_payment_type")
    private Boolean pgPaymentType;

    @ApiModelProperty("상점아이디")
    @Column(name = "mid")
    private String mid;

    @ApiModelProperty("웹모바일결제구분(0웹, 1모바일)")
    @Column(name = "pay_type")
    private Boolean payType;


    public PgPaymentLog(InicisAuthResult req) {
        this.tid = req.getTid();
        this.moid = req.getMOID();
        this.price = req.getTotPrice();
        this.payMethod = req.getPayMethod();
        this.buyerName = req.getBuyerName();
        this.buyerTel = req.getBuyerTel();
        this.buyerEmail = req.getBuyerEmail();
        this.custEmail = req.getCustEmail();
        this.applNum = req.getApplNum();
        this.applDate = req.getApplDate();
        this.applTime = req.getApplTime();
        this.cardNum = req.getCARD_Num();
        this.cardInterest = req.getCARD_Interest();
        this.cardQuota = req.getCARD_Quota();
        this.cardCode = req.getCARD_Code();
        this.cardCorpFlag = req.getCARD_CorpFlag();
        this.cardCheckFlag = req.getCARD_CheckFlag();
        this.cardBankCode = req.getCARD_BankCode();
        this.cardSrcCode = req.getCARD_SrcCode();
        this.accBankCode = req.getACCT_BankCode();
        this.cshrResultCode = req.getResultCode();
        this.cshrType = req.getCSHR_Type();
        this.acctName = req.getACCT_Name();
        this.vactNum = req.getVACT_Num();
        this.vactBankCode = req.getVACT_BankCode();
        this.vactBankName = req.getVactBankName();
        this.vactName = req.getVACT_Name();
        this.vactInputName = req.getVACT_InputName();
        this.vactDate = req.getVACT_Date();
        this.vactTime = req.getVACT_Time();
        this.hppNum = req.getHPP_Num();
        this.payDevice = req.getPayDevice();
        this.hppBillkey = req.getHPP_Billkey();
        this.resultCode = req.getResultCode();
        this.resultMsg = req.getResultMsg();
        this.paymentSeq = req.getPaymentSeq();
        this.pgPaymentType = false;
        this.payType = false;
    }

    public void setCancelPgPayment(InicisCancelRes InicisReq, PaymentCancelRes paymentReq,Boolean isMobile) {
        this.tid = paymentReq.getTid();
        this.mid = paymentReq.getMid();
        this.price = paymentReq.getPrice();
        this.payMethod = paymentReq.getPayMethod();
        this.buyerName = paymentReq.getBuyerName();
        this.buyerEmail = paymentReq.getBuyerEmail();
        this.applNum = paymentReq.getApplNum();
        this.resultCode = InicisReq.getResultCode();
        this.resultMsg = InicisReq.getResultMsg();
        this.resultDetailCode = InicisReq.getDetailResultCode();
        this.paymentSeq = paymentReq.getPaymentSeq();
        this.pgPaymentType = true;
        this.payType = isMobile;

        if(StringUtils.isBlank(InicisReq.getCancelDate())) {
            this.applDate = LocalDateUtils.krNowByFormatter("yyyyMMdd");
        }else {
            this.applDate = InicisReq.getCancelDate();
        }
        if(StringUtils.isBlank(InicisReq.getCancelTime())) {
            this.applTime = LocalDateUtils.krNowByFormatter("HHmmss");
        }else {
            this.applTime = InicisReq.getCancelTime();
        }
    }

    public void setPgPaymentLog(InicisAuthMobileResult req) {
        this.tid = req.getP_TID();
        this.payMethod = req.getP_TYPE();
        this.applDate = StringUtils.substring(req.getP_AUTH_DT(), 0, 8);
        this.applTime = StringUtils.substring(req.getP_AUTH_DT(), 8);
        this.mid = req.getP_MID();
        this.moid = req.getP_OID();
        this.price = req.getP_AMT();
        this.buyerName = req.getP_UNAME();
        this.resultCode = req.getResultCode();
        this.resultMsg = req.getResultMsg();
        this.applNum = req.getP_AUTH_NO();
        this.cardNum = req.getP_CARD_NUM();
        this.cardInterest = req.getP_CARD_INTEREST();
        this.cardQuota = req.getP_RMESG2();
        this.cardCode = req.getP_FN_CD1();
        this.cardCorpFlag = req.getCARD_CorpFlag();
        this.cardCheckFlag = req.getP_CARD_CHECKFLAG();
        this.cardSrcCode = req.getP_SRC_CODE();
        this.paymentSeq = req.getPaymentSeq();
        this.pgPaymentType = false;
        this.payType = true;
    }
}
