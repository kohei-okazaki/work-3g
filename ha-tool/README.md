# ha-tool

## 概要
健康管理アプリのDDLやDMLを指定のDB定義書から自動生成するツールプロジェクト

## 作成ツール一覧
- DDL生成
- DML生成
- Entity生成(MyBatisより、自動生成するため使用しない)
- DROP.sql生成
- テーブル定義参照SQL生成

## 依存関係
Webアプリ側のプロジェクト__からの__参照を禁止

## ツールの使い方
/ha-tool/src/main/java/jp/co/ha/tool/ToolInvoker.javaをスタンドアローンで起動
