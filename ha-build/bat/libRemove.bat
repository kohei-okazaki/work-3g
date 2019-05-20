@echo off

rem ------------------------------------------------------------------------
rem libを削除するバッチ
rem ------------------------------------------------------------------------

rem iniファイルを読み込む
call initialize.bat


rem jarが配置されてるディレクトリ
set dbLibDir=%BASE_DIR%\ha-db\src\main\webapp\WEB-INF\lib
set webLibDir=%BASE_DIR%\ha-web\src\main\webapp\WEB-INF\lib
set businessLibDir=%BASE_DIR%\ha-business\src\main\webapp\WEB-INF\lib
set apiLibDir=%BASE_DIR%\ha-api\src\main\webapp\WEB-INF\lib
set dashboardLibDir=%BASE_DIR%\ha-dashboard\src\main\webapp\WEB-INF\lib

cls

echo ------------------------------------------------------------------------
echo START jar delete
echo ------------------------------------------------------------------------


rem db/libのファイルを削除
cd %dbLibDir%
del *.jar

rem web/libのファイルを削除
cd %webLibDir%
del *.jar

rem business/libのファイルを削除
cd %businessLibDir%
del *.jar

rem api/libのファイルを削除
cd %apiLibDir%
del *.jar

rem dashboard/libのファイルを削除
cd %dashboardLibDir%
del *.jar


echo ------------------------------------------------------------------------
echo END jar delete
echo ------------------------------------------------------------------------


cd %~dp0