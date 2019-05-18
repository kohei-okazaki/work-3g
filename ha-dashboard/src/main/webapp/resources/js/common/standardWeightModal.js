$(function() {

  $("#standardWeight-modal").css({
    "cursor" : "pointer"
  });

  // modalの内容を非表示
  $("#standardWeight-modal-content").css({
    "display" : "none"
  });

  // modalを開くイベント処理
  $("#standardWeight-modal").on("click", function() {
    $("body").append('<div id="standardWeight-modal-bg"></div>');
    $("#standardWeight-modal-bg").css({
      "display" : "none",
      "width" : "100%",
      "height" : "100%",
      "background-color" : "rgba(0,0,0,0.5)",
      "position" : "fixed",
      "top" : "0",
      "left" : "0",
      "z-index" : "1"
    });

    $("body").append('<div id="standardWeight-modal-content"><div class="contentLayout"><b>標準体重</b><br><div align="center" class="contentLayout"><label>身長(m) × 身長(m) × 22</label></div><br><label>で計算されます。</label><br><div align="center" class="contentLayout"><label>165cmの人の場合、1.65 × 1.65 × 22 = 59.895となります。</label></div></div></div>');
    $("#standardWeight-modal-content").css({
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
    $("#standardWeight-modal-bg,#standardWeight-modal-content").fadeIn("slow");

    // modal背景のどこかをクリックしたらモーダルを閉じる
    $("#standardWeight-modal-bg").on("click", function() {
      $("#standardWeight-modal-content,#standardWeight-modal-bg").fadeOut("slow", function() {
        // modalを削除
        $('#standardWeight-modal-bg').remove();
        $('#standardWeight-modal-content').remove();
      });
    });

    // 画面の左上からmodalの横幅・高さを引き、その値を2で割り画面中央に表示
    $(window).resize(modalResize);

    function modalResize() {
      var w = $(window).width();
      var h = $(window).height();
      var cw = $("#standardWeight-modal-content").outerWidth();
      var ch = $("#standardWeight-modal-content").outerHeight();
      //取得した値をcssに追加する
      $("#standardWeight-modal-content").css({
        "left" : ((w - cw) / 2) + "px",
        "top" : ((h - ch) / 2) + "px"
      });
    }

  });
});
