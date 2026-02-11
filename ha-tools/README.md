# ha-tools

## 概要
以下のツールプロジェクト。
 - 健康管理アプリのDDLやDMLを指定のDB定義書から自動生成ツール
 - 健康管理アプリのAPI自動テストツール


## プログラミング言語
Python3.13


## 利用ライブラリ
 - openpyxl
 - Jinja2


## 作成ツール一覧
- DDL生成
- DML生成
- DROP.sql生成
- テーブル定義参照SQL生成


## ツール初期設定
| # | 手順 |
| --- | --- |
| 1 | コマンドプロンプト → ''' cd C:\app\git\work-3g\ha-tools\db ''' |
| 2 | ''' py -3.13 -m venv .venv ''' |
| 3 | コマンドプロンプト → ''' .venv\Scripts\activate.bat ''' |
| 4 | ''' python -m pip install -U pip ''' |
| 5 | ''' pip install -r requirements.txt ''' |


## ツール利用方法

### 自動生成
| # | 手順 |
| --- | --- |
| 1 | コマンドプロンプト → ''' cd C:\app\git\work-3g\ha-tools\db && .venv\Scripts\activate.bat ''' |
| 2 | コマンドプロンプト → ''' python main.py ''' |
