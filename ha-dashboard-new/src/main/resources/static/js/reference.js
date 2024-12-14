$(function() {
  $("#excel-download").on("click", function() {
    // excelダウンロードを行う
    var form = $("<form></form>", {
      method : "get",
      action : "../healthinforeference/exceldownload"
    });
    form.appendTo(document.body);
    form.submit();
  });

  $("#csv-download").on("click", function() {
    // excelダウンロードを行う
    var form = $("<form></form>", {
      method : "get",
      action : "../healthinforeference/csvdownload"
    });
    form.appendTo(document.body);
    form.submit();
  });

  $("#healthInfoRegDateSelectFlagFalse").on("click", function() {

    var classList = document.getElementsByClassName('hideCalendar');
    for (var i = 0; i < classList.length; i++) {
      classList[i].style.display = "";
    }
  });

  $("#healthInfoRegDateSelectFlagTrue").on("click", function() {
    var classList = document.getElementsByClassName('hideCalendar');
    for (var i = 0; i < classList.length; i++) {
      classList[i].style.display = "none";
    }
  });

});
