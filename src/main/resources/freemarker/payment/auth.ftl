<script type="text/javascript" src="https://stdpay.inicis.com/stdjs/INIStdPay.js" charset="UTF-8"></script>

<form name="" id="form" method="post">
  <input type="hidden" name="goodname" value="${goodname}">
  <input type="hidden" name="buyername" value="${buyername}">
  <input type="hidden" name="buyertel" value="${buyertel}">
  <input type="hidden" name="buyeremail" value="${buyeremail}">
  <input type="hidden" name="price" value="${price?c}">
  <input type="hidden" name="mid" value="${mid}">
  <input type="hidden" name="gopaymethod" value="${gopaymethod}">
  <input type="hidden" name="quotabase" value="${quotabase}">
  <input type="hidden" name="mKey" value="${mKey}">
  <input type="hidden" name="signature" value="${signature}">
  <input type="hidden" name="oid" value="${oid}">
  <input type="hidden" name="timestamp" value="${timestamp?c}">
  <input type="hidden" name="version" value="${version}">
  <input type="hidden" name="currency" value="${currency}">
  <input type="hidden" name="acceptmethod" value="${acceptmethod}" >
  <input type="hidden" name="returnUrl" value="${returnUrl}">
  <input type="hidden" name="closeUrl" value="${closeUrl}">
</form>

<script type="text/javascript">
  window.onload = () => {
    INIStdPay.pay('form');
  }
</script>