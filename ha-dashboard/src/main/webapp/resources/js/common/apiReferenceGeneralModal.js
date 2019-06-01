$(function() {

  $("#api-reference-general-modal").css({
    "cursor" : "pointer"
  });

  // modalの内容を非表示
  $("#api-reference-general-modal-content").css({
    "display" : "none"
  });

  // modalを開くイベント処理
  $("#api-reference-general-modal").on("click", function() {
    $("body").append('<div id="api-reference-general-modal-bg"></div>');
    $("#api-reference-general-modal-bg").css({
      "display" : "none",
      "width" : "100%",
      "height" : "100%",
      "background-color" : "rgba(0,0,0,0.5)",
      "position" : "fixed",
      "top" : "0",
      "left" : "0",
      "z-index" : "1"
    });

    $("body").append('<div id="api-reference-general-modal-content"><div class="contentLayout"><b>API 健康情報照会</b><br><br><div class="contentLayout"><label>jsonにより指定されたデータから、ユーザの登録された健康情報の照会を行う</label><br><label>リクエストURL:/ha-api/healthInfoReference</label></div></div></div>');
    $("#api-reference-general-modal-content").css({
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

    // modalの位置を画面中央に計算する関数を実行
    modalResize();

    // modalウィンドウを表示
    $("#api-reference-general-modal-bg,#api-reference-general-modal-content").fadeIn("slow");

    // modal背景のどこかをクリックしたらモーダルを閉じる
    $("#api-reference-general-modal-bg").on("click", function() {
      $("#api-reference-general-modal-content,#api-reference-general-modal-bg").fadeOut("slow", function() {
        // modalを削除
        $('#api-reference-general-modal-bg').remove();
        $('#api-reference-general-modal-content').remove();
      });
    });

    // 画面の左上からmodalの横幅・高さを引き、その値を2で割り画面中央に表示
    $(window).resize(modalResize);

    function modalResize() {
      var w = $(window).width();
      var h = $(window).height();
      var cw = $("#api-reference-general-modal-content").outerWidth();
      var ch = $("#api-reference-general-modal-content").outerHeight();
      // 取得した値をcssに追加する
      $("#api-reference-general-modal-content").css({
        "left" : ((w - cw) / 2) + "px",
        "top" : ((h - ch) / 2) + "px"
      });
    }

  });
});
