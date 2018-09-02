(function() {
	// 標準エラーメッセージの変更
	$.extend($.validator.messages, {
		required : '*入力必須です'
	});

	// 入力項目の検証ルール定義
	var rules = {
		regDateSelectFlag : {
			required : true
		},
		fromRegDate : {
			required : true
		}
	};

	// 入力項目ごとのエラーメッセージ定義
	var messages = {
		regDateSelectFlag : {
			required : "登録日直接利用フラグが未入力です"
		},
		fromRegDate : {
			required : "登録日が未入力です"
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