# work-3g
健康管理アプリ  

## 01_環境構築手順  
[環境構築手順](https://github.com/kohei-okazaki/work-3g/wiki/00_%E7%92%B0%E5%A2%83%E6%A7%8B%E7%AF%89%E6%89%8B%E9%A0%86)を参考にローカル環境構築を行う  

## 02_Project構成  
* ### ha-api  
リクエストBodyにJSONを設定したPOST形式のHTTPリクエストを処理するプロジェクト   

* ### ha-batch  
Batch処理を定義したプロジェクト  

* ### ha-build  
ローカル環境でJarやAWS環境にデプロイするWarを作成するプロジェクト  
windows, linuxでそれぞれbatとshを用意  
(都度Jarを配置せずにpomから必要なJarを読み取って動くように修正した為、buildシェルを流す無くても良い)  

* ### ha-business  
api, dashboard, batchで共通的に使うbusinessロジックをまとめたプロジェクト  

* ### ha-common  
共通処理を定義したプロジェクト  

* ### ha-dashboard  
健康管理のダッシュボードのプロジェクト  

* ### ha-db  
Tableに対応したEntityとMapperのみを定義したプロジェクト  

* ### ha-pom  
api, auto, batch, business, common, tool, dashboard, webで共通的に使う外部ライブラリを定義したpomプロジェクト  

* ### ha-resource  
詳細設計書、DDL等を定義したドキュメントプロジェクト  

* ### ha-selenium  
seleniumを利用した画面の自動テスト処理を定義したプロジェクト  

* ### ha-tool  
指定のフォーマットのExcelからDDLなどを自動生成するツールプロジェクト  

* ### ha-web  
WebFWを定義したプロジェクト  
