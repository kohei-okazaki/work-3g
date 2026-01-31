from pathlib import Path
from jinja2 import Environment, FileSystemLoader
import const, util

"""
Define SQLを自動生成するモジュール
"""


def execute():
    """
    Define SQL自動生成のメイン関数
    """
    tables: dict[str, list[dict]] = read_tables()
    generate(tables)


def read_tables() -> dict[str, list[dict]]:
    """
    ExcelファイルからDefine SQLリストを読み込む関数
    @return: Define SQLリスト
    """

    # Excelファイルから行データリストを取得(テーブル定義のみ取得するのでDDLとシート名は同じ)
    rows: list = util.read_rows(
        excel_path=const.EXCEL_PATH, sheet_name=const.DDL_SHEET_NAME
    )

    # ヘッダ行を削除
    rows.remove(rows[0])

    # テーブル名（物理）->テーブル名（論理）のdict
    tables: dict[str, str] = {}

    for row in rows:
        # テーブル名（論理）
        logical_table_name = row[const.LOGICAL_TABLE_NAME_POS]
        # テーブル名（物理）
        physical_table_name = row[const.PHYSICAL_TABLE_NAME_POS]

        if physical_table_name not in tables:
            # テーブル名追加
            tables[physical_table_name] = logical_table_name

    return tables


def generate(tables: dict[str, str]):
    """
    Define SQLを生成する関数
    @param tables: (テーブル名（物理）, テーブル名（論理）)の辞書
    """

    # Jinja2環境設定
    env = Environment(loader=FileSystemLoader("templates"), autoescape=False)

    # テンプレートファイルを取得
    template = env.get_template(const.DEF_TEMPLATE_FILE)

    # 出力先ディレクトリ作成
    output_path = Path(const.DEF_OUTPUT_PATH)
    output_path.mkdir(exist_ok=True)

    print(tables)
    
    # Define SQL生成
    def_sql = template.render(tables=tables)

    def_file_name = "TABLE_DEFINE.sql"
    (output_path / def_file_name).write_text(def_sql, encoding="utf-8")