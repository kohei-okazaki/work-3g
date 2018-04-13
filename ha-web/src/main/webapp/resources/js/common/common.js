function finish() {
	var finishMessage = document.write('完了しました');
	alert(finishMessage);
}

function toMenu() {
	var form = document.createElement('form');
	form.action = '/ha-web/menu.html';
	form.method = 'get';
	document.body.appendChild(form);
	form.submit();
}
