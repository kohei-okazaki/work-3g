from pathlib import Path

from jinja2 import Environment, FileSystemLoader
from openpyxl import load_workbook

"""
自動生成共通モジュール
"""


def get_template(template_file_name: str):
    """指定された Jinja2 テンプレートを取得する。

    Args:
        template_file_name: templates ディレクトリ内のテンプレートファイル名。

    Returns:
        読み込んだ Jinja2 の Template オブジェクト。
    """

    # Jinja2環境設定
    env = Environment(loader=FileSystemLoader("templates"), autoescape=False)

    # テンプレートファイルを取得
    return env.get_template(template_file_name)


def get_sheet_names(excel_path: str) -> list[str]:
    """Excel ファイルからシート名の一覧を取得する。

    Args:
        excel_path: 読み込む Excel ファイルのパス。

    Returns:
        ブック内の順序で並んだシート名のリスト。
    """

    # Excelファイル取得
    workbook = load_workbook(filename=Path(excel_path), data_only=True, read_only=True)

    # シート名リスト取得
    sheet_names: list[str] = workbook.sheetnames

    # Excelファイルを閉じる
    workbook.close()

    return sheet_names


def read_rows(
    excel_path: str,
    sheet_name: str,
    ignore_header: bool = False,
) -> list[str]:
    """指定された Excel シートから空行を除いて行データを読み込む。

    Args:
        excel_path: 読み込む Excel ファイルのパス。
        sheet_name: 読み込むシートの名前。
        ignore_header: True の場合は先頭行を除外する。既定値は False。

    Returns:
        各行のセル値をタプルとして格納したリスト。

    Raises:
        ValueError: 指定されたシートが存在しない場合。
    """

    # Excelファイル取得
    workbook = load_workbook(filename=Path(excel_path), data_only=True, read_only=True)

    if sheet_name not in workbook.sheetnames:
        raise ValueError("{}シートが存在しません".format(sheet_name))

    # 対象シートを取得
    worksheet = workbook[sheet_name]

    # 最小行番号設定
    min_row: int = 2 if ignore_header else 1

    rows: list = []
    for row in worksheet.iter_rows(min_row=min_row, values_only=True):
        if row is None or all(cell is None for cell in row):
            # 空行はスキップ
            continue
        rows.append(row)

    # Excelファイルを閉じる
    workbook.close()

    return rows
