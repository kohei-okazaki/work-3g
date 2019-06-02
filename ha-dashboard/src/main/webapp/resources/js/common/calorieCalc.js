$("#calorie-modal").css({
  "cursor": "pointer"
});

// モーダルの内容を非表示にする
$("#calorie-modal-content").css({
  "display" : "none"
});

// モーダルを開くイベント処理
$("#calorie-modal").on("click", function() {
  $("body").append('<div id="calorie-modal-bg"></div>');
  $("#calorie-modal-bg").css({
    "display" : "none",
    "width" : "100%",
    "height" : "100%",
    "background-color" : "rgba(0,0,0,0.5)",
    "position" : "fixed",
    "top" : "0",
    "left" : "0",
    "z-index" : "1"
  });

  $("body").append('<div id="calorie-modal-content"><div class="contentLayout"><b>生活活動代謝って</b><p>日常の生活活動や運動で消費するエネルギー。</p></div></div>');
  $("#calorie-modal-content").css({
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
  $("#calorie-modal-bg,#calorie-modal-content").fadeIn("slow");

  // 背景のどこかをクリックしたらモーダルを閉じる
  $("#calorie-modal-bg").on("click", function() {
    $("#calorie-modal-content,#calorie-modal-bg").fadeOut("slow", function() {
      // modalを削除
      $('#calorie-modal-bg').remove();
      $('#calorie-modal-content').remove();
    });
  });

  // 画面の左上からmodal-mainの横幅・高さを引き、その値を2で割ると画面中央の位置が計算できます
  $(window).resize(modalResize);

  function modalResize() {
    var w = $(window).width();
    var h = $(window).height();
    var cw = $("#calorie-modal-content").outerWidth();
    var ch = $("#calorie-modal-content").outerHeight();
    // 取得した値をcssに追加する
    $("#calorie-modal-content").css({
      "left" : ((w - cw) / 2) + "px",
      "top" : ((h - ch) / 2) + "px"
    });
  }

});