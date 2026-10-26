// DatePicker の Date を、入力欄と既存 API 処理が使う日付文字列へ戻す。
export function formatDateInput(value) {
  if (value == null || value === "") {
    return "";
  }

  if (!(value instanceof Date)) {
    return value;
  }

  if (Number.isNaN(value.getTime())) {
    return "";
  }

  // UTC へ変換するとタイムゾーンにより選択日がずれるため、ローカル日付を使う。
  const year = String(value.getFullYear()).padStart(4, "0");
  const month = String(value.getMonth() + 1).padStart(2, "0");
  const day = String(value.getDate()).padStart(2, "0");
  return `${year}-${month}-${day}`;
}
