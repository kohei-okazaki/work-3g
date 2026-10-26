import ddl_gen
import define_gen
import dml_gen
import drop_gen

"""
自動生成処理のメインモジュール
    - DDL生成
    - DML生成
    - Define SQL生成
    - Drop SQL生成
"""


def execute():
    """DDL、DML、テーブル定義参照 SQL、テーブル削除 SQL を順に生成する。"""
    # DDL生成
    ddl_gen.execute()

    # DML生成
    dml_gen.execute()

    # Define SQL生成
    define_gen.execute()

    # Drop SQL生成
    drop_gen.execute()


if __name__ == "__main__":
    execute()
