<script type="text/javascript">
  (function(){
    var resultCode = '${result.resultCode}';
    var resultMsg  = '${result.resultMsg}';

    if ('00' != resultCode && resultMsg) {
      alert(resultMsg);
    }

    window.parent.close();
  }());
</script>