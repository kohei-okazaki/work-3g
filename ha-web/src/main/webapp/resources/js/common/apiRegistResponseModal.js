$(function() {

	// モーダルウィンドウを出現させるクリックイベント
	$("#api-regist-response-modal-open").click(function() {

		// キーボード操作などにより、オーバーレイが多重起動するのを防止する
		$(this).blur();	// ボタンからフォーカスを外す
		if ($("#api-regist-response-modal-overlay")[0]) {
			return false;
		}
		// if($("#modal-overlay")[0]) $("#modal-overlay").remove() ;
		// //現在のモーダルウィンドウを削除して新しく起動する (防止策2)

		// オーバーレイを出現させる
		$("body").append('<div id="api-regist-response-modal-overlay"></div>');
		$("#api-regist-response-modal-overlay").fadeIn("slow");

		// コンテンツをセンタリングする
		centeringModalSyncer();

		// コンテンツをフェードインする
		$("#api-regist-response-modal-content").fadeIn("slow");

		// [#modal-overlay]、または[#modal-close]をクリックしたら…
		$("#api-regist-response-modal-overlay,#api-regist-response-modal-close").unbind().click(function() {

			// [#modal-content]と[#modal-overlay]をフェードアウトした後に…
			$("#api-regist-response-modal-content,#api-regist-response-modal-overlay").fadeOut("slow", function() {

				// [#modal-overlay]を削除する
				$('#api-regist-response-modal-overlay').remove();

			});

		});

	});

	// リサイズされたら、センタリングをする関数[centeringModalSyncer()]を実行する
	$(window).resize(centeringModalSyncer);

	// センタリングを実行する関数
	function centeringModalSyncer() {

		// 画面(ウィンドウ)の幅、高さを取得
		var w = $(window).width();
		var h = $(window).height();

		// コンテンツ(#modal-content)の幅、高さを取得
		// jQueryのバージョンによっては、引数[{margin:true}]を指定した時、不具合を起こします。
		var cw = $("#api-regist-response-modal-content").outerWidth();
		var ch = $("#api-regist-response-modal-content").outerHeight();

		// センタリングを実行する
		$("#api-regist-response-modal-content").css({"left": ((w - cw) / 2) + "px","top": ((h - ch) / 2) + "px"});

	}

});
