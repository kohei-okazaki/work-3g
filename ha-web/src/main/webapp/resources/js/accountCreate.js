function accountCreate() {
	var form = document.createElement('form');
	form.action = '/ha-web/account-create-input.html';
	form.method = 'get';
	document.body.appendChild(form);
	form.submit();
}

