@echo off

rem iniファイルを読み込む
call initialize.bat



rem jarが置かれてるフォルダ
set commonTargetDir=%baseDir%\ha-common\target
set businessTargetDir=%baseDir%\ha-business\target
set dbTargetDir=%baseDir%\ha-db\target

rem jarを配置したいフォルダ
set dbLibDir=%baseDir%\ha-db\src\main\webapp\WEB-INF\lib
set businessLibDir=%baseDir%\ha-business\src\main\webapp\WEB-INF\lib
set apiLibDir=%baseDir%\ha-api\src\main\webapp\WEB-INF\lib
set webLibDir=%baseDir%\ha-web\src\main\webapp\WEB-INF\lib

rem ディレクトリ存在確認用変数
set befDbDir=%baseDir%\ha-db\src\main\webapp\WEB-INF
set befBusinessDir=%baseDir%\ha-business\src\main\webapp\WEB-INF
set befApiDir=%baseDir%\ha-api\src\main\webapp\WEB-INF
set befWebDir=%baseDir%\ha-web\src\main\webapp\WEB-INF

echo ------------------------------------------------------------------------
echo START check lib dir
echo ------------------------------------------------------------------------
rem dbのlibディレクトリ 存在確認
echo %dbsLibDir% check
call makeDir.bat %befDbDir% "lib"


rem businessのlibディレクトリ 存在確認
echo %businessLibDir% check
call makeDir.bat %befBusinessDir% "lib"


rem apiのlibディレクトリ 存在確認
echo %apiLibDir% check
call makeDir.bat %befApiDir% "lib"


rem webのlibディレクトリ 存在確認
echo %webLibDir% check
call makeDir.bat %befWebDir% "lib"

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
copy *.jar %webLibDir%
echo ------------------------------------------------------------------------
echo END db.jar deploy
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
