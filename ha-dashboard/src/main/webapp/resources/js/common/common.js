$(function() {
  $("#appTitle").on("click", function() {
    var $form = $("<form></form>", {
      action : '../login/top',
      method : 'get'
    });
    $form.appendTo(document.body);
    $form.submit();
  });
});

$("form").submit(function() {
  var self = this;
  $(":submit", self).prop("disabled", true);
  setTimeout(function() {
    $(":submit", self).prop("disabled", false);
  }, 10000);
});

jQuery(function() {
  var appear = false;
  var pagetop = $('#page_top');
  $(window).scroll(function() {
    if ($(this).scrollTop() > 100) {
      //100pxスクロールしたら
      if (!appear) {
        appear = true;
        // 下から50pxの位置に、0.3秒かけて現れる
        pagetop.stop().animate({
          'bottom' : '50px'
        }, 300);
      }
    } else {
      if (appear) {
        appear = false;
        // 下から-50pxの位置に0.3秒かけて隠れる
        pagetop.stop().animate({
          'bottom' : '-50px'
        }, 300);
      }
    }
  });
  pagetop.click(function() {
    //0.5秒かけてトップへ戻る
    $('body, html').animate({
      scrollTop : 0
    }, 500);
    return false;
  });
});