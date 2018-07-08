function accountCreate() {
	var form = document.createElement('form');
	form.action = './accountRegist-input.html';
	form.method = 'get';
	document.body.appendChild(form);
	form.submit();
}

