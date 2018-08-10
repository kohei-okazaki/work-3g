(function() {
	// 標準エラーメッセージの変更
	$.extend($.validator.messages, {
		required: '*入力必須です'
	});

	// 入力項目の検証ルール定義
	var rules = {
			deleteFlag: {
				required: true
			},
			userId: {
				required: true
			},
			apiKey: {
				required: true
			},
			password: {
				required: true
			},
			mailAddress: {
				required: true
			},
			mailPassword: {
				required: true
			}
	};

	// 入力項目ごとのエラーメッセージ定義
	var messages = {
			deleteFlag: {
				required: "削除フラグが未入力です"
			},
			userId: {
				required: "ユーザIDが未入力です"
			},
			apiKey: {
				required: "APIキーが未入力です"
			},
			password: {
				required: "パスワードが未入力です"
			},
			mailAddress: {
				required: "メールアドレスが未入力です"
			},
			mailPassword: {
				required: "メールパスワードが未入力です"
			}
	};

	$(function() {
		var formId = $('form').attr('id');
		$(formId).validate({
			rules: rules,
			messages: messages,

			// エラーメッセージ出力箇所調整
			errorPlacement: function(error, element) {
				if (element.is(':radio')) {
					error.appendTo(element.parent());
				} else {
					error.insertAfter(element);
				}
			}
		});
	});

})();