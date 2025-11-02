# work-3g
健康管理アプリ  

## 01_環境構築手順  
[環境構築手順](http://healthinfo-app-docs.s3-website-ap-northeast-1.amazonaws.com/wiki/localEnv)を参考にローカル環境構築を行う  

 
### **環境図**
![image](https://github.com/kohei-okazaki/work-3g/issues/423#issuecomment-3419791708)  


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
以下のプロジェクトで共通的に使うbusinessロジックをまとめたプロジェクト  
| プロジェクト名 |
| :---- |
| api |
| batch |
| dashboard |
| root |

* ### ha-common  
以下のMavenプロジェクトで共通的に使う共通処理を定義したプロジェクト  
| プロジェクト名 |
| :---- |
| api |
| batch |
| business |
| dashboard |
| db |
| root |

* ### ha-dashboard  
健康管理のダッシュボードプロジェクト  

* ### ha-db  
以下のMavenプロジェクトで共通的に使うTableに対応したEntityとMapperのみを定義したプロジェクト  
環境へのDB反映は本プロジェクトよりFlywayで行う  
| プロジェクト名 |
| :---- |
| api |
| batch |
| business |
| dashboard |
| root |

* ### ha-docs  
健康管理アプリのドキュメントプロジェクト  

* ### ha-node  
健康情報計算を行うAPIプロジェクト(JavaScriptのNode.jsで実装) 

* ### ha-pom  
以下のプロジェクトで共通的に使う外部ライブラリを定義したpomプロジェクト
| プロジェクト名 |
| :---- |
| api |
| batch |
| business |
| common |
| dashboard |
| db |
| root |
| tool |

* ### ha-root  
健康管理アプリの管理者用サイト    

* ### ha-selenium  
seleniumを利用した画面の自動テスト処理を定義したプロジェクト  

* ### ha-tool  
指定のフォーマットのExcelからDDLなどを自動生成するツールプロジェクト  
