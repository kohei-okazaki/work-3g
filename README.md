# work-3g
健康管理アプリ  

ビルド手順  
1.ha-commonをmavenInstall  
2.ha-businessをmavenInstall  
3.コマンドプロンプトでha-build/batに移動  
build.batを実行  
(build.iniのbaseDirの値を適宜合わせて下さい)
3.ha-apiをjarでエクスポートし以下のプロジェクトのWEB-INF/libに配置  
・ha-web  
