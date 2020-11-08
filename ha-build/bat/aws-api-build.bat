@echo off

rem ------------------------------------------------------------------------
rem EC2環境用のAPIのwarファイルを作成するバッチ
rem ha-pom/pom.xmlのプロファイルはec2とする
rem warファイルの出力先 = C:\app
<<<<<<< HEAD
rem
=======
rem ※※※※※maven-build.batでビルドを行うので、本ファイルは基本使用しない※※※※※
>>>>>>> 8c90c43b2e480fb2d180a3ac63e6d3ad68084620
rem ------------------------------------------------------------------------

rem 出力先
set OUTPUT_PATH=C:\app
rem 資材位置
set SRC_PATH=%OUTPUT_PATH%\pleiades\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps\ha-api

rem ディレクトリ 削除
echo ------------------------------------------------------------------------
echo clean war
echo ------------------------------------------------------------------------
del %OUTPUT_PATH%\api-1.0.0.war
del %SRC_PATH%\api-1.0.0.war

rem warファイルの作成
cd %SRC_PATH%
echo ------------------------------------------------------------------------
echo create war
echo ------------------------------------------------------------------------
jar cvf api-1.0.0.war *

rem 出力先にwarファイルを配置
copy api-1.0.0.war %OUTPUT_PATH%

rem ディレクトリ 掃除
echo ------------------------------------------------------------------------
echo clean war
echo ------------------------------------------------------------------------
del %SRC_PATH%\api-1.0.0.war

rem batディレクトリへ戻る
cd %~dp0
