function changeValue() {

  // form情報を取得
  var heightValue = document.getElementById("height").value;
  var weightValue = document.getElementById("weight").value;

  // 進捗バーを取得
  var bar = document.getElementById("bar");
  var heightVal = 0;
  var weightVal = 0;
  var confirmPasswordVal = 0;
  if (heightValue !== "") {
    heightVal = 1;
  }
  if (weightValue !== "") {
    weightVal = 1;
  }

  bar.value = heightVal + weightVal;
}
