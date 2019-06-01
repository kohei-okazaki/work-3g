$(function() {

  $("#service-modal").css({
    "cursor": "pointer"
  });

  // modalの内容を非表示
  $("#service-modal-content").css({
    "display" : "none"
  });

  // modalを開くイベント処理
  $("#service-modal").on("click", function() {
    $("body").append('<div id="service-modal-bg"></div>');
    $("#service-modal-bg").css({
      "display" : "none",
      "width" : "100%",
      "height" : "100%",
      "background-color" : "rgba(0,0,0,0.5)",
      "position" : "fixed",
      "top" : "0",
      "left" : "0",
      "z-index" : "1"
    });

    $("body").append('<div id="service-modal-content"><div class="contentLayout"><b>利用規約</b><br><br><label>計算方法は以下を参照下さい</label><br><label>その他 -> 健康情報 -> BMI, 標準体重</label></div></div>');
    $("#service-modal-content").css({
      "display" : "none",
      "width" : "500px",
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
    $("#service-modal-bg,#service-modal-content").fadeIn("slow");

    // modal背景のどこかをクリックしたらモーダルを閉じる
    $("#service-modal-bg").on("click", function() {
      $("#service-modal-content,#service-modal-bg").fadeOut("slow", function() {
        // modalを削除
        $('#service-modal-bg').remove();
        $('#service-modal-content').remove();
      });
    });

    // 画面の左上からmodalの横幅・高さを引き、その値を2で割り画面中央に表示
    $(window).resize(modalResize);

    function modalResize() {
      var w = $(window).width();
      var h = $(window).height();
      var cw = $("#service-modal-content").outerWidth();
      var ch = $("#service-modal-content").outerHeight();
      // 取得した値をcssに追加する
      $("#service-modal-content").css({
        "left" : ((w - cw) / 2) + "px",
        "top" : ((h - ch) / 2) + "px"
      });
    }

  });
});
