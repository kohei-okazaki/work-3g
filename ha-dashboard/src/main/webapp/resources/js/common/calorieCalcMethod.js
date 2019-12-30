$(function() {
  $("#calorieCalcMethod-modal").css({
    "cursor": "pointer"
  });

  // モーダルの内容を非表示にする
  $("#calorieCalcMethod-modal-content").css({
    "display" : "none"
  });

  // モーダルを開くイベント処理
  $("#calorieCalcMethod-modal").on("click", function() {
    $("body").append('<div id="calorieCalcMethod-modal-bg"></div>');
    $("#calorieCalcMethod-modal-bg").css({
      "display" : "none",
      "width" : "100%",
      "height" : "100%",
      "background-color" : "rgba(0,0,0,0.5)",
      "position" : "fixed",
      "top" : "0",
      "left" : "0",
      "z-index" : "1"
    });

    $("body").append('<div id="calorieCalcMethod-modal-content"><div class="contentLayout"><b>カロリー計算の方法</b> <p>以下の2種類の合計が1日に消費するカロリーです<ul><li>生活活動代謝(kcal)</li><br><li>基礎代謝量(kcal)</ul></div></div>');
    $("#calorieCalcMethod-modal-content").css({
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

    // 画面中央を計算する関数を実行
    modalResize();

    // モーダルウィンドウを表示
    $("#calorieCalcMethod-modal-bg,#calorieCalcMethod-modal-content").fadeIn("slow");

    // 背景のどこかをクリックしたらモーダルを閉じる
    $("#calorieCalcMethod-modal-bg").on("click", function() {
      $("#calorieCalcMethod-modal-content,#calorieCalcMethod-modal-bg").fadeOut("slow", function() {
        // modalを削除
        $('#calorieCalcMethod-modal-bg').remove();
        $('#calorieCalcMethod-modal-content').remove();
      });
    });

    // 画面の左上からmodal-mainの横幅・高さを引き、その値を2で割ると画面中央の位置が計算できます
    $(window).resize(modalResize);

    function modalResize() {
      var w = $(window).width();
      var h = $(window).height();
      var cw = $("#calorieCalcMethod-modal-content").outerWidth();
      var ch = $("#calorieCalcMethod-modal-content").outerHeight();
      // 取得した値をcssに追加する
      $("#calorieCalcMethod-modal-content").css({
        "left" : ((w - cw) / 2) + "px",
        "top" : ((h - ch) / 2) + "px"
      });
    }

  });

});