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

$("form").submit(function() {
  var self = this;
  $(":submit", self).prop("disabled", true);
  setTimeout(function() {
    $(":submit", self).prop("disabled", false);
  }, 10000);
});