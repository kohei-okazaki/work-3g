$(function() {

  $("#api-regist-request-modal").css({
    "cursor" : "pointer"
  });

  // modalの内容を非表示
  $("#api-regist-request-modal-content").css({
    "display" : "none"
  });

  // modalを開くイベント処理
  $("#api-regist-request-modal").on("click", function() {
    $("body").append('<div id="api-regist-request-modal-bg"></div>');
    $("#api-regist-request-modal-bg").css({
      "display" : "none",
      "width" : "100%",
      "height" : "100%",
      "background-color" : "rgba(0,0,0,0.5)",
      "position" : "fixed",
      "top" : "0",
      "left" : "0",
      "z-index" : "1"
    });

    $("body").append('<div id="api-regist-request-modal-content"><div class="contentLayout"><table class="data"><tr class="data"><th class="data" colspan="2">リクエストパラメータ<th class="data">詳細<tr class="data"><td class="data">リクエストタイプ<td class="data">requestType<td class="data">健康情報登録のリクエスト種別<br>"001"を固定とする<tr class="data"><td class="data">ユーザID<td class="data">userId<td class="data">健康情報アプリでユーザを一意に識別するID<tr class="data"><td class="data">APIキー<td class="data">apiKey<td class="data">健康情報アプリでユーザに払い出されているキー<tr class="data"><td class="data">身長<td class="data">height<td class="data">ユーザの登録したい身長<tr class="data"><td class="data">体重<td class="data">weight<td class="data">ユーザの登録したい体重<tr class="data"><td class="data">テストモード<td class="data">testMode<td class="data"><ul><li>0:DB登録を行う</li><br><li>1:DB登録を行わない</ul></table></div></div>');
    $("#api-regist-request-modal-content").css({
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
    $("#api-regist-request-modal-bg,#api-regist-request-modal-content").fadeIn("slow");

    // modal背景のどこかをクリックしたらモーダルを閉じる
    $("#api-regist-request-modal-bg").on("click", function() {
      $("#api-regist-request-modal-content,#api-regist-request-modal-bg").fadeOut("slow", function() {
        // modalを削除
        $('#api-regist-request-modal-bg').remove();
        $('#api-regist-request-modal-content').remove();
      });
    });

    // 画面の左上からmodalの横幅・高さを引き、その値を2で割り画面中央に表示
    $(window).resize(modalResize);

    function modalResize() {
      var w = $(window).width();
      var h = $(window).height();
      var cw = $("#api-regist-request-modal-content").outerWidth();
      var ch = $("#api-regist-request-modal-content").outerHeight();
      // 取得した値をcssに追加する
      $("#api-regist-request-modal-content").css({
        "left" : ((w - cw) / 2) + "px",
        "top" : ((h - ch) / 2) + "px"
      });
    }

  });
});
