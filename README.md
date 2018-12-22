# work-3g
健康管理アプリ  

## ビルド手順(windows)  
1.コマンドプロンプトでha-build/batまで移動  
2.maven-build.batを実行 
(build.iniのbaseDirの値を適宜合わせて下さい、work3gの直下にha-common等がみれるように)  
3.ha-apiをjarでエクスポートし以下のプロジェクトのWEB-INF/libに配置  
・ha-web  

## Project構成  
### ha-api  
BodyにJsonを設定したPost形式のHttpリクエストを処理するプロジェクト  

### ha-build  
ローカル環境でjarを作成するプロジェクト  
windows, linuxで用意  

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
指定フォーマットのExcelからDDLなどを自動生成するプロジェクト  

### ha-web  
管理画面のプロジェクト  



