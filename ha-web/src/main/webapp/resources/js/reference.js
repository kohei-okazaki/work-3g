/**
 * Excelをダウンロードする関数
 *
 * @returns
 */
function referenceExcelDownload() {
  var form = document.createElement('form');
  form.action = '../healthInfoReference/excelDownload.html';
  form.method = 'get';
  document.body.appendChild(form);
  form.submit();
}

/**
 * CSVをダウンロードする関数
 *
 * @returns
 */
function referenceCsvDownload() {
  var form = document.createElement('form');
  form.action = '../healthInfoReference/csvDownload.html';
  form.method = 'get';
  document.body.appendChild(form);
  form.submit();
}

/**
 * カレンダーを隠す関数
 *
 * @returns
 */
function hideCalendar() {
  var classList = document.getElementsByClassName('hideCalendar');
  for (var i = 0; i < classList.length; i++) {
    classList[i].style.display = "none";
  }
}

/**
 * カレンダーを表示させる関数
 *
 * @returns
 */
function showCalendar() {
  var classList = document.getElementsByClassName('hideCalendar');
  for (var i = 0; i < classList.length; i++) {
    classList[i].style.display = "";
  }
}
