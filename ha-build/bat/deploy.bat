@echo off

rem iniファイルを読み込む
call initialize.bat



rem jarが置かれてるフォルダ
set commonTargetDir=%baseDir%\ha-common\target
set businessTargetDir=%baseDir%\ha-business\target

rem jarを配置したいフォルダ
set businessLibDir=%baseDir%\ha-business\src\main\webapp\WEB-INF\lib
set apiLibDir=%baseDir%\ha-api\src\main\webapp\WEB-INF\lib
set webLibDir=%baseDir%\ha-web\src\main\webapp\WEB-INF\lib

rem ディレクトリ存在確認用変数
set befBusinessDir=%baseDir%\ha-business\src\main\webapp\WEB-INF
set befApiDir=%baseDir%\ha-api\src\main\webapp\WEB-INF
set befWebDir=%baseDir%\ha-web\src\main\webapp\WEB-INF

echo ------------------------------------------------------------------------
echo START check lib dir
echo ------------------------------------------------------------------------
rem businessのlibディレクトリ 存在確認
cd %befBusinessDir%
echo %businessLibDir% check
if not exist "lib\" (
	mkdir lib
	echo mkdir %businessLibDir%
)


rem apiのlibディレクトリ 存在確認
cd %befApiDir%
echo %apiLibDir% check
if not exist "lib\" (
	mkdir lib
	echo mkdir %apiLibDir%
)


rem webのlibディレクトリ 存在確認
cd %befWebDir%
echo %webLibDir% check
if not exist "lib\" (
	mkdir lib
	echo mkdir %webLibDir%
)
echo ------------------------------------------------------------------------
echo END check lib dir
echo ------------------------------------------------------------------------




rem common.jarを配置
echo ------------------------------------------------------------------------
echo START common.jar deploy
echo ------------------------------------------------------------------------
cd %commonTargetDir%
copy *.jar %businessLibDir%
copy *.jar %apiLibDir%
copy *.jar %webLibDir%
echo ------------------------------------------------------------------------
echo END common.jar deploy
echo ------------------------------------------------------------------------


rem business.jarを配置
echo ------------------------------------------------------------------------
echo START business.jar deploy
echo ------------------------------------------------------------------------
cd %businessTargetDir%
copy *.jar %apiLibDir%
copy *.jar %webLibDir%
echo ------------------------------------------------------------------------
echo END business.jar deploy
echo ------------------------------------------------------------------------



cd %~dp0
