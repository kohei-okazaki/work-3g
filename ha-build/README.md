# ha-build

## 概要
健康管理アプリのビルド関連処理を管理するプロジェクト

## bat
概要：Windowsユーザ用に開発に必要なmavenビルドバッチやflywayを行うバッチを管理。build.iniのBASE_DIRをローカルの本プロジェクトのルートパスを合わせるようにする。
- ローカルリポジトリの場所が「C:\app\git\work-3g」の場合、修正不要
- ローカルリポジトリの場所が「C:\hoge」の場合、BASE_DIR=C:\hogeとする

## shell
概要：Macユーザ用に開発に必要なmavenビルドシェルやflywayを行うシェルを管理。また、EC2インスタンスのAMIがLinuxのため、EC2インスタンスのサーバセットアップ用にシェルを用意。