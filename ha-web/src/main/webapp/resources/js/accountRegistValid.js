$('form').validate({

	// 入力項目の検証ルール定義
	rules: {
		userId: {
			required: true
		},
		password: {
			required: true
		},
		confirmPassword: {
			required: true,
			equalTo: "#password"
		},
		remarks: {
			required: false
		}
	},

	// 入力項目ごとのエラーメッセージ定義
	messages: {
		userId: {
			required: "ユーザIDが未入力です"
		},
		password: {
			required: "パスワードが未入力です"
		},
		confirmPassword: {
			required: "確認用パスワードが未入力です"
		}
	},

	// エラーメッセージの表示場所を設定
	errorPlacement: function (error, element) {
		var div = document.getElementById('errorMessage');
		var p = div.firstElementChild;
		error.appendTo(p)
	}

});