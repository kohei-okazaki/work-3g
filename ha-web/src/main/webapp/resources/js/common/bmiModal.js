$(function() {

// モーダルウィンドウを出現させるクリックイベント
	$("#bmi-modal-open").click(function() {

		// キーボード操作などにより、オーバーレイが多重起動するのを防止する
		$(this).blur();	// ボタンからフォーカスを外す
		if ($("#bmi-modal-overlay")[0]) {
			// 新しくモーダルウィンドウを起動しない
			return false;
		}

		// オーバーレイを出現させる
		$("body").append('<div id="bmi-modal-overlay"></div>');
		$("#bmi-modal-overlay").fadeIn("slow");

		// コンテンツをセンタリングする
		centeringModalSyncer();

		// コンテンツをフェードインする
		$("#bmi-modal-content").fadeIn("slow");

		// [#bmi-modal-overlay]、または[#bmi-modal-close]をクリックしたら…
		$("#bmi-modal-overlay,#bmi-modal-close").unbind().click(function() {

			// [#bmi-modal-content]と[#bmi-modal-overlay]をフェードアウトした後に…
			$("#bmi-modal-content,#bmi-modal-overlay").fadeOut("slow", function() {

				// [#modal-overlay]を削除する
				$('#bmi-modal-overlay').remove();

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
		var cw = $("#bmi-modal-content").outerWidth();
		var ch = $("#bmi-modal-content").outerHeight();

		// センタリングを実行する
		$("#bmi-modal-content").css({"left": ((w - cw)/2) + "px","top": ((h - ch)/2) + "px"});

	}

});
