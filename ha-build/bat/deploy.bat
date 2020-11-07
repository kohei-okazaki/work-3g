@echo off

rem ------------------------------------------------------------------------
rem jarを配置するバッチファイル
rem
<<<<<<< HEAD
rem ■■■このファイルはpomを共通で定義する方法でうまく行ったので基本的に使用しない■■■
=======
rem ■■■pomを共通で定義する方法でうまく行ったので基本的にこのファイルは使用しない■■■
>>>>>>> 8c90c43b2e480fb2d180a3ac63e6d3ad68084620
rem
rem ha-dashboard/target配下にha-common-1.0.jar, ha-db-1.0.jar, ha-web-1.0.jar, ha-business-1.0.jarを配置
rem ha-api/target配下に              ha-common-1.0.jar, ha-db-1.0.jar, ha-web-1.0.jar, ha-business-1.0.jarを配置
rem ha-business/target配下に  ha-common-1.0.jar, ha-db-1.0.jar, ha-web-1.0.jarを配置
rem ha-web/target配下に              ha-common-1.0.jar, ha-db-1.0.jarを配置
rem ha-db/target配下に                ha-common-1.0.jarを配置
rem
rem ------------------------------------------------------------------------

rem iniファイルを読み込む
call initialize.bat

cd %~dp0
echo ------------------------------------------------------------------------
echo START deploy.bat
echo ------------------------------------------------------------------------

rem jarが置かれてるフォルダ
set commonTargetDir=%BASE_DIR%\ha-common\target
set dbTargetDir=%BASE_DIR%\ha-db\target
set webTargetDir=%BASE_DIR%\ha-web\target
set businessTargetDir=%BASE_DIR%\ha-business\target

rem jarを配置したいフォルダ
set dbLibDir=%BASE_DIR%\ha-db\src\main\webapp\WEB-INF\lib
set webLibDir=%BASE_DIR%\ha-web\src\main\webapp\WEB-INF\lib
set businessLibDir=%BASE_DIR%\ha-business\src\main\webapp\WEB-INF\lib
set apiLibDir=%BASE_DIR%\ha-api\src\main\webapp\WEB-INF\lib
set dashboardLibDir=%BASE_DIR%\ha-dashboard\src\main\webapp\WEB-INF\lib

rem ディレクトリ存在確認用変数
set befDbDir=%BASE_DIR%\ha-db\src\main\webapp\WEB-INF
set befWebDir=%BASE_DIR%\ha-web\src\main\webapp\WEB-INF
set befBusinessDir=%BASE_DIR%\ha-business\src\main\webapp\WEB-INF
set befApiDir=%BASE_DIR%\ha-api\src\main\webapp\WEB-INF
set befDashboardDir=%BASE_DIR%\ha-dashboard\src\main\webapp\WEB-INF

echo ------------------------------------------------------------------------
echo START check lib dir
echo ------------------------------------------------------------------------

rem dbのlibディレクトリ 存在確認
echo %dbLibDir% check
call makeDir.bat %befDbDir% lib


rem webのlibディレクトリ 存在確認
echo %webLibDir% check
call makeDir.bat %befWebDir% lib


rem businessのlibディレクトリ 存在確認
echo %businessLibDir% check
call makeDir.bat %befBusinessDir% lib


rem apiのlibディレクトリ 存在確認
echo %apiLibDir% check
call makeDir.bat %befApiDir% lib


rem dashboardのlibディレクトリ 存在確認
echo %dashboardLibDir% check
call makeDir.bat %befDashboardDir% lib

echo ------------------------------------------------------------------------
echo END check lib dir
echo ------------------------------------------------------------------------




rem common.jarを配置
echo ------------------------------------------------------------------------
echo START common.jar deploy
echo ------------------------------------------------------------------------
cd %commonTargetDir%
copy *.jar %dbLibDir%
copy *.jar %businessLibDir%
copy *.jar %apiLibDir%
copy *.jar %webLibDir%
copy *.jar %dashboardLibDir%
echo ------------------------------------------------------------------------
echo END common.jar deploy
echo ------------------------------------------------------------------------


rem db.jarを配置
echo ------------------------------------------------------------------------
echo START db.jar deploy
echo ------------------------------------------------------------------------
cd %dbTargetDir%
copy *.jar %businessLibDir%
copy *.jar %apiLibDir%
copy *.jar %dashboardLibDir%
echo ------------------------------------------------------------------------
echo END db.jar deploy
echo ------------------------------------------------------------------------


rem web.jarを配置
echo ------------------------------------------------------------------------
echo START web.jar deploy
echo ------------------------------------------------------------------------
cd %webTargetDir%
copy *.jar %businessLibDir%
copy *.jar %apiLibDir%
copy *.jar %dashboardLibDir%
echo ------------------------------------------------------------------------
echo END web.jar deploy
echo ------------------------------------------------------------------------


rem business.jarを配置
echo ------------------------------------------------------------------------
echo START business.jar deploy
echo ------------------------------------------------------------------------
cd %businessTargetDir%
copy *.jar %apiLibDir%
copy *.jar %dashboardLibDir%
echo ------------------------------------------------------------------------
echo END business.jar deploy
echo ------------------------------------------------------------------------

echo ------------------------------------------------------------------------
echo END deploy.bat
echo ------------------------------------------------------------------------


cd %~dp0
