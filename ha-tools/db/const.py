"""
DB定義関連の定数定義モジュール
"""

# DB定義書パス
EXCEL_PATH = r"C:\app\git\work-3g\ha-asset\02_db\DB.xlsx"

# DDL出力パス
DDL_OUTPUT_PATH = r"C:\app\git\work-3g\ha-asset\02_db\ddl\test"
# DDLテンプレートファイル名
DDL_TEMPLATE_FILE = "ddl_template.sql.j2"
# DDLシート名
DDL_SHEET_NAME = "TABLE_LIST"

# Define SQL出力パス
DEF_OUTPUT_PATH = r"C:\app\git\work-3g\ha-asset\02_db\others\test"
# Define SQLテンプレートファイル名
DEF_TEMPLATE_FILE = "define_template.sql.j2"


# テーブル名（論理）定義位置
LOGICAL_TABLE_NAME_POS = 0
# テーブル名（物理）定義位置
PHYSICAL_TABLE_NAME_POS = 1
# PKフラグ定義位置
PK_FLAG_POS = 2
# シーケンスフラグ定義位置
SEQUENCE_FLAG_POS = 3
# 暗号化フラグ定義位置
CRYPT_FLAG_POS = 4
# NotNullフラグ定義位置
NOTNULL_FLAG_POS = 5
# カラム名（論理）定義位置
LOGICAL_COLUMN_NAME_POS = 6
# カラム名（物理）定義位置
PHYSICAL_COLUMN_NAME_POS = 7
# データ型定義位置
DATA_TYPE_POS = 8
# サイズ定義位置
SIZE_POS = 9
# デフォルト値定義位置
DEFAULT_VALUE_POS = 10
