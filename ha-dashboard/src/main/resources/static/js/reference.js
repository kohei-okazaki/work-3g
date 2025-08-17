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

  function updateDateVisibility() {
    const isDirect = $("#healthInfoRegDateSelectFlagTrue").is(":checked");

    console.log("called updateDateVisibility");

    if (isDirect) {
      console.log("→ hide");

      // 「直接指定する」 → to欄と〜を非表示
      $("#toDateWrapper").hide();

    } else {
      // 「直接指定しない」 → 両方表示
      console.log("→ show");
      $("#toDateWrapper").show();
	  
    }
  }

  // 初期状態の反映
  updateDateVisibility();

  // ラジオボタン変更時にイベント発火
  $("#healthInfoRegDateSelectFlagFalse, #healthInfoRegDateSelectFlagTrue").on("change", updateDateVisibility);

});
