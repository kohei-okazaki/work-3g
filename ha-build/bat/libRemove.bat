@echo off

rem ------------------------------------------------------------------------
rem libを削除するバッチ
rem ------------------------------------------------------------------------

rem iniファイルを読み込む
call initialize.bat


rem jarが配置されてるディレクトリ
set dbLibDir=%baseDir%\ha-db\src\main\webapp\WEB-INF\lib
set businessLibDir=%baseDir%\ha-business\src\main\webapp\WEB-INF\lib
set apiLibDir=%baseDir%\ha-api\src\main\webapp\WEB-INF\lib
set webLibDir=%baseDir%\ha-web\src\main\webapp\WEB-INF\lib

cls

echo ------------------------------------------------------------------------
echo START jar delete
echo ------------------------------------------------------------------------


rem db/libのファイルを削除
cd %dbLibDir%
del *.jar

rem business/libのファイルを削除
cd %businessLibDir%
del *.jar

rem api/libのファイルを削除
cd %apiLibDir%
del *.jar

rem web/libのファイルを削除
cd %webLibDir%
del *.jar


echo ------------------------------------------------------------------------
echo END jar delete
echo ------------------------------------------------------------------------


cd %~dp0