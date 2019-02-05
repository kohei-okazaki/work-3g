# work-3g
健康管理アプリ  

## 01_ビルド手順(windows)  
1.コマンドプロンプトでha-build/batまで移動  
2.maven-build.batを実行(build.iniのbaseDirの値を適宜合わせて下さい、work3gの直下のha-common等がみれるように)  
3.ha-api/pom.xmlのha-XXXのライブラリの依存関係をコメントアウト  
4.ha-web/pom.xmlのha-XXXのライブラリの依存関係をコメントアウト  

## 02_Project構成  
### ha-api  
リクエストBodyにJSONを設定したPOST形式のHTTPリクエストを処理するプロジェクト  

### ha-build  
ローカル環境でjarを作成するプロジェクト  
windows, linuxでそれぞれbatとshを用意  

### ha-business  
api, webで共通的に使うbusinessロジックをまとめたプロジェクト  

### ha-common  
Util, 基底クラスをまとめたプロジェクト  

### ha-db  
Tableに対応したEntityとMapperのみを定義したプロジェクト  

### ha-pom  
api, business, common, tool, webで共通的に使う外部ライブラリを定義したpomプロジェクト  

### ha-resource  
ddlなどを定義したプロジェクト  

### ha-tool  
指定のフォーマットのExcelからDDLなどを自動生成するプロジェクト  

### ha-web  
管理画面のプロジェクト  



