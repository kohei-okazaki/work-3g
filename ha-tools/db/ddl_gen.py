from pathlib import Path
import const, utils

"""
DDLを自動生成するモジュール
"""


def execute():
    """
    DDL自動生成のメイン関数
    """
    tables: dict[str, list[dict]] = read_tables()
    generate(tables)


def read_tables() -> dict[str, list[dict]]:
    """
    Excelファイルからテーブルリストを読み込む関数
    @return: (テーブル名, カラム情報リスト)の辞書
    """

    # Excelファイルから行データリストを取得
    rows: list = utils.read_rows(
        excel_path=const.EXCEL_PATH,
        sheet_name=const.DDL_SHEET_NAME,
        ignore_header=True,
    )

    # テーブル名->カラム情報リストのdict
    tables: dict[str, list[dict]] = {}

    for row in rows:
        # テーブル名（論理）
        logical_table_name = row[const.LOGICAL_TABLE_NAME_POS]
        # テーブル名（物理）
        physical_table_name = row[const.PHYSICAL_TABLE_NAME_POS]
        # PKフラグ
        is_primary_key = (
            row[const.PK_FLAG_POS] is not None and row[const.PK_FLAG_POS] == "1"
        )
        # シーケンスフラグ
        is_sequence = (
            row[const.SEQUENCE_FLAG_POS] is not None
            and row[const.SEQUENCE_FLAG_POS] == "1"
        )
        # 暗号化フラグ
        is_crypt = (
            row[const.CRYPT_FLAG_POS] is not None and row[const.CRYPT_FLAG_POS] == "1"
        )
        # NotNullフラグ
        is_not_null = (
            row[const.NOTNULL_FLAG_POS] is not None
            and row[const.NOTNULL_FLAG_POS] == "1"
        )
        # カラム名(論理)
        logical_column_name = row[const.LOGICAL_COLUMN_NAME_POS]
        # カラム名(物理)
        physical_column_name = row[const.PHYSICAL_COLUMN_NAME_POS]
        # データ型
        data_type = row[const.DATA_TYPE_POS]
        # サイズ
        size = row[const.SIZE_POS]
        # データ型にサイズを付与
        if size is not None and size != "":
            # サイズ指定がある場合
            data_type = data_type + "({})".format(size)
        # デフォルト値
        default_value = row[const.DEFAULT_VALUE_POS]
        if (
            row[const.DATA_TYPE_POS] is not None
            and row[const.DATA_TYPE_POS] in ("VARCHAR", "DATE")
            and default_value is not None
        ):
            # デフォルト値が文字列型 or 日付型の場合、シングルクォートで囲む
            default_value = "'{}'".format(default_value)

        # column_info辞書を作成
        column_info = {
            "logical_column_name": logical_column_name,
            "physical_column_name": physical_column_name,
            "is_primary_key": is_primary_key,
            "is_sequence": is_sequence,
            "is_crypt": is_crypt,
            "is_not_null": is_not_null,
            "type": data_type,
            "has_default": default_value is not None and default_value != "",
            "default_value": default_value,
        }

        # テーブル名(論理名.物理名)をキーにしてカラム情報を追加
        table_key = "{}.{}".format(logical_table_name, physical_table_name)
        if table_key in tables:
            # テーブルが存在する場合、既存テーブルにカラム情報を追加
            tables[table_key].append(column_info)
        else:
            # テーブルが存在しない場合、新規作成してカラム情報を追加
            tables[table_key] = [column_info]

    return tables


def generate(tables: dict[str, list[dict]]):
    """
    DDLを生成してファイルに出力する
    @param tables: (テーブル名, カラム情報リスト)の辞書
    """

    # テンプレートファイルを取得
    ddl_template = utils.get_template(const.DDL_TEMPLATE_FILE)

    # 出力ファイルパスを作成
    output_file_path = Path(const.DDL_OUTPUT_PATH)
    output_file_path.mkdir(exist_ok=True)

    for table_name, columns in tables.items():

        # テーブル名（論理）
        logical_table_name = table_name.split(".")[const.LOGICAL_TABLE_NAME_POS]
        # テーブル名（物理）
        physical_table_name = table_name.split(".")[const.PHYSICAL_TABLE_NAME_POS]

        ddl = ddl_template.render(
            logical_table_name=logical_table_name,
            physical_table_name=physical_table_name,
            columns=columns,
        )

        ddl_file_name = "{}.sql".format(physical_table_name)
        (output_file_path / ddl_file_name).write_text(ddl, encoding="utf-8")
