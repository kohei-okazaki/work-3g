# work-3g
健康管理アプリ  

ビルド手順  
1.ha-commonをmavenInstallし、生成されたjarを以下のプロジェクトのWEB-INF/libに配置  
・ha-business  
・ha-api  
・ha-web  
2.ha-businessをmavenInstallし、生成されたjarを以下のプロジェクトのWEB-INF/libに配置  
・ha-api  
・ha-web  
3.ha-apiをjarでエクスポートし以下のプロジェクトのWEB-INF/libに配置  
・ha-web  
