# work-3g
健康管理アプリ  

## 01_ビルド手順(windows)  
1.コマンドプロンプトでha-build/batまで移動  
2.maven-build.batを実行(build.iniのbaseDirの値を適宜合わせて下さい、work3gの直下のha-common等がみれるように)  
3.ha-api/pom.xmlのha-XXXのライブラリの依存関係をコメントアウト  
4.ha-web/pom.xmlのha-XXXのライブラリの依存関係をコメントアウト  
※上記の手順を行わなくてよいです(2019/06/01)  

## 02_Project構成  
* ### ha-api  
リクエストBodyにJSONを設定したPOST形式のHTTPリクエストを処理するプロジェクト  

* ### ha-auto  
WEB画面の自動テストをするプロジェクト  

* ### ha-batch  
Batch処理を定義したプロジェクト  

* ### ha-build  
ローカル環境でjarを作成するプロジェクト  
windows, linuxでそれぞれbatとshを用意  
(都度jarを配置せずにpomから必要なjarを読み取って動くように修正した為、buildシェルを流す無くても良い)  

* ### ha-business  
api, dashboard, batchで共通的に使うbusinessロジックをまとめたプロジェクト  

* ### ha-common  
共通処理を定義したプロジェクト  

* ### ha-dashboard  
健康管理のダッシュボードのプロジェクト  

* ### ha-db  
Tableに対応したEntityとMapperのみを定義したプロジェクト  

* ### ha-pom  
api, auto, business, common, tool, dashboard, webで共通的に使う外部ライブラリを定義したpomプロジェクト  

* ### ha-resource  
詳細設計書、DDL等を定義したドキュメントプロジェクト  

* ### ha-tool  
指定のフォーマットのExcelからDDLなどを自動生成するツールプロジェクト  

* ### ha-web  
WebFWを定義したプロジェクト  

