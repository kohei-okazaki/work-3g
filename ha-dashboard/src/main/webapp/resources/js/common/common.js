$(function() {
  $("#appTitle").on("click", function() {
    var $form = $("<form></form>", {
        action : '../login/top',
        method : 'get'
      });
    $form.appendTo(document.body);
    $form.submit();
  });
});

$("form").submit(function() {
  var self = this;
  $(":submit", self).prop("disabled", true);
  setTimeout(function() {
    $(":submit", self).prop("disabled", false);
  }, 10000);
});
