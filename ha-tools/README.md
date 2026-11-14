# ha-tools

## 概要
以下のツールプロジェクト。
 - 健康管理アプリのDDLやDMLを指定のDB定義書から自動生成ツール
 - 健康管理アプリのAPI自動テストツール
 - ローカル開発用の補助ツール・画像資料


## Pythonツールの実行環境
Python 3.15.0

ローカルにインストールした Python 3.15 を使って、ツールごとに venv 仮想環境を作成します。
ライブラリのインストールとツールの実行には、各仮想環境の Python を使用します。
venv は作成元の Python を利用するため、Python 3.15 のインストールが必要です。


## 利用ライブラリ
| ライブラリ | バージョン | 用途 |
|---|---|---|
| openpyxl | 3.1.5 | Excelファイルの読み込み |
| Jinja2 | 3.1.6 | SQLテンプレートの処理 |
| et_xmlfile | 2.0.0 | openpyxlの依存ライブラリ |
| MarkupSafe | 3.0.4 | Jinja2の依存ライブラリ |
| pytest | 9.1.1 | API自動テスト |
| requests | 2.34.2 | APIへのHTTPリクエスト |


## 作成ツール一覧
- DDL生成
- DML生成
- DROP.sql生成
- テーブル定義参照SQL生成


## ツール初期設定
以下のコマンドは Windows のコマンドプロンプトと PowerShell の両方で実行できます。
仮想環境の Python を直接指定するため、activate は不要です。

### DBツール(/db)
```bash
cd C:\app\git\work-3g\ha-tools\db
py -3.15 -m venv .venv
.\.venv\Scripts\python.exe -m pip install -U pip
.\.venv\Scripts\python.exe -m pip install -r requirements.txt
```

### API自動テスト(/ha-api-test)
```bash
cd C:\app\git\work-3g\ha-tools\ha-api-test
py -3.15 -m venv .venv
.\.venv\Scripts\python.exe -m pip install -U pip
.\.venv\Scripts\python.exe -m pip install -r requirements.txt
```

既存の `.venv` が別の Python バージョンで作成されている場合は、その仮想環境を削除してから上記の手順で作り直してください。
`.venv` は Git の管理対象外です。
## ツール利用方法

### 自動生成
入力定義書は [ha-docs/db/DB.xlsx](../ha-docs/db/DB.xlsx) とし、生成したSQLは同じ [ha-docs/db](../ha-docs/db/) 配下の`ddl`・`dml`・`others`へ出力する。

```bash
cd C:\app\git\work-3g\ha-tools\db
.\.venv\Scripts\python.exe main.py
```

### API自動テスト
テスト対象のAPIを `http://localhost:8081` で起動してから実行します。

```bash
cd C:\app\git\work-3g\ha-tools\ha-api-test
.\.venv\Scripts\python.exe -m pytest
```

## その他の開発補助ツール（other_tools）

| フォルダ | 内容 |
| --- | --- |
| [api](./other_tools/api/) | Google Chrome拡張機能のAPITesterテンプレート。 |
| [png](./other_tools/png/) | READMEで参照する健康情報登録フロー画像。 |
| [sql](./other_tools/sql/) | ローカルMySQLへのSQL実行・ダンプ用バッチとSQLファイル。 |

APITesterの計算APIリクエストはAPI Gatewayへ接続する。接続先はAPITesterの環境設定`CALC_API_GW_URL`で指定する。

SQLバッチは`other_tools/sql`を作業ディレクトリとして使用する。
