import ddl_gen, dml_gen, define_gen, drop_gen

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
