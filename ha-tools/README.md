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
コマンドプロンプト上で以下を実行
```bash
cd C:\app\git\work-3g\ha-tools\db
py -3.13 -m venv .venv
.venv\Scripts\activate.bat
python -m pip install -U pip
pip install -r requirements.txt
```

## ツール利用方法

### 自動生成
コマンドプロンプト上で以下を実行
```bash
cd C:\app\git\work-3g\ha-tools\db && .venv\Scripts\activate.bat
python main.py
```
