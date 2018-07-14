function accountCreate() {
	var form = document.createElement('form');
	form.action = './accountRegist-input.html';
	form.method = 'get';
	document.body.appendChild(form);
	form.submit();
}

function changeValue() {

	// 入力値を取得
	var userIdTag = document.getElementById("userId");
	var userIdValue = userIdTag.value;
	var passwordTag = document.getElementById("password");
	var passwordValue = passwordTag.value;
	var confirmPasswordTag = document.getElementById("confirmPassword");
	var confirmPasswordValue = confirmPasswordTag.value;

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