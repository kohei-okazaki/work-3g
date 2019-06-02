$("#lifeWorkMetabolism-modal").css({
  "cursor": "pointer"
});

// モーダルの内容を非表示にする
$("#lifeWorkMetabolism-modal-content").css({
  "display" : "none"
});

// モーダルを開くイベント処理
$("#lifeWorkMetabolism-modal").on("click", function() {
  $("body").append('<div id="lifeWorkMetabolism-modal-bg"></div>');
  $("#lifeWorkMetabolism-modal-bg").css({
    "display" : "none",
    "width" : "100%",
    "height" : "100%",
    "background-color" : "rgba(0,0,0,0.5)",
    "position" : "fixed",
    "top" : "0",
    "left" : "0",
    "z-index" : "1"
  });

  $("body").append('<div id="lifeWorkMetabolism-modal-content"><div class="contentLayout"><b>生活活動代謝って</b><p>日常の生活活動や運動で消費するエネルギー。</p></div></div>');
  $("#lifeWorkMetabolism-modal-content").css({
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
  $("#lifeWorkMetabolism-modal-bg,#lifeWorkMetabolism-modal-content").fadeIn("slow");

  // 背景のどこかをクリックしたらモーダルを閉じる
  $("#lifeWorkMetabolism-modal-bg").on("click", function() {
    $("#lifeWorkMetabolism-modal-content,#lifeWorkMetabolism-modal-bg").fadeOut("slow", function() {
      // modalを削除
      $('#lifeWorkMetabolism-modal-bg').remove();
      $('#lifeWorkMetabolism-modal-content').remove();
    });
  });

  // 画面の左上からmodal-mainの横幅・高さを引き、その値を2で割ると画面中央の位置が計算できます
  $(window).resize(modalResize);

  function modalResize() {
    var w = $(window).width();
    var h = $(window).height();
    var cw = $("#lifeWorkMetabolism-modal-content").outerWidth();
    var ch = $("#lifeWorkMetabolism-modal-content").outerHeight();
    // 取得した値をcssに追加する
    $("#lifeWorkMetabolism-modal-content").css({
      "left" : ((w - cw) / 2) + "px",
      "top" : ((h - ch) / 2) + "px"
    });
  }

});