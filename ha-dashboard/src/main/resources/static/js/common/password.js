$(function() {
    var password = $('#password').get(0);
    var passwordCheck = $('#password-check').get(0);
    passwordCheck.addEventListener('change', function() {
      var inputType = passwordCheck.checked ? 'text' : 'password';
      password.setAttribute('type', inputType);
    }, false);

    var confirmPassword = $('#confirmPassword').get(0);
    var confirmPasswordCheck = $('#confirmPassword-check').get(0);
    confirmPasswordCheck.addEventListener('change', function() {
      var inputType = confirmPasswordCheck.checked ? 'text' : 'password';
      confirmPassword.setAttribute('type', inputType);
    }, false);
});