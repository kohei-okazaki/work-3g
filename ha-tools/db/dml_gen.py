from pathlib import Path
import const, utils

"""
DMLを自動生成するモジュール
"""


def execute():
    """
    DML自動生成のメイン関数
    """

    for sheet_name in utils.get_sheet_names(excel_path=const.EXCEL_PATH):
        if sheet_name == const.DDL_SHEET_NAME or sheet_name == "TEMPLATE":
            # テーブル定義シートはスキップ
            continue
        row_list: list[list[str]] = read_data(sheet_name=sheet_name)
        generate(sheet_name, row_list)


def read_data(sheet_name: str) -> list[list[str]]:
    """
    指定されたシート名のDMLデータを読み込む関数
    @param sheet_name: シート名
    @return: 行情報リスト
    """

    # 行情報リストを取得
    return utils.read_rows(
        excel_path=const.EXCEL_PATH,
        sheet_name=sheet_name,
    )


def generate(sheet_name: str, row_list: list[list[str]]):
    """
    DMLを生成する関数
    @param sheet_name: シート名
    @param row_list: 行情報リスト
    """

    # テンプレートファイルを取得
    template = utils.get_template(const.DML_TEMPLATE_FILE)

    # 出力先ディレクトリ作成
    output_path = Path(const.DML_OUTPUT_PATH)
    output_path.mkdir(exist_ok=True)

    # ヘッダー情報
    header: str = get_header(row_list)
    # print("sheet: {}, header: {}".format(sheet_name, header))

    # データ情報リスト
    # ヘッダー行を除外
    data_row_list: list[list[str]] = row_list[1:]
    edit_row_list: list[str] = []

    for column_list in data_row_list:
        # 行単位でループ

        # 行情報を編集してカンマ区切りの文字列に変換
        row: str = ""
        for column in column_list:
            # カラム単位でループ

            if column == "True" or column == "False":
                # 真偽値にはシングルクォートを付けないように変換
                row += "{},".format(column.upper())
            elif column is True or column is False:
                # 真偽値にはシングルクォートを付けないように変換
                row += "{},".format(str(column).upper())
            else:
                row += "'{}',".format(column)

        # 行末のカンマを削除
        edit_row_list.append(row.rstrip(","))

    # Drop SQL生成
    dml_sql = template.render(
        table_name=sheet_name, header=header, row_list=edit_row_list
    )

    dml_file_name = "{}.sql".format(sheet_name.upper())
    (output_path / dml_file_name).write_text(dml_sql, encoding="utf-8")


def get_header(row_list: list[list[str]]) -> str:
    """
    行情報リストからヘッダ情報を取得する関数
    @param row_list: 行情報リスト
    @return: ヘッダ情報
    """
    if not row_list:
        return ""

    # 行情報リストの最初の行をヘッダとして取得
    row: list[str] = row_list[0]
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
