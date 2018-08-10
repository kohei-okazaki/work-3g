function accountCreate() {
	// formを作成
	var form = document.createElement('form');
	form.action = '../accountRegist/input.html';
	form.method = 'get';
	document.body.appendChild(form);
	form.submit();
}

function changeValue() {

	// 入力値を取得
	var userIdValue = document.getElementById("userId").value;
	var passwordValue = document.getElementById("password").value;
	var confirmPasswordValue = document.getElementById("confirmPassword").value;

	// 進捗バーを取得
	var bar = document.getElementById("bar");
	var userIdVal = 0;
	var passwordVal = 0;
	var confirmPasswordVal = 0;
	if (userIdValue !== "") {
		userIdVal = 1;
	}
	if (passwordValue !== "") {
		passwordVal = 1;
	}
	if (confirmPasswordValue !== "") {
		confirmPasswordVal = 1;
	}
	bar.value = userIdVal + passwordVal + confirmPasswordVal;
}

(function() {
	// 標準エラーメッセージの変更
	$.extend($.validator.messages, {
		required: '*入力必須です'
	});

	// 追加ルールの定義
	var methods = {
			phone: function(value, element){
				return this.optional(element) || /^\d{11}$|^\d{3}-\d{4}-\d{4}$/.test(value);
			}
	};

	// メソッドの追加
	$.each(methods, function(key) {
		$.validator.addMethod(key, this);
	});

	// 入力項目の検証ルール定義
	var rules = {
			userId: {
				required: true
			},
			password: {
				required: true
			},
			confirmPassword: {
				required: true,
				equalTo: "#password"
			}
	};

	// 入力項目ごとのエラーメッセージ定義
	var messages = {
			userId: {
				required: "ユーザIDが未入力です"
			},
			password: {
				required: "パスワードが未入力です"
			},
			confirmPassword: {
				required: "確認用パスワードが未入力です"
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