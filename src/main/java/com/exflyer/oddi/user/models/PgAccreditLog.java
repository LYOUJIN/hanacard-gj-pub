package com.exflyer.oddi.user.models;

import com.exflyer.oddi.user.api.payment.dto.InicisMobileReqResult;
import com.exflyer.oddi.user.api.payment.dto.InicisReqResult;
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

@Data
@Entity
@ApiModel("pg결제인증결과로그")
@Table(name="pg_accredit_log")
@NoArgsConstructor
public class PgAccreditLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 순번
     */
    @Id
    @ApiModelProperty("순번")
    @Column(name = "seq", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    /**
     *결제순번
     */
    @ApiModelProperty("결제순번")
    @Column(name = "payment_seq")
    private Long paymentSeq;

    /**
     *상점아이디
     */
    @ApiModelProperty("상점아이디")
    @Column(name = "mid")
    private String mid;


    /**
     *주문번호
     */
    @ApiModelProperty("주문번호")
    @Column(name = "order_number")
    private String orderNumber;



    /**
     *승인요청url
     */
    @ApiModelProperty("승인요청url")
    @Column(name = "auth_url")
    private String authUrl;


    /**
     *망취소요청url
     */
    @ApiModelProperty("망취소요청url")
    @Column(name = "net_cancel_url")
    private String netCancelUrl;


    /**
     *SHA256 Hash값
     */
    @ApiModelProperty("SHA256 Hash값")
    @Column(name = "signature")
    private String signature;

    /**
     *타임스템프
     */
    @ApiModelProperty("타임스템프")
    @Column(name = "timestamp")
    private String timestamp;


    /**
     *인증결과 인코딩
     */
    @ApiModelProperty("인증결과 인코딩")
    @Column(name = "charset")
    private String charset;


    /**
     *결과코드 ["0000": 정상, 이외 실패]
     */
    @ApiModelProperty("결과코드 [\"0000\": 정상, 이외 실패]")
    @Column(name = "result_code")
    private String resultCode;


    /**
     *결과메시지
     */
    @ApiModelProperty("결과메시지")
    @Column(name = "result_msg")
    private String resultMsg;

    public void setPgAccreditLog(InicisReqResult req) {
        this.paymentSeq = req.getPaymentSeq();
        this.mid = req.getMid();
        this.orderNumber = req.getOrderNumber();
        this.authUrl = req.getAuthUrl();
        this.netCancelUrl = req.getNetCancelUrl();
        this.signature = req.getSignature();
        this.timestamp = req.getTimestamp();
        this.charset = req.getCharset();
        this.resultCode = req.getResultCode();
        this.resultMsg = req.getResultMsg();
    }

    public void setMobilePgAccreditLog(InicisMobileReqResult req) {
        this.paymentSeq = req.getPaymentSeq();
        this.orderNumber = req.getP_TID();
        this.authUrl = req.getP_REQ_URL();
        this.resultCode = req.getP_STATUS();
        this.resultMsg = req.getP_RMESG1();
        this.mid = req.getMid();
    }

}
