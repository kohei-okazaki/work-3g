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
バッチ処理を定義したプロジェクト  

* ### ha-build  
ローカル環境でjarを作成するプロジェクト  
windows, linuxでそれぞれbatとshを用意  

* ### ha-business  
api, dashboard, batchで共通的に使うbusinessロジックをまとめたプロジェクト  

* ### ha-common  
共通処理を定義したプロジェクト  

* ### ha-dashboard  
健康管理のダッシュボードのプロジェクト  

* ### ha-db  
Tableに対応したEntityとMapperのみを定義したプロジェクト  

* ### ha-pom  
api, business, common, tool, dashboard, webで共通的に使う外部ライブラリを定義したpomプロジェクト  

* ### ha-resource  
ddlなどを定義したプロジェクト  

* ### ha-tool  
指定のフォーマットのExcelからDDLなどを自動生成するプロジェクト  

* ### ha-web  
Webフレームワークを定義したプロジェクト  



