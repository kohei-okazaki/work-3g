@echo off

rem ------------------------------------------------------------------------
rem AWS環境にデプロイするwarを作成するバッチ
rem warファイルの出力先 = C:\app
rem
rem ------------------------------------------------------------------------

rem 出力先
set OUTPUT_PATH=C:\app
rem 資材位置
set SRC_PATH=C:\app\pleiades\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps\ha-dashboard

rem ディレクトリ 削除
echo ------------------------------------------------------------------------
echo clean war
echo ------------------------------------------------------------------------
del %OUTPUT_PATH%\dashboard.1.0.war
del %SRC_PATH%\dashboard.1.0.war

rem warファイルの作成
cd %SRC_PATH%
echo ------------------------------------------------------------------------
echo create war
echo ------------------------------------------------------------------------
jar cvf dashboard.1.0.war *

rem 出力先にwarファイルを配置
copy dashboard.1.0.war %OUTPUT_PATH%

rem ディレクトリ 掃除
echo ------------------------------------------------------------------------
echo clean war
echo ------------------------------------------------------------------------
del %SRC_PATH%\dashboard.1.0.war

rem batディレクトリへ戻る
cd %~dp0
