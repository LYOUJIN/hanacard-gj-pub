<!-- 인코딩 euc-kr 필수 -->
<form name="mobileweb" method="post" accept-charset="euc-kr">

<!--*************************필수 세팅 부분************************************-->
  <!-- 리턴받는 가맹점 URL 세팅 -->
  <input type="hidden" name="P_NEXT_URL" value="${returnUrl}">
  <!-- 지불수단 선택 (신용카드,계좌이체,가상계좌,휴대폰) -->
  <input type="hidden" name="P_INI_PAYMENT" value="${gopaymethod}">
  <!-- 복합/옵션 파라미터 -->
  <input type="hidden" name="P_RESERVED" value="twotrs_isp=Y&block_isp=Y&twotrs_isp_noti=N"> <!-- 에스크로옵션 : useescrow=Y -->
  <input type="hidden" name="P_MID" value="${mid}"> <!-- 에스크로테스트 : iniescrow0, 모바일빌링(정기과금)은 별도연동필요 -->
  <input type="hidden" name="P_OID" value="${oid}">
  <input type="hidden" name="P_GOODS" value="${goodname}">
  <input type="hidden" name="P_AMT" value="${price?c}">
  <input type="hidden" name="P_UNAME" value="${buyername}">
  <!--*************************선택 필수 세팅 부분************************************-->
  <!-- 가상계좌 입금 노티 사용시 필수 -->
  <input type="hidden" name="P_NOTI_URL" value="">
  <!-- 휴대폰결제 필수 [1:컨텐츠, 2:실물] -->
  <input type="hidden" name="P_HPP_METHOD" value="1">
</form>

<script type="text/javascript">
  window.onload = () => {
    myform = document.mobileweb;
    myform.action = "https://mobile.inicis.com/smart/payment/";
    myform.target = "_self";
    myform.submit();
  }
</script>