function referenceExcelDownload() {
	var form = document.createElement('form');
	form.action = '/ha-web/result-reference-excelDownload.html';
	form.method = 'get';
	document.body.appendChild(form);
	form.submit();
}
function referenceCsvDownload() {
	var form = document.createElement('form');
	form.action = '/ha-web/result-reference-csvDownload';
	form.method = 'get';
	document.body.appendChild(form);
	form.submit();
}

// カレンダーを隠すかどうかの判定
function hide() {

	var classList = document.getElementsByClassName('hide');
	for (var i = 0; i < classList.length; i++) {
		classList[i].style.display = "none";
	}
}

function show() {
	var classList = document.getElementsByClassName('hide');
	for (var i = 0; i < classList.length; i++) {
		classList[i].style.display = "block";
	}
}