function changeValue() {

	// form情報を取得
	var heightValue = document.getElementById("height").value;
	var weightValue = document.getElementById("weight").value;

	// 進捗バーを取得
	var bar = document.getElementById("bar");
	var heightVal = 0;
	var weightVal = 0;
	var confirmPasswordVal = 0;
	if (heightValue !== "") {
		heightVal = 1;
	}
	if (weightValue !== "") {
		weightVal = 1;
	}

	bar.value = heightVal + weightVal;
}

(function() {
	// 標準エラーメッセージの変更
	$.extend($.validator.messages, {
		required: '*入力必須です'
	});

	// 入力項目の検証ルール定義
	var rules = {
			height: {
				required: true
			},
			weight: {
				required: true
			}
	};

	// 入力項目ごとのエラーメッセージ定義
	var messages = {
			height: {
				required: "削除フラグが未入力です"
			},
			weight: {
				required: "ユーザIDが未入力です"
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