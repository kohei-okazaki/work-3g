$(function() {

  $("#api-regist-response-modal").css({
    "cursor" : "pointer"
  });

  // modalの内容を非表示
  $("#api-regist-response-modal-content").css({
    "display" : "none"
  });

  // modalを開くイベント処理
  $("#api-regist-response-modal").on("click", function() {
    $("body").append('<div id="api-regist-response-modal-bg"></div>');
    $("#api-regist-response-modal-bg").css({
      "display" : "none",
      "width" : "100%",
      "height" : "100%",
      "background-color" : "rgba(0,0,0,0.5)",
      "position" : "fixed",
      "top" : "0",
      "left" : "0",
      "z-index" : "1"
    });

    $("body").append('<div id="api-regist-response-modal-content"><div class="contentLayout"><table class="data"><tr class="data"><th class="data" colspan="2">レスポンスパラメータ</th><th class="data">詳細</th></tr><tr class="data"><td class="data">結果区分</td><td class="data">result</td><td class="data">API実施時の結果区分</td></tr><tr class="data"><td class="data">健康情報ID</td><td class="data">healthInfoId</td><td class="data">健康情報を登録したときに採番される健康情報を一意に識別するID</td></tr><tr class="data"><td class="data">ユーザID</td><td class="data">userId</td><td class="data">健康情報アプリでユーザを一意に識別するID</td></tr><tr class="data"><td class="data">身長</td><td class="data">height</td><td class="data">健康情報IDに紐づく健康情報に登録された身長(入力値)</td></tr><tr class="data"><td class="data">体重</td><td class="data">weight</td><td class="data">健康情報IDに紐づく健康情報に登録された体重(入力値)</td></tr><tr class="data"><td class="data">BMI</td><td class="data">bmi</td><td class="data">健康情報IDに紐づく健康情報に登録されたBMI</td></tr><tr class="data"><td class="data">標準体重</td><td class="data">standardWeight</td><td class="data">健康情報IDに紐づく健康情報に登録された標準体重</td></tr><tr class="data"><td class="data">健康情報ステータス</td><td class="data">healthInfoStatus</td><td class="data">前回登録された健康情報と比較し <br><ul><li>減少した場合:10</li><li>変化なしの場合:20</li><li>増加した場合:30</li></ul></td></tr><tr class="data"><td class="data">健康情報作成日時</td><td class="data">healthInfoRegDate</td><td class="data">健康情報IDに紐づく健康情報に登録された登録日時</td></tr></table></div></div>');
    $("#api-regist-response-modal-content").css({
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
    $("#api-regist-response-modal-bg,#api-regist-response-modal-content").fadeIn("slow");

    // modal背景のどこかをクリックしたらモーダルを閉じる
    $("#api-regist-response-modal-bg").on("click", function() {
      $("#api-regist-response-modal-content,#api-regist-response-modal-bg").fadeOut("slow", function() {
        // modalを削除
        $('#api-regist-response-modal-bg').remove();
        $('#api-regist-response-modal-content').remove();
      });
    });

    // 画面の左上からmodalの横幅・高さを引き、その値を2で割り画面中央に表示
    $(window).resize(modalResize);

    function modalResize() {
      var w = $(window).width();
      var h = $(window).height();
      var cw = $("#api-regist-response-modal-content").outerWidth();
      var ch = $("#api-regist-response-modal-content").outerHeight();
      //取得した値をcssに追加する
      $("#api-regist-response-modal-content").css({
        "left" : ((w - cw) / 2) + "px",
        "top" : ((h - ch) / 2) + "px"
      });
    }

  });
});
