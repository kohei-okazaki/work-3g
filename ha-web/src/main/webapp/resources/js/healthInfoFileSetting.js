(function() {
	// 標準エラーメッセージの変更
	$.extend($.validator.messages, {
		required : '*入力必須です'
	});

	// 入力項目の検証ルール定義
	var rules = {
		userId : {
			required : true
		},
		headerFlag : {
			required : true
		},
		footerFlag : {
			required : true
		},
		maskFlag : {
			required : true
		},
		enclosureCharFlag : {
			required : true
		}
	};

	// 入力項目ごとのエラーメッセージ定義
	var messages = {
		userId : {
			required : "ユーザIDが未入力です"
		},
		headerFlag : {
			required : "ヘッダ利用フラグが未入力です"
		},
		footerFlag : {
			required : "フッタ利用フラグが未入力です"
		},
		maskFlag : {
			required : "マスク利用フラグが未入力です"
		},
		enclosureCharFlag : {
			required : "ファイル囲み文字利用フラグが未入力です"
		}
	};

	$(function() {
		var formId = $('form').attr('id');
		$(formId).validate({
			rules : rules,
			messages : messages,

			// エラーメッセージ出力箇所調整
			errorPlacement : function(error, element) {
				if (element.is(':radio')) {
					error.appendTo(element.parent());
				} else {
					error.insertAfter(element);
				}
			}
		});
	});

})();