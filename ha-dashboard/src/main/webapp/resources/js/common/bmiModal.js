$(function() {

  $("#bmi-modal").css({
    "cursor" : "pointer"
  });

  // modalの内容を非表示
  $("#bmi-modal-content").css({
    "display" : "none"
  });

  // modalを開くイベント処理
  $("#bmi-modal").on("click", function() {
    $("body").append('<div id="bmi-modal-bg"></div>');
    $("#bmi-modal-bg").css({
      "display" : "none",
      "width" : "100%",
      "height" : "100%",
      "background-color" : "rgba(0,0,0,0.5)",
      "position" : "fixed",
      "top" : "0",
      "left" : "0",
      "z-index" : "1"
    });

    $("body").append('<div id="bmi-modal-content"><div class="modal-layout"><b>BMI</b><br> <label>計算式</label><br><div align="center" class="modal-layout formula"> <label>体重(kg) / 身長(m)の2剰</label></div><br><div align="center" class="modal-layout"><table class="data"><tr class="data"><th class="data"><label>範囲</label><th class="data"><label>肥満度</label><tr class="data"><td class="data"><label>18.5未満</label><td class="data"><label>低体重</label><tr class="data"><td class="data"><label>18.5 ~ 25未満</label><td class="data"><label>普通体重</label><tr class="data"><td class="data"><label>25 ~ 30未満</label><td class="data"><label>肥満(1)</label><tr class="data"><td class="data"><label>30 ~ 35未満</label><td class="data"><label>肥満(2)</label><tr class="data"><td class="data"><label>35 ~ 40未満</label><td class="data"><label>肥満(3)</label><tr class="data"><td class="data"><label>40以上</label><td class="data"><label>肥満(4)</label></table></div></div></div>');
    $("#bmi-modal-content").css({
      "display" : "none",
      "width" : "auto",
      "height" : "auto",
      "margin" : "0",
      "padding" : "0",
      "background-color" : "#ffffff",
      "color" : "#666666",
      "position" : "fixed",
      "z-index" : "9999"
    });
    $(".modal-layout").css({
        "margin" : "0px",
        "width" : "auto",
        "padding" : "15px",
        "background" : "#d2e8d561",
        "box-shadow" : "0 3px 4px rgba(0, 0, 0, 0.32)"
      });
      $(".formula").css({
        "width" : "510px"
      });

    // modalの位置を画面中央に計算する関数を実行
    modalResize();

    // modalウィンドウを表示
    $("#bmi-modal-bg,#bmi-modal-content").fadeIn("slow");

    // modal背景のどこかをクリックしたらモーダルを閉じる
    $("#bmi-modal-bg").on("click", function() {
      $("#bmi-modal-content,#bmi-modal-bg").fadeOut("slow", function() {
        // modalを削除
        $('#bmi-modal-bg').remove();
        $('#bmi-modal-content').remove();
      });
    });

    // 画面の左上からmodalの横幅・高さを引き、その値を2で割り画面中央に表示
    $(window).resize(modalResize);

    function modalResize() {
      var w = $(window).width();
      var h = $(window).height();
      var cw = $("#bmi-modal-content").outerWidth();
      var ch = $("#bmi-modal-content").outerHeight();
      // 取得した値をcssに追加する
      $("#bmi-modal-content").css({
        "left" : ((w - cw) / 2) + "px",
        "top" : ((h - ch) / 2) + "px"
      });
    }

  });
});
