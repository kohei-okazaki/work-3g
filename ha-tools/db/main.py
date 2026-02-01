import ddl_gen, dml_gen, define_gen, drop_gen

"""
自動生成処理のメインモジュール
 - DDL生成
 - DML生成
 - Define SQL生成
 - Drop SQL生成
"""


def execute():
    """
    メイン処理
    """

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
