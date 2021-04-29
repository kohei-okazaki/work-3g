# work-3g
健康管理アプリ  

## 01_環境構築手順  
[環境構築手順](https://github.com/kohei-okazaki/work-3g/wiki/00_%E7%92%B0%E5%A2%83%E6%A7%8B%E7%AF%89%E6%89%8B%E9%A0%86)を参考にローカル環境構築を行う  

 
### **ローカル環境図**
![image](https://user-images.githubusercontent.com/24481212/104831982-f7470280-58d0-11eb-8277-96886b961222.PNG)  

-----

### **EC2環境図**  
![image](https://user-images.githubusercontent.com/24481212/104832424-4cd0de80-58d4-11eb-9117-86cf24c6191d.png)  

## 02_Project構成  
* ### ha-api  
JSONでのHTTP通信を受け付けるプロジェクト   

* ### ha-asset  
詳細設計書、DDL等を定義したドキュメントプロジェクト  

* ### ha-batch  
Batch処理を定義したプロジェクト  

* ### ha-build  
ローカル環境でJarやEC2環境にデプロイするWarを作成するプロジェクト  
windows, linuxでそれぞれbatとshを用意  

* ### ha-business  
api, dashboard, batch, rootで共通的に使うbusinessロジックをまとめたプロジェクト  

* ### ha-common  
共通処理を定義したプロジェクト  

* ### ha-dashboard  
健康管理のダッシュボードプロジェクト  

* ### ha-db  
Tableに対応したEntityとMapperのみを定義したプロジェクト  
環境へのDB反映は本プロジェクトよりFlywayで行う  

* ### ha-docs  
健康管理アプリのドキュメントプロジェクト  

* ### ha-node  
健康情報計算を行うAPIプロジェクト(JavaScriptのNode.jsで実装) 

* ### ha-pom  
api, batch, business, common, tool, dashboard, web, rootで共通的に使う外部ライブラリを定義したpomプロジェクト  

* ### ha-root  
健康管理アプリの管理者用サイト    

* ### ha-selenium  
seleniumを利用した画面の自動テスト処理を定義したプロジェクト  

* ### ha-tool  
指定のフォーマットのExcelからDDLなどを自動生成するツールプロジェクト  
