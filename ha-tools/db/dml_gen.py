import math
from collections.abc import Sequence
from datetime import date, datetime, time
from decimal import Decimal
from pathlib import Path

import const
import utils

"""
DMLを自動生成するモジュール
"""


def execute():
    """DB 定義書のデータシートから DML ファイルを生成する。"""
    for sheet_name in utils.get_sheet_names(excel_path=const.EXCEL_PATH):
        if sheet_name == const.DDL_SHEET_NAME or sheet_name == "TEMPLATE":
            # テーブル定義、テンプレートシートはスキップ
            continue
        row_list = read_data(sheet_name=sheet_name)
        generate(sheet_name, row_list)


def read_data(sheet_name: str) -> list[tuple[object, ...]]:
    """指定されたシートからヘッダーを含む DML データを読み込む。

    Args:
        sheet_name: DB 定義書内のデータシート名。

    Returns:
        各行のセル値をタプルとして格納したリスト。空行は含まない。

    Raises:
        ValueError: 指定されたシートが存在しない場合。
    """

    # 行情報リストを取得
    return utils.read_rows(
        excel_path=const.EXCEL_PATH,
        sheet_name=sheet_name,
    )


def _mysql_literal(value: object) -> str:
    """Excel のセル値を MySQL の値式へ変換する。"""
    if value is None:
        return "NULL"
    if isinstance(value, bool):
        return "TRUE" if value else "FALSE"
    if isinstance(value, int):
        return str(value)
    if isinstance(value, (float, Decimal)):
        finite = value.is_finite() if isinstance(value, Decimal) else math.isfinite(value)
        if not finite:
            raise ValueError("Non-finite numeric cell values cannot be written to SQL")
        return str(value)
    if isinstance(value, (datetime, time)):
        if value.utcoffset() is not None:
            raise ValueError("Timezone-aware cell values cannot be written to SQL")
        text = value.isoformat(sep=" ") if isinstance(value, datetime) else value.isoformat()
        return "'{}'".format(text)
    if isinstance(value, date):
        return "'{}'".format(value.isoformat())
    if isinstance(value, str):
        # 既存の定義書で使われている文字列の真偽値表現も維持する。
        if value in ("True", "False"):
            return value.upper()
        try:
            encoded = value.encode("utf-8")
        except UnicodeEncodeError as error:
            raise ValueError("Text cell values must be valid UTF-8") from error
        if "\\" in value or any(ord(char) < 32 or 127 <= ord(char) <= 159 for char in value):
            # バックスラッシュの解釈は sql_mode に依存するため、制御文字とともに
            # UTF-8 の16進表現を使う。NO_BACKSLASH_ESCAPES の有無で値が変わらない。
            # https://dev.mysql.com/doc/refman/8.4/en/hexadecimal-literals.html
            return "CONVERT(X'{}' USING utf8mb4)".format(encoded.hex())
        # https://dev.mysql.com/doc/refman/8.4/en/string-literals.html
        return "'{}'".format(value.replace("'", "''"))
    raise TypeError("Unsupported Excel cell type: {}".format(type(value).__name__))


def generate(sheet_name: str, row_list: Sequence[Sequence[object]]):
    """行データから DML を生成し、設定された出力先へ保存する。

    シート名を大文字にした SQL ファイル名を使用し、同名の既存ファイルは
    UTF-8 で上書きする。

    Args:
        sheet_name: 挿入先のテーブル名として使用するシート名。
        row_list: 先頭行をカラム名、残りの行を挿入データとする行データ。

    Raises:
        TypeError: 未対応の型のセルが存在する場合。既存 SQL は上書きしない。
        ValueError: 非有限の数値やタイムゾーン付き日時が存在する場合。
    """

    # テンプレートファイルを取得
    template = utils.get_template(const.DML_TEMPLATE_FILE)

    # 出力先ディレクトリ作成
    output_path = Path(const.DML_OUTPUT_PATH)
    output_path.mkdir(exist_ok=True)

    # ヘッダー情報
    header: str = get_header(row_list)

    # データ情報リスト
    # ヘッダー行を除外
    data_row_list = row_list[1:]
    edit_row_list: list[str] = []

    for row_number, column_list in enumerate(data_row_list, start=2):
        literals: list[str] = []
        for column_number, column in enumerate(column_list, start=1):
            try:
                literals.append(_mysql_literal(column))
            except (TypeError, ValueError) as error:
                raise type(error)(
                    "{} row {}, column {}: {}".format(
                        sheet_name, row_number, column_number, error
                    )
                ) from error
        edit_row_list.append(",".join(literals))

    # DML SQL生成（全セルの検証が済んでから既存ファイルを上書きする）
    dml_sql = template.render(
        table_name=sheet_name, header=header, row_list=edit_row_list
    )

    dml_file_name = "{}.sql".format(sheet_name.upper())
    (output_path / dml_file_name).write_text(dml_sql, encoding="utf-8")


def get_header(row_list: Sequence[Sequence[object]]) -> str:
    """行データの先頭行からカンマ区切りのカラム名を取得する。

    Args:
        row_list: 先頭行にカラム名を格納した行データ。

    Returns:
        カンマ区切りのカラム名。行データが空の場合は空文字列。
    """
    if not row_list:
        return ""

    # 行情報リストの最初の行をヘッダとして取得
    row = row_list[0]
    # ヘッダ情報をカンマ区切りで連結
    header: str = ""
    # 最後のインデックス
    is_last_index = len(row) - 1

    for index, column in enumerate(row):
        if index == is_last_index:
            header += "{}".format(column)
        else:
            header += "{},".format(column)

    return header
