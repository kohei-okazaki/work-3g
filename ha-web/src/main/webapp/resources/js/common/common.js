function finish() {
	var finishMessage = document.write('完了しました');
	alert(finishMessage);
}

function toTop() {
	var form = document.createElement('form');
	form.action = '../login/top.html';
	form.method = 'get';
	document.body.appendChild(form);
	form.submit();
}
