# work-3g
健康管理アプリ  

## 01_環境構築手順  
[環境構築手順](https://github.com/kohei-okazaki/work-3g/wiki/00_%E7%92%B0%E5%A2%83%E6%A7%8B%E7%AF%89%E6%89%8B%E9%A0%86)を参考にローカル環境構築を行う  

構成図イメージ  
ローカル環境図
![image](https://user-images.githubusercontent.com/24481212/87867075-1a493600-c9c4-11ea-8351-cd0aad6eca4a.png) 

## 02_Project構成  
* ### ha-api  
JSONでのHTTP通信を受け付けるプロジェクト   

* ### ha-batch  
Batch処理を定義したプロジェクト  

* ### ha-build  
ローカル環境でJarやEC2環境にデプロイするWarを作成するプロジェクト  
windows, linuxでそれぞれbatとshを用意  

* ### ha-business  
api, dashboard, batchで共通的に使うbusinessロジックをまとめたプロジェクト  

* ### ha-common  
共通処理を定義したプロジェクト  

* ### ha-dashboard  
健康管理のダッシュボードプロジェクト  

* ### ha-db  
Tableに対応したEntityとMapperのみを定義したプロジェクト  
環境へのDB反映は本プロジェクトよりFlywayで行う  

* ### ha-node  
健康情報計算を行うAPIプロジェクト(JavaScriptのNode.jsで実装) 

* ### ha-pom  
api, batch, business, common, tool, dashboard, webで共通的に使う外部ライブラリを定義したpomプロジェクト  

* ### ha-resource  
詳細設計書、DDL等を定義したドキュメントプロジェクト  

* ### ha-selenium  
seleniumを利用した画面の自動テスト処理を定義したプロジェクト  

* ### ha-tool  
指定のフォーマットのExcelからDDLなどを自動生成するツールプロジェクト  

* ### ha-web  
WebFW部分を定義したプロジェクト  
