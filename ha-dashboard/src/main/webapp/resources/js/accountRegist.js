/**
 * アカウントを登録する関数
 *
 * @returns
 */
function accountRegist() {
  var form = document.createElement('form');
  form.action = '../accountRegist/input';
  form.method = 'get';
  document.body.appendChild(form);
  form.submit();
}
