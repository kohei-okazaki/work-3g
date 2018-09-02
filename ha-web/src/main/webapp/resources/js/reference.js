// Excelをダウンロードする関数
function referenceExcelDownload() {
	var form = document.createElement('form');
	form.action = '../healthInfoReference/excelDownload.html';
	form.method = 'get';
	document.body.appendChild(form);
	form.submit();
}

// CSVをダウンロードする関数
function referenceCsvDownload() {
	var form = document.createElement('form');
	form.action = '../healthInfoReference/csvDownload.html';
	form.method = 'get';
	document.body.appendChild(form);
	form.submit();
}

// カレンダーを隠す関数
function hideCalendar() {
	var classList = document.getElementsByClassName('hideCalendar');
	for (var i = 0; i < classList.length; i++) {
		classList[i].style.display = "none";
	}
}

// カレンダーを表示させる関数
function showCalendar() {
	var classList = document.getElementsByClassName('hideCalendar');
	for (var i = 0; i < classList.length; i++) {
		classList[i].style.display = "";
	}
}

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