function healthInfoCsvDownload() {
	var form = document.createElement('form');
	form.action = '/isol-web/healthInfo-csvDownload';
	form.method = 'get';
	document.body.appendChild(form);
	form.submit();
}

